package roth.infrastructure.service.provider;

import roth.infrastructure.data.type.HostType;
import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateHostRequest extends IdRequest
{
	@Property(name = "type")
	protected HostType type;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "apiKey")
	protected String apiKey;
	
	@Property(name = "username")
	protected String username;
	
	public CreateHostRequest()
	{
		
	}
	
	public HostType getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getApiKey()
	{
		return apiKey;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public CreateHostRequest setType(HostType type)
	{
		this.type = type;
		return this;
	}
	
	public CreateHostRequest setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public CreateHostRequest setApiKey(String apiKey)
	{
		this.apiKey = apiKey;
		return this;
	}
	
	public CreateHostRequest setUsername(String username)
	{
		this.username = username;
		return this;
	}
	
}
