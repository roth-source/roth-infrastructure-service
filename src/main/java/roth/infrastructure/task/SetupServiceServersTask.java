package roth.infrastructure.task;

import java.io.File;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.model.App;
import roth.infrastructure.data.model.Dist;
import roth.infrastructure.data.model.Install;
import roth.infrastructure.data.model.Server;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.table.InstallTable;
import roth.infrastructure.data.table.ServerTable;
import roth.infrastructure.data.type.DistType;
import roth.infrastructure.data.type.ServerType;
import roth.infrastructure.service.app.SetupServiceServersRequest;
import roth.infrastructure.ssh.ServerSsh;
import roth.lib.util.ResourceUtil;

public class SetupServiceServersTask extends SetupServerTask
{
	protected SetupServiceServersRequest request;
	
	public SetupServiceServersTask(Db db, User user, HttpSession session, String taskId, App app, SetupServiceServersRequest request)
	{
		super(db, user, session, taskId, app);
		this.request = request;
		
	}
	
	@Override
	public void run()
	{
		for(String serverId : request.getServerIds())
		{
			Server server = ServerTable.get(db, user).findById(serverId);
			setupServer(server);
			//new AppServer(db, app, server).insert();
		}
		completeTask();
	}
	
	protected void setupServer(Server server)
	{
		startAction("CONNECT SSH TO " + server.getDisplayName());
		ServerType serverType = server.getType();
		try(ServerSsh ssh = connectSsh(serverType, server.getIp());)
		{
			setupAuthorizedKeys(ssh);
			setupJdk(ssh);
			setupJetty(ssh);
			setupMysql(ssh);
		}
	}
	
	protected void setupJdk(ServerSsh ssh)
	{
		startAction("SETUP JDK");
		
		updateAction("making dirs");
		ssh.mkdir(JDK_DIST);
		
		Install appDist = InstallTable.get(db, user).findByAppAndDistType(app, DistType.JDK);
		if(appDist != null)
		{
			Dist dist = appDist.getDist();
			String name = dist.getName();
			String distPath = String.format("%s/%s", JDK_DIST, name);
			String archive = name + ".tar.gz";
			String archivePath = String.format("%s/%s", JDK_DIST, archive);
			
			updateAction("copying " + archive);
			File distFile = new File(config.getDataDir(), "dist/jdk/" + archive);
			ssh.put(distFile, archivePath);
			
			updateAction("unarchiving dist", true);
			ssh.untar(archivePath, distPath);
			
			updateAction("linking home");
			ssh.ln(distPath, JDK_HOME);
		}
		else
		{
			// error
		}
		endAction();
	}
	
	protected void setupJetty(ServerSsh ssh)
	{
		startAction("SETUP JETTY");
		
		updateAction("making dirs");
		ssh.mkdir(JETTY_DIST, JETTY_BASE, JETTY_BASE_START_D, JETTY_BASE_CONF, JETTY_BASE_CERT, JETTY_BASE_LOGS, JETTY_BASE_WEBAPPS, JETTY_BASE_TEMP);
		
		Install appDist = InstallTable.get(db, user).findByAppAndDistType(app, DistType.JETTY);
		if(appDist != null)
		{
			Dist dist = appDist.getDist();
			String name = dist.getName();
			String distPath = String.format("%s/%s", JETTY_DIST, name);
			String archive = name + ".tar.gz";
			String archivePath = String.format("%s/%s", JETTY_DIST, archive);
			
			updateAction("copying " + archive);
			File distFile = new File(config.getDataDir(), "dist/jetty/" + archive);
			ssh.put(distFile, archivePath);
			
			updateAction("unarchiving dist", true);
			ssh.untar(archivePath, distPath);
			
			updateAction("linking home");
			ssh.ln(distPath, JETTY_HOME);
			
			String group = "jetty";
			String user = "jetty";
			
			updateAction("creating jetty:jetty", true);
			ssh.useradd(group, user);
			
			updateAction("changing owner");
			ssh.chownDir(group, user, JETTY);
			
			updateAction("copying env variables");
			String baseEnv = ResourceUtil.toString("roth/infrastructure/base/linux/bash_env");
			ssh.put(baseEnv, "/etc/default/jetty");
			
			updateAction("installing base", true);
			LinkedList<String> startdFiles = ResourceUtil.listPaths("roth/infrastructure/base/jetty/start.d/");
			for(String startdFile : startdFiles)
			{
				String startdData = ResourceUtil.toString("roth/infrastructure/base/jetty/start.d/" + startdFile);
				ssh.put(startdData, JETTY_BASE_START_D + "/" + startdFile);
			}
			String keystore = ResourceUtil.toString("roth/infrastructure/base/jetty/cert/keystore");
			ssh.put(keystore, JETTY_BASE_CERT + "/keystore");
			
			updateAction("enabling systemctl");
			ssh.cp(JETTY_HOME + "/bin/jetty.sh", "/etc/init.d/jetty");
			ssh.systemctlDaemonReload();
			ssh.systemctlEnable("jetty");
			ssh.systemctlStart("jetty");
			
		}
		else
		{
			// error
		}
		endAction();
	}
	
	protected void setupMysql(ServerSsh ssh)
	{
		startAction("SETUP MYSQL");
		
		updateAction("making dirs");
		ssh.mkdir(MYSQL_DIST, MYSQL_BASE, MYSQL_BASE_CONF, MYSQL_BASE_DATA);
		
		Install appDist = InstallTable.get(db, user).findByAppAndDistType(app, DistType.MYSQL);
		if(appDist != null)
		{
			Dist dist = appDist.getDist();
			String name = dist.getName();
			String distPath = String.format("%s/%s", MYSQL_DIST, name);
			String archive = name + ".tar.gz";
			String archivePath = String.format("%s/%s", MYSQL_DIST, archive);
			
			updateAction("copying " + archive);
			File distFile = new File(config.getDataDir(), "dist/mysql/" + archive);
			ssh.put(distFile, archivePath);
			
			updateAction("unarchiving dist", true);
			ssh.untar(archivePath, distPath);
			
			updateAction("linking home");
			ssh.ln(distPath, MYSQL_HOME);
			
			updateAction("installing libaio");
			ssh.install("libaio1");
			
			String group = "mysql";
			String user = "mysql";
			
			updateAction("creating mysql:mysql", true);
			ssh.useradd(group, user);
			
			updateAction("changing owner");
			ssh.chownDir(group, user, MYSQL);
			
			updateAction("copying my.cnf");
			String myConf = ResourceUtil.toString("roth/infrastructure/base/mysql/conf/my.cnf");
			ssh.put(myConf, MYSQL_BASE_CONF + "/my.cnf");
			
			updateAction("copying mysql.service", true);
			String mysqlService = ResourceUtil.toString("roth/infrastructure/base/mysql/conf/mysql.service");
			ssh.put(mysqlService, "/etc/systemd/system/mysql.service");
			
			updateAction("enabling mysql");
			ssh.exec(String.format("%s/scripts/mysql_install_db --defaults-file=%s/my.cnf --user=mysql --basedir=%s --datadir=%s;", MYSQL_HOME, MYSQL_BASE_CONF, MYSQL_HOME, MYSQL_BASE_DATA));
			ssh.systemctlDaemonReload();
			ssh.systemctlEnable("mysql");
			ssh.systemctlStart("mysql");
			
		}
		else
		{
			// error
		}
		endAction();
	}
	
	public static void main(String[] args)
	{
		LinkedList<String> paths = ResourceUtil.listPaths("roth/infrastructure/base/jetty/start.d/");
		for(String path : paths)
		{
			System.out.println(path);
		}
	}
	
}
