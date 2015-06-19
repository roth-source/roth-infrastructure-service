package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Deploy;
import roth.infrastructure.data.model.User;

public class DeployTable extends Table<Deploy, String>
{
	
	protected DeployTable(Db db, User user)
	{
		super(db, user, Deploy.class);
	}
	
	public static DeployTable get(Db db, User user)
	{
		return new DeployTable(db, user);
	}
	
}
