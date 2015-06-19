package roth.infrastructure.service.app;

import roth.infrastructure.data.type.EnvType;
import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateEnvRequest extends IdRequest
{
	@Property(name = "type")
	protected EnvType type;
	
	@Property(name = "name")
	protected String name;
	
	public CreateEnvRequest()
	{
		
	}
	
	public EnvType getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public CreateEnvRequest setType(EnvType type)
	{
		this.type = type;
		return this;
	}
	
	public CreateEnvRequest setName(String name)
	{
		this.name = name;
		return this;
	}
	
}
