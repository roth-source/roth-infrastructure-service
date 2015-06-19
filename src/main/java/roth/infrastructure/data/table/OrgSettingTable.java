package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.OrgSetting;
import roth.infrastructure.data.model.User;

public class OrgSettingTable extends Table<OrgSetting, String>
{
	
	protected OrgSettingTable(Db db, User user)
	{
		super(db, user, OrgSetting.class);
	}
	
	public static OrgSettingTable get(Db db, User user)
	{
		return new OrgSettingTable(db, user);
	}
	
}
