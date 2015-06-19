package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Release;
import roth.infrastructure.data.model.User;

public class ReleaseTable extends Table<Release, String>
{
	
	protected ReleaseTable(Db db, User user)
	{
		super(db, user, Release.class);
	}
	
	public static ReleaseTable get(Db db, User user)
	{
		return new ReleaseTable(db, user);
	}
	
}
