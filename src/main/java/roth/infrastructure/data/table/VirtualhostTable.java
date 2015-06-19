package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Virtualhost;
import roth.infrastructure.data.model.User;

public class VirtualhostTable extends Table<Virtualhost, String>
{
	
	protected VirtualhostTable(Db db, User user)
	{
		super(db, user, Virtualhost.class);
	}
	
	public static VirtualhostTable get(Db db, User user)
	{
		return new VirtualhostTable(db, user);
	}
	
}
