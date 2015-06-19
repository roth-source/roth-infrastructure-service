package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Provider;
import roth.infrastructure.data.references.DomainReferences;
import roth.infrastructure.data.type.RepoType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "repo")
@SuppressWarnings("serial")
public class Repo extends Provider implements DomainReferences
{
	@Property(name = "type")
	protected RepoType type;
	
	@Property(name = "password", json = false)
	protected String password;
	
	protected Repo(Rdb rdb)
	{
		super(rdb);
	}
	
	public Repo(Db db, User user, Org org, RepoType type, String name)
	{
		super(db, user, org, name);
		this.type = type;
	}
	
	@Override
	public RepoType getType()
	{
		return type;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public Repo setType(RepoType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
	public Repo setPassword(String password)
	{
		this.password = setDirty("password", this.password, password);
		return this;
	}
	
}
