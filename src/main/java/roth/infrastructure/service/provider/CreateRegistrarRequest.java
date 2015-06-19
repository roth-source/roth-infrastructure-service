package roth.infrastructure.service.provider;

import roth.infrastructure.data.type.RegistrarType;
import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateRegistrarRequest extends IdRequest
{
	@Property(name = "type")
	protected RegistrarType type;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "apiKey")
	protected String apiKey;
	
	@Property(name = "username")
	protected String username;
	
	public CreateRegistrarRequest()
	{
		
	}
	
	public RegistrarType getType()
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
	
	public CreateRegistrarRequest setType(RegistrarType type)
	{
		this.type = type;
		return this;
	}
	
	public CreateRegistrarRequest setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public CreateRegistrarRequest setApiKey(String apiKey)
	{
		this.apiKey = apiKey;
		return this;
	}
	
	public CreateRegistrarRequest setUsername(String username)
	{
		this.username = username;
		return this;
	}
	
}
