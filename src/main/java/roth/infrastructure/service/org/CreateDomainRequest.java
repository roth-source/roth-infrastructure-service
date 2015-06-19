package roth.infrastructure.service.org;

import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateDomainRequest extends IdRequest
{
	@Property(name = "registrarId")
	protected String registrarId;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "expiresOn")
	protected String expiresOn;
	
	public CreateDomainRequest()
	{
		
	}
	
	public String getRegistrarId()
	{
		return registrarId;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getExpiresOn()
	{
		return expiresOn;
	}
	
	public CreateDomainRequest setRegistrarId(String registrarId)
	{
		this.registrarId = registrarId;
		return this;
	}
	
	public CreateDomainRequest setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public CreateDomainRequest setExpiresOn(String expiresOn)
	{
		this.expiresOn = expiresOn;
		return this;
	}
	
}
