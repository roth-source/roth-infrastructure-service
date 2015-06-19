package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.AppSetting;
import roth.infrastructure.data.model.User;

public class AppSettingTable extends Table<AppSetting, String>
{
	
	protected AppSettingTable(Db db, User user)
	{
		super(db, user, AppSetting.class);
	}
	
	public static AppSettingTable get(Db db, User user)
	{
		return new AppSettingTable(db, user);
	}
	
}
