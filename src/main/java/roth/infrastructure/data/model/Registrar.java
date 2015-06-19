package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Provider;
import roth.infrastructure.data.references.DomainReferences;
import roth.infrastructure.data.type.RegistrarType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "registrar")
@SuppressWarnings("serial")
public class Registrar extends Provider implements DomainReferences
{
	@Property(name = "type")
	protected RegistrarType type;
	
	protected Registrar(Rdb rdb)
	{
		super(rdb);
	}
	
	public Registrar(Db db, User user, Org org, RegistrarType type, String name, String apiKey)
	{
		super(db, user, org, name, apiKey);
		this.type = type;
	}
	
	@Override
	public RegistrarType getType()
	{
		return type;
	}
	
	public Registrar setType(RegistrarType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
}
