package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Provider;
import roth.infrastructure.data.references.RecordReferences;
import roth.infrastructure.data.type.NameserverType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "nameserver")
@SuppressWarnings("serial")
public class Nameserver extends Provider implements RecordReferences
{
	@Property(name = "type")
	protected NameserverType type;
	
	protected Nameserver(Rdb rdb)
	{
		super(rdb);
	}
	
	public Nameserver(Db db, User user, Org org, NameserverType type, String name, String apiKey)
	{
		super(db, user, org, name, apiKey);
		this.type = type;
	}
	
	@Override
	public NameserverType getType()
	{
		return type;
	}
	
	public Nameserver setType(NameserverType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
}
