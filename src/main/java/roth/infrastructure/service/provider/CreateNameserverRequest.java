package roth.infrastructure.service.provider;

import roth.infrastructure.data.type.NameserverType;
import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateNameserverRequest extends IdRequest
{
	@Property(name = "type")
	protected NameserverType type;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "apiKey")
	protected String apiKey;
	
	@Property(name = "username")
	protected String username;
	
	public CreateNameserverRequest()
	{
		
	}
	
	public NameserverType getType()
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
	
	public CreateNameserverRequest setType(NameserverType type)
	{
		this.type = type;
		return this;
	}
	
	public CreateNameserverRequest setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public CreateNameserverRequest setApiKey(String apiKey)
	{
		this.apiKey = apiKey;
		return this;
	}
	
	public CreateNameserverRequest setUsername(String username)
	{
		this.username = username;
		return this;
	}
	
}
