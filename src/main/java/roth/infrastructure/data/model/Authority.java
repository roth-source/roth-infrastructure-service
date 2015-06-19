package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Provider;
import roth.infrastructure.data.references.CertReferences;
import roth.infrastructure.data.type.AuthorityType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "authority")
@SuppressWarnings("serial")
public class Authority extends Provider implements CertReferences
{
	@Property(name = "type")
	protected AuthorityType type;
	
	protected Authority(Rdb rdb)
	{
		super(rdb);
	}
	
	public Authority(Db db, User user, Org org, AuthorityType type, String name, String apiKey)
	{
		super(db, user, org, name, apiKey);
		this.type = type;
	}
	
	@Override
	public AuthorityType getType()
	{
		return type;
	}
	
	public Authority setType(AuthorityType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
}
