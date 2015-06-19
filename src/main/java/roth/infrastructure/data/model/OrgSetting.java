package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdOrgModel;
import roth.infrastructure.data.reference.OrgReference;
import roth.infrastructure.data.type.OrgSettingType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "setting")
@SuppressWarnings("serial")
public class OrgSetting extends IdOrgModel implements OrgReference
{
	@Property(name = "type")
	protected OrgSettingType type;
	
	@Property(name = "value")
	protected String value;
	
	protected OrgSetting(Rdb rdb)
	{
		super(rdb);
	}
	
	public OrgSetting(Db db, User user, Org org, OrgSettingType type)
	{
		super(db, user, org);
		this.type = type;
	}
	
	@Override
	public OrgSetting mask()
	{
		return this;
	}
	
	public OrgSettingType getOrgType()
	{
		return type;
	}
	
	public OrgSetting setType(OrgSettingType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
}
