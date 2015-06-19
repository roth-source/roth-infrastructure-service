package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdModel;
import roth.infrastructure.data.reference.DomainReference;
import roth.infrastructure.data.reference.NameserverReference;
import roth.infrastructure.data.reference.ServerReference;
import roth.infrastructure.data.references.VirtualhostReferences;
import roth.infrastructure.data.type.RecordType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "record")
@SuppressWarnings("serial")
public class Record extends IdModel implements NameserverReference, DomainReference, ServerReference, VirtualhostReferences
{
	@Property(name = "type")
	protected RecordType type;
	
	@Property(name = "nameserver_id")
	protected String nameserverId;
	
	@Property(name = "domain_id")
	protected String domainId;
	
	@Property(name = "server_id")
	protected String serverId;
	
	@Property(name = "server", rdb = false)
	protected Server server;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "value")
	protected String value;
	
	@Property(name = "routed")
	protected boolean routed;
	
	protected Record(Rdb rdb)
	{
		super(rdb);
	}
	
	public Record(Db db, User user, Nameserver nameserver, Domain domain, RecordType type, String name, Server server, boolean routed)
	{
		super(db, user);
		this.nameserverId = nameserver.getId();
		this.domainId = domain.getId();
		this.type = type;
		this.name = name;
		this.serverId = server.getId();
		this.routed = routed;
	}
	
	public Record(Db db, User user, Nameserver nameserver, Domain domain, RecordType type, String name, String value, boolean routed)
	{
		super(db, user);
		this.nameserverId = nameserver.getId();
		this.domainId = domain.getId();
		this.type = type;
		this.name = name;
		this.value = value;
		this.routed = routed;
	}
	
	@Override
	public Record mask()
	{
		return this;
	}
	
	public Record maskServer()
	{
		Server server = getServer();
		if(server != null)
		{
			server.name = server.getDisplayName();
			this.server = server;
		}
		return this;
	}
	
	@Override
	public String getNameserverId()
	{
		return nameserverId;
	}
	
	@Override
	public String getDomainId()
	{
		return domainId;
	}
	
	@Override
	public String getServerId()
	{
		return serverId;
	}
	
	public RecordType getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public boolean isRouted()
	{
		return routed;
	}
	
	public Record setNameserverId(String nameserverId)
	{
		this.nameserverId = setDirty("nameserverId", this.nameserverId, nameserverId);
		return this;
	}
	
	public Record setDomainId(String domainId)
	{
		this.domainId = setDirty("domainId", this.domainId, domainId);
		return this;
	}
	
	public Record setServerId(String serverId)
	{
		this.serverId = setDirty("serverId", this.serverId, serverId);
		return this;
	}
	
	public Record setType(RecordType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
	public Record setName(String name)
	{
		this.name = setDirty("name", this.name, name);
		return this;
	}
	
	public Record setValue(String value)
	{
		this.value = setDirty("value", this.value, value);
		return this;
	}
	
	public Record setRouted(boolean routed)
	{
		this.routed = setDirty("routed", this.routed, routed);
		return this;
	}
	
}
