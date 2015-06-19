package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.App;
import roth.infrastructure.data.model.User;

public class AppTable extends Table<App, String>
{
	
	protected AppTable(Db db, User user)
	{
		super(db, user, App.class);
	}
	
	public static AppTable get(Db db, User user)
	{
		return new AppTable(db, user);
	}
	
}
