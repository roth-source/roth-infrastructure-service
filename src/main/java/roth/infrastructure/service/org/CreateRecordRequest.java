package roth.infrastructure.service.org;

import roth.infrastructure.data.type.RecordType;
import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateRecordRequest extends IdRequest
{
	@Property(name = "type")
	protected RecordType type;
	
	@Property(name = "nameserverId")
	protected String nameserverId;
	
	@Property(name = "domainId")
	protected String domainId;
	
	@Property(name = "serverId")
	protected String serverId;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "value")
	protected String value;
	
	@Property(name = "routed")
	protected boolean routed;
	
	public CreateRecordRequest()
	{
		
	}
	
	public RecordType getType()
	{
		return type;
	}
	
	public String getNameserverId()
	{
		return nameserverId;
	}
	
	public String getDomainId()
	{
		return domainId;
	}
	
	public String getServerId()
	{
		return !"null".equalsIgnoreCase(serverId) ? serverId : null;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getValue()
	{
		return getServerId() == null ? value : null;
	}
	
	public boolean isRouted()
	{
		return routed;
	}
	
	public CreateRecordRequest setType(RecordType type)
	{
		this.type = type;
		return this;
	}
	
	public CreateRecordRequest setNameserverId(String nameserverId)
	{
		this.nameserverId = nameserverId;
		return this;
	}
	
	public CreateRecordRequest setDomainId(String domainId)
	{
		this.domainId = domainId;
		return this;
	}
	
	public CreateRecordRequest setServerId(String serverId)
	{
		this.serverId = serverId;
		return this;
	}
	
	public CreateRecordRequest setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public CreateRecordRequest setValue(String value)
	{
		this.value = value;
		return this;
	}
	
	public CreateRecordRequest setRouted(boolean routed)
	{
		this.routed = routed;
		return this;
	}
	
}
