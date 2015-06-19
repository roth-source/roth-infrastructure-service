package roth.infrastructure.service.provider;

import roth.infrastructure.data.type.RepoType;
import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateRepoRequest extends IdRequest
{
	@Property(name = "type")
	protected RepoType type;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "apiKey")
	protected String apiKey;
	
	@Property(name = "username")
	protected String username;

	@Property(name = "password")
	protected String password;
	
	public CreateRepoRequest()
	{
		
	}
	
	public RepoType getType()
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
	
	public String getPassword()
	{
		return password;
	}
	
	public CreateRepoRequest setType(RepoType type)
	{
		this.type = type;
		return this;
	}
	
	public CreateRepoRequest setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public CreateRepoRequest setApiKey(String apiKey)
	{
		this.apiKey = apiKey;
		return this;
	}
	
	public CreateRepoRequest setUsername(String username)
	{
		this.username = username;
		return this;
	}
	
	public CreateRepoRequest setPassword(String password)
	{
		this.password = password;
		return this;
	}
	
}
