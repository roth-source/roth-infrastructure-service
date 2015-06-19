package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.AdminSetting;
import roth.infrastructure.data.model.User;

public class AdminSettingTable extends Table<AdminSetting, String>
{
	
	protected AdminSettingTable(Db db, User user)
	{
		super(db, user, AdminSetting.class);
	}
	
	public static AdminSettingTable get(Db db, User user)
	{
		return new AdminSettingTable(db, user);
	}
	
}
