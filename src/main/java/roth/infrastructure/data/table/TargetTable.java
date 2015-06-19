package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Target;
import roth.infrastructure.data.model.User;

public class TargetTable extends Table<Target, String>
{
	
	protected TargetTable(Db db, User user)
	{
		super(db, user, Target.class);
	}
	
	public static TargetTable get(Db db, User user)
	{
		return new TargetTable(db, user);
	}
	
}
