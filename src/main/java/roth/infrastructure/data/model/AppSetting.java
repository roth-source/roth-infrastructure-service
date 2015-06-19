package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdAppModel;
import roth.infrastructure.data.reference.AppReference;
import roth.infrastructure.data.type.AppSettingType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "setting")
@SuppressWarnings("serial")
public class AppSetting extends IdAppModel implements AppReference
{
	@Property(name = "type")
	protected AppSettingType type;
	
	@Property(name = "value")
	protected String value;
	
	protected AppSetting(Rdb rdb)
	{
		super(rdb);
	}
	
	public AppSetting(Db db, User user, App app, AppSettingType type)
	{
		super(db, user, app);
		this.type = type;
	}
	
	@Override
	public AppSetting mask()
	{
		return this;
	}
	
	public AppSettingType getAppType()
	{
		return type;
	}
	
	public AppSetting setType(AppSettingType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
}
