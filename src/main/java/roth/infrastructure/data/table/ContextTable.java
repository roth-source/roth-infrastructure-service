package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Context;
import roth.infrastructure.data.model.User;

public class ContextTable extends Table<Context, String>
{
	
	protected ContextTable(Db db, User user)
	{
		super(db, user, Context.class);
	}
	
	public static ContextTable get(Db db, User user)
	{
		return new ContextTable(db, user);
	}
	
}
