package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Provider;
import roth.infrastructure.data.references.ServerReferences;
import roth.infrastructure.data.type.HostType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "host")
@SuppressWarnings("serial")
public class Host extends Provider implements ServerReferences
{
	@Property(name = "type")
	protected HostType type;
	
	@Property(name = "sshKeyId", json = false)
	protected String sshKeyId;
	
	protected Host(Rdb rdb)
	{
		super(rdb);
	}
	
	public Host(Db db, User user, Org org, HostType type, String name, String apiKey)
	{
		super(db, user, org, name, apiKey);
		this.type = type;
		this.apiKey = apiKey;
	}
	
	@Override
	public HostType getType()
	{
		return type;
	}
	
	public String getSshKeyId()
	{
		return sshKeyId;
	}
	
	public Host setType(HostType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
	public Host setSshKeyId(String sshKeyId)
	{
		this.sshKeyId = setDirty("sshKeyId", this.sshKeyId, sshKeyId);
		return this;
	}
	
}
