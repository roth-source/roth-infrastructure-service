package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdModel;
import roth.infrastructure.data.type.AdminSettingType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "setting")
@SuppressWarnings("serial")
public class AdminSetting extends IdModel
{
	@Property(name = "type")
	protected AdminSettingType type;
	
	@Property(name = "value")
	protected String value;
	
	protected AdminSetting(Rdb rdb)
	{
		super(rdb);
	}
	
	public AdminSetting(Db db, User user, AdminSettingType type)
	{
		super(db, user);
		this.type = type;
	}
	
	@Override
	public AdminSetting mask()
	{
		return this;
	}
	
	public AdminSettingType getType()
	{
		return type;
	}
	
	public AdminSetting setType(AdminSettingType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
}
