package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.model.Webserver;

public class WebserverTable extends Table<Webserver, String>
{
	
	protected WebserverTable(Db db, User user)
	{
		super(db, user, Webserver.class);
	}
	
	public static WebserverTable get(Db db, User user)
	{
		return new WebserverTable(db, user);
	}
	
}
