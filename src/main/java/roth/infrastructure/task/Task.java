package roth.infrastructure.task;

import java.util.concurrent.Callable;

import javax.servlet.http.HttpSession;

import roth.infrastructure.Constants;
import roth.infrastructure.config.Config;
import roth.infrastructure.config.Environment;
import roth.infrastructure.data.Db;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.type.ServerType;
import roth.infrastructure.ssh.ServerSsh;
import roth.lib.net.http.task.HttpTask;
import roth.lib.net.ssh.SshConfig;
import roth.lib.net.ssh.SshException;
import roth.lib.util.ThreadUtil;

public abstract class Task extends HttpTask implements Runnable, Callable<Boolean>, Constants
{
	protected Db db;
	protected Environment environment;
	protected Config config;
	protected User user;
	
	public Task(Db db, User user, HttpSession session, String taskId)
	{
		super(session, taskId);
		this.db = db;
		this.environment = db.getEnvironment();
		this.config = db.getConfig();
		this.user = user;
	}
	
	protected ServerSsh connectSsh(ServerType serverType, String ip)
	{
		updateAction("connecting to " + ip);
		ServerSsh ssh = connectSsh(serverType, ip, 1, 10);
		endAction();
		return ssh;
	}
	
	protected ServerSsh connectSsh(ServerType serverType, String ip, int attempt, int max)
	{
		if(attempt < max)
		{
			if(attempt >= 2)
			{
				updateAction("attempt " + attempt, attempt == 2);
			}
			SshConfig config = new SshConfig(ip);
			try
			{
				return ServerSsh.get(serverType, config);
			}
			catch(SshException e)
			{
				ThreadUtil.wait(10);
				return connectSsh(serverType, ip, attempt++, max);
			}
		}
		else
		{
			throw new SshException("ssh failed");
		}
	}
	
}
