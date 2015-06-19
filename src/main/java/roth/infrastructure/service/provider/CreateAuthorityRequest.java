package roth.infrastructure.service.provider;

import roth.infrastructure.data.type.AuthorityType;
import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateAuthorityRequest extends IdRequest
{
	@Property(name = "type")
	protected AuthorityType type;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "apiKey")
	protected String apiKey;
	
	@Property(name = "username")
	protected String username;
	
	public CreateAuthorityRequest()
	{
		
	}
	
	public AuthorityType getType()
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
	
	public CreateAuthorityRequest setType(AuthorityType type)
	{
		this.type = type;
		return this;
	}
	
	public CreateAuthorityRequest setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public CreateAuthorityRequest setApiKey(String apiKey)
	{
		this.apiKey = apiKey;
		return this;
	}
	
	public CreateAuthorityRequest setUsername(String username)
	{
		this.username = username;
		return this;
	}
	
}
