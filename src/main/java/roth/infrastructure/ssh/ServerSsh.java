package roth.infrastructure.ssh;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import roth.infrastructure.data.type.ServerType;
import roth.lib.net.ssh.Sftp;
import roth.lib.net.ssh.Ssh;
import roth.lib.net.ssh.SshConfig;

public class ServerSsh extends Ssh
{
	
	public static ServerSsh get(ServerType serverType, SshConfig config)
	{
		switch(serverType)
		{
			case UBUNTU:
			{
				return new UbuntuSsh(config);
			}
			case FEDORA:
			{
				throw new RuntimeException();
			}
			default:
			{
				throw new RuntimeException();
			}
		}
	}
	
	public ServerSsh(String host)
	{
		super(host);
	}
	
	public ServerSsh(SshConfig config)
	{
		super(config);
	}
	
	public LinkedList<String> mkdir(String...dirs)
	{
		StringBuilder command = new StringBuilder();
		for(String dir : dirs)
		{
			command.append(String.format("mkdir -p %s;", dir));
		}
		return exec(command.toString());
	}
	
	public LinkedList<String> rmdir(String...dirs)
	{
		StringBuilder command = new StringBuilder();
		for(String dir : dirs)
		{
			command.append(String.format("rm -rf %s;", dir));
		}
		return exec(command.toString());
	}
	
	public LinkedList<String> rm(String...files)
	{
		StringBuilder command = new StringBuilder();
		for(String file : files)
		{
			command.append(String.format("rm -f %s;", file));
		}
		return exec(command.toString());
	}
	
	public LinkedList<String> cp(String source, String dest)
	{
		String command = "cp %s %s;";
		return exec(String.format(command, source, dest));
	}
	
	public LinkedList<String> ln(String source, String target)
	{
		String command = "ln -sf %s %s;";
		return exec(String.format(command, source, target));
	}
	
	public LinkedList<String> untar(String source, String dest)
	{
		mkdir(dest);
		String command = "tar -xf %s -C %s --strip-components 1;";
		LinkedList<String> result = exec(String.format(command, source, dest));
		rm(source);
		return result;
	}
	
	public LinkedList<String> useradd(String group, String user)
	{
		String command = "groupadd -f %s; useradd -g %s %s;";
		return exec(String.format(command, group, group, user));
	}

	public LinkedList<String> chown(String group, String user, String file)
	{
		String command = "chown %s:%s %s;";
		return exec(String.format(command, group, user, file));
	}
	
	public LinkedList<String> chownDir(String group, String user, String dir)
	{
		String command = "chown -R %s:%s %s;";
		return exec(String.format(command, group, user, dir));
	}
	
	public LinkedList<String> passwd(String password)
	{
		String command = "(echo \"%s\"; echo \"%s\";) | passwd;";
		return exec(String.format(command, password, password));
	}
	
	public LinkedList<String> setTimeZone(String timeZone)
	{
		String command = "timedatectl set-timezone %s; date +%%Z;";
		return exec(String.format(command, timeZone));
	}
	
	public LinkedList<String> update()
	{
		return new LinkedList<String>();
	}
	
	public LinkedList<String> install(String app)
	{
		return new LinkedList<String>();
	}
	
	public LinkedList<String> clearIpTables()
	{
		String command = "iptables -P INPUT ACCEPT; iptables -P OUTPUT ACCEPT; iptables -P FORWARD ACCEPT; iptables -F;";
		return exec(command);
	}
	
	public LinkedList<String> setupFirewall(Map<Integer, Integer> forwardedPorts, Collection<Integer> allowedPorts, Collection<Integer> limitedPorts)
	{
		clearIpTables();
		exec("echo 'y' | ufw reset;");
		if(forwardedPorts != null && !forwardedPorts.isEmpty())
		{
			StringBuilder before = new StringBuilder();
			before.append("*nat\n");
			before.append(":PREROUTING ACCEPT [0:0]\n");
			for(Entry<Integer, Integer> entry : forwardedPorts.entrySet())
			{
				before.append(String.format("-A PREROUTING -p tcp --dport %d -j REDIRECT --to-port %d\n", entry.getKey(), entry.getValue()));
			}
			before.append("COMMIT\n");
			String beforeRules = "/etc/ufw/before.rules";
			String before6Rules = "/etc/ufw/before6.rules";
			String beforeTemp = "/etc/ufw/before.temp";
			String command = "echo -e '%s' | cat - %s > %s; mv %s %s;";
			exec(String.format(command, before.toString(), beforeRules, beforeTemp, beforeTemp, beforeRules));
			exec(String.format(command, before.toString(), before6Rules, beforeTemp, beforeTemp, before6Rules));
		}
		StringBuilder command = new StringBuilder();
		command.append("ufw default deny;");
		if(allowedPorts != null)
		{
			for(Integer allowedPort : allowedPorts)
			{
				command.append(String.format("ufw allow %d/tcp;", allowedPort));
			}
		}
		if(limitedPorts != null)
		{
			for(Integer limitedPort : limitedPorts)
			{
				command.append(String.format("ufw limit %d/tcp;", limitedPort));
			}
		}
		command.append("echo 'y' | ufw enable;");
		command.append("ufw status;");
		return exec(command.toString());
	}
	
	public LinkedList<String> systemctl(String command, String service)
	{
		return exec(String.format("systemctl %s %s;", command, service));
	}
	
	public LinkedList<String> systemctlStart(String service)
	{
		return systemctl("start", service);
	}
	
	public LinkedList<String> systemctlStop(String service)
	{
		return systemctl("stop", service);
	}
	
	public LinkedList<String> systemctlRestart(String service)
	{
		return systemctl("restart", service);
	}
	
	public LinkedList<String> systemctlStatus(String service)
	{
		return systemctl("status", service);
	}
	
	public LinkedList<String> systemctlEnable(String service)
	{
		return systemctl("enable", service);
	}
	
	public LinkedList<String> systemctlDisable(String service)
	{
		return systemctl("disable", service);
	}
	
	public LinkedList<String> systemctlDaemonReload()
	{
		return systemctl("daemon-reload", "");
	}
	
	public void putDir(File sourceDir, String destDir)
	{
		destDir = !destDir.endsWith("/") ? destDir + "/" : destDir;
		try(Sftp sftp = sftp();)
		{
			for(File source : sourceDir.listFiles())
			{
				if(source.exists() && source.isFile())
				{
					sftp.put(source, destDir + source.getName());
				}
			}
		}
	}
	
	public void put(File source, String dest)
	{
		try(Sftp sftp = sftp();)
		{
			sftp.put(source, dest);
		}
	}
	
	public void put(String source, String dest)
	{
		try(Sftp sftp = sftp();)
		{
			sftp.put(source, dest);
		}
	}
	
}
