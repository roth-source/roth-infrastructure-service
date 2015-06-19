package roth.infrastructure.task;

import java.util.Arrays;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpSession;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.model.Host;
import roth.infrastructure.data.model.Server;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.table.HostTable;
import roth.infrastructure.data.type.HostType;
import roth.infrastructure.data.type.ServerType;
import roth.infrastructure.service.org.CreateServerRequest;
import roth.infrastructure.ssh.ServerSsh;
import roth.infrastructure.util.DigitalOceanUtil;
import roth.lib.api.digitalocean.data.model.Action;
import roth.lib.api.digitalocean.data.model.ActionLink;
import roth.lib.api.digitalocean.data.model.Droplet;
import roth.lib.api.digitalocean.data.model.Network;
import roth.lib.api.digitalocean.data.response.DropletResponse;
import roth.lib.util.CalendarUtil;
import roth.lib.util.EnumUtil;
import roth.lib.util.ResourceUtil;
import roth.lib.util.ThreadUtil;

public class CreateServerTask extends Task
{
	protected static String CREATE 			= "create";
	protected static String IN_PROGRESS 	= "in-progress";
	protected static String COMPLETED 		= "completed";
	protected static String ERRORED 		= "errored";
	
	protected CreateServerRequest request;
	
	public CreateServerTask(Db db, User user, HttpSession session, String taskId, CreateServerRequest request)
	{
		super(db, user, session, taskId);
		this.request = request;
	}
	
	@Override
	public void run()
	{
		Host host = null;
		HostType hostType = HostType.UNMANAGED;
		if(!request.getHostId().equals(HostType.UNMANAGED.toString()))
		{
			host = HostTable.get(db, user).findById(request.getHostId());
			hostType = host.getType();
		}
		ServerType serverType = EnumUtil.fromString(request.getServerType(), ServerType.class);
		startAction("CREATE SERVER");
		switch(hostType)
		{
			case DIGITAL_OCEAN:
			{
				String token = host.getApiKey();
				updateAction("creating droplet");
				DropletResponse response = DigitalOceanUtil.createServer(token, request.getName(), request.getDatacenter(), request.getPlan(), request.getDistribution(), host.getSshKeyId());
				Integer dropletId = response.getDroplet().getId();
				Integer actionId = null;
				for(ActionLink actionLink : response.getLinks().getActions())
				{
					if(CREATE.equalsIgnoreCase(actionLink.getRel()))
					{
						actionId = actionLink.getId();
						break;
					}
				}
				if(dropletId != null && actionId != null)
				{
					updateAction("created droplet " + dropletId);
					Server server = new Server(db, user, host, serverType, request.getName()).setCreatedOn(CalendarUtil.now()).insert();
					waitAction(30);
					updateAction("checking action " + actionId, true);
					Action action = DigitalOceanUtil.getDropletAction(host.getApiKey(), dropletId, actionId);
					while(IN_PROGRESS.equalsIgnoreCase(action.getStatus()))
					{
						ThreadUtil.wait(10);
						action = DigitalOceanUtil.getDropletAction(token, dropletId, actionId);
					}
					if(!ERRORED.equalsIgnoreCase(action.getStatus()))
					{
						Droplet droplet = DigitalOceanUtil.getDroplet(token, dropletId);
						if(droplet != null)
						{
							Network network = droplet.getNetworks().getV4().peekFirst();
							if(network != null)
							{
								String ip = network.getIpAddress();
								server.setIp(ip).update();
								updateAction("started " + ip);
								endAction();
								startAction("CONNECT SSH");
								waitAction(60);
								try(ServerSsh ssh = connectSsh(serverType, ip);)
								{
									setupLinux(ssh);
								}
							}
							else
							{
								//throw new DigitalOceanException("no ip address");
							}
						}
						else
						{
							//throw new DigitalOceanException("no ip address");
						}
					}
					else
					{
						//throw new DigitalOceanException("create server errored");
					}
				}
				else
				{
					//throw new DigitalOceanException("create server errored");
				}
				break;
			}
			case LINODE:
			{
				
				break;
			}
			case VULTR:
			{
				break;
			}
			case RACKSPACE:
			{
				break;
			}
			case UNMANAGED:
			{
				break;
			}
			default:
			{
				
			}
		}
		completeTask();
	}
	
	protected void setupLinux(ServerSsh ssh)
	{
		startAction("SETUP LINUX");
		
		updateAction("changing root password");
		ssh.passwd(request.getPassword());
		
		updateAction("setting Mountain timezone");
		ssh.setTimeZone("America/Denver");
		
		updateAction("updating package manager", true);
		ssh.update();
		
		Integer sshPort = 22;
		updateAction("copying .bashrc", true);
		String bashrc = ResourceUtil.toString("roth/infrastructure/base/linux/bashrc");
		String bashEnv = ResourceUtil.toString("roth/infrastructure/base/linux/bash_env");
		ssh.put(bashrc + bashEnv, ".bashrc");
		
		updateAction("copying sshd_config");
		String sshdConfig = ResourceUtil.toString("roth/infrastructure/base/linux/sshd_config");
		sshdConfig = sshdConfig.replaceAll("\\{\\{port\\}\\}", String.valueOf(sshPort));
		ssh.put(sshdConfig, "/etc/ssh/sshd_config");
	
		updateAction("setting up firewall", true);
		Integer httpPort = 80;
		Integer httpsPort = 443;
		Integer jettyHttpPort = 8080;
		Integer jettyHttpsPort = 8443;
		LinkedHashMap<Integer, Integer> forwardedPorts = new LinkedHashMap<Integer, Integer>();
		forwardedPorts.put(httpPort, jettyHttpPort);
		forwardedPorts.put(httpsPort, jettyHttpsPort);
		ssh.setupFirewall(forwardedPorts, Arrays.asList(jettyHttpPort, jettyHttpsPort), Arrays.asList(sshPort));
		
		updateAction("restarting ssh");
		ssh.systemctlRestart("ssh");
		
		endAction();
	}
	
}
