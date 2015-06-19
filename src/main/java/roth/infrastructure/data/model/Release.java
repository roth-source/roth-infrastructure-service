package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdAppModel;
import roth.infrastructure.data.type.ReleaseType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "release")
@SuppressWarnings("serial")
public class Release extends IdAppModel
{
	@Property(name = "type")
	protected ReleaseType type;
	
	@Property(name = "version")
	protected String version;
	
	protected Release(Rdb rdb)
	{
		super(rdb);
	}
	
	public Release(Db db, User user, App app, ReleaseType type, String version)
	{
		super(db, user, app);
		this.type = type;
		this.version = version;
	}
	
	@Override
	public Release mask()
	{
		return this;
	}
	
	public ReleaseType getType()
	{
		return type;
	}
	
	public String getVersion()
	{
		return version;
	}
	
	public Release setType(ReleaseType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
	public Release setVersion(String version)
	{
		this.version = setDirty("version", this.version, version);
		return this;
	}
	
}
