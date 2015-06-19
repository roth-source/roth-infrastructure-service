package roth.infrastructure.data.model;

import java.util.Calendar;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdOrgModel;
import roth.infrastructure.data.reference.HostReference;
import roth.infrastructure.data.references.InstallReferences;
import roth.infrastructure.data.references.RecordReferences;
import roth.infrastructure.data.type.ServerType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "server")
@SuppressWarnings("serial")
public class Server extends IdOrgModel implements HostReference, InstallReferences, RecordReferences
{
	@Property(name = "type")
	protected ServerType type;
	
	@Property(name = "host_id")
	protected String hostId;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "ip")
	protected String ip;
	
	@Property(name = "key")
	protected String key;
	
	@Property(name = "details")
	protected String details;
	
	@Property(name = "createdOn")
	protected Calendar createdOn;
	
	@Property(name = "destroyedOn")
	protected Calendar destroyedOn;
	
	protected Server(Rdb rdb)
	{
		super(rdb);
	}
	
	public Server(Db db, User user, Host host, ServerType type, String name)
	{
		super(db, user);
		this.hostId = host.getId();
		this.type = type;
		this.name = name;
	}
	
	public Server(Db db, User user, Org org, ServerType type, String name)
	{
		super(db, user, org);
		this.type = type;
		this.name = name;
	}
	
	@Override
	public Server mask()
	{
		return this;
	}
	
	@Override
	public String getHostId()
	{
		return hostId;
	}
	
	public ServerType getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getIp()
	{
		return ip;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public String getDetails()
	{
		return details;
	}
	
	public Calendar getCreatedOn()
	{
		return createdOn;
	}
	
	public Calendar getDestroyedOn()
	{
		return destroyedOn;
	}
	
	public String getDisplayName()
	{
		return name + " - " + getHost().getDisplayName();
	}
	
	public Server setHostId(String hostId)
	{
		this.hostId = setDirty("hostId", this.hostId, hostId);
		return this;
	}
	
	public Server setType(ServerType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
	public Server setName(String name)
	{
		this.name = setDirty("name", this.name, name);
		return this;
	}
	
	public Server setIp(String ip)
	{
		this.ip = setDirty("ip", this.ip, ip);
		return this;
	}
	
	public Server setKey(String key)
	{
		this.key = setDirty("key", this.key, key);
		return this;
	}
	
	public Server setDetails(String details)
	{
		this.details = setDirty("details", this.details, details);
		return this;
	}
	
	public Server setCreatedOn(Calendar createdOn)
	{
		this.createdOn = setDirty("createdOn", this.createdOn, createdOn);
		return this;
	}
	
	public Server setDestroyedOn(Calendar destroyedOn)
	{
		this.destroyedOn = setDirty("destroyedOn", this.destroyedOn, destroyedOn);
		return this;
	}
	
}
