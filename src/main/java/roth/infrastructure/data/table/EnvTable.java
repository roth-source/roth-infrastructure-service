package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Env;
import roth.infrastructure.data.model.User;

public class EnvTable extends Table<Env, String>
{
	
	protected EnvTable(Db db, User user)
	{
		super(db, user, Env.class);
	}
	
	public static EnvTable get(Db db, User user)
	{
		return new EnvTable(db, user);
	}
	
}
