package roth.infrastructure.service.org;

import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateAppRequest extends IdRequest
{
	@Property(name = "name")
	protected String name;
	
	public CreateAppRequest()
	{
		
	}
	
	public String getName()
	{
		return name;
	}
	
	public CreateAppRequest setName(String name)
	{
		this.name = name;
		return this;
	}
	
}
