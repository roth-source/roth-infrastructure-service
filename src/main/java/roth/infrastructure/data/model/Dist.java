package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdModel;
import roth.infrastructure.data.references.InstallReferences;
import roth.infrastructure.data.type.DistType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "dist")
@SuppressWarnings("serial")
public class Dist extends IdModel implements InstallReferences
{
	@Property(name = "type")
	protected DistType type;
	
	@Property(name = "name")
	protected String name;
	
	protected Dist(Rdb rdb)
	{
		super(rdb);
	}
	
	public Dist(Db db, User user, DistType type, String name)
	{
		super(db, user);
		this.type = type;
		this.name = name;
	}
	
	@Override
	public Dist mask()
	{
		return this;
	}
	
	public DistType getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Dist setType(DistType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
	public Dist setName(String name)
	{
		this.name = setDirty("name", this.name, name);
		return this;
	}
	
}
