package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdOrgModel;
import roth.infrastructure.data.type.PackageType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "package")
@SuppressWarnings("serial")
public class Package extends IdOrgModel
{
	@Property(name = "type")
	protected PackageType type;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "excludes")
	protected String excludes;
	
	protected Package(Rdb rdb)
	{
		super(rdb);
	}
	
	public Package(Db db, User user, Org org, PackageType type, String name)
	{
		super(db, user, org);
		this.type = type;
		this.name = name;
	}
	
	@Override
	public Package mask()
	{
		return this;
	}
	
	public PackageType getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getExcludes()
	{
		return excludes;
	}
	
	public Package setType(PackageType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
	public Package setName(String name)
	{
		this.name = setDirty("name", this.name, name);
		return this;
	}
	
	public Package setExcludes(String excludes)
	{
		this.excludes = setDirty("excludes", this.excludes, excludes);
		return this;
	}
	
}
