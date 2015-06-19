package roth.infrastructure.task;

import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import roth.infrastructure.Constants;
import roth.infrastructure.data.Db;
import roth.infrastructure.data.model.App;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.table.UserTable;
import roth.infrastructure.ssh.ServerSsh;
import roth.lib.util.FileUtil;

public abstract class SetupServerTask extends Task implements Constants
{
	protected App app;
	
	public SetupServerTask(Db db, User user, HttpSession session, String taskId, App app)
	{
		super(db, user, session, taskId);
		this.app = app;
	}
	
	protected void setupAuthorizedKeys(ServerSsh ssh)
	{
		startAction("SETUP AUTHORIZED KEYS");
		
		StringBuilder authorizedKeys = new StringBuilder();
		authorizedKeys.append("# infrastructure\n");
		authorizedKeys.append(FileUtil.toString(config.getPublicKeyFile()));
		authorizedKeys.append("\n\n");
		LinkedList<User> users = UserTable.get(db).findAllByOrg(app.getOrg());
		for(User user : users)
		{
			String publicKey = user.getPublicKey();
			if(publicKey != null && !publicKey.isEmpty())
			{
				authorizedKeys.append("# " + user.getEmail());
				authorizedKeys.append("\n");
				authorizedKeys.append(publicKey);
				authorizedKeys.append("\n\n");
			}
		}
		
		updateAction("copying generated authorized_keys");
		ssh.put(authorizedKeys.toString(), ".ssh/authorized_keys");
		
		endAction();
	}
	
	
}
