package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdAppModel;
import roth.infrastructure.data.references.TargetReferences;
import roth.infrastructure.data.references.WebserverReferences;
import roth.infrastructure.data.type.EnvType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "env")
@SuppressWarnings("serial")
public class Env extends IdAppModel implements TargetReferences, WebserverReferences
{
	@Property(name = "type")
	protected EnvType type;
	
	@Property(name = "name")
	protected String name;
	
	protected Env(Rdb rdb)
	{
		super(rdb);
	}
	
	public Env(Db db, User user, App app, EnvType type, String name)
	{
		super(db, user, app);
		this.name = name;
		this.type = type;
	}
	
	@Override
	public Env mask()
	{
		return this;
	}
	
	public EnvType getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Env setType(EnvType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
	public Env setName(String name)
	{
		this.name = setDirty("name", this.name, name);
		return this;
	}
	
}
