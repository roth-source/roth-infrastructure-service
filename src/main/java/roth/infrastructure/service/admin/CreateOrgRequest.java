package roth.infrastructure.service.admin;

import roth.infrastructure.service.ServiceRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateOrgRequest extends ServiceRequest
{
	@Property(name = "name")
	protected String name;
	
	public CreateOrgRequest()
	{
		
	}
	
	public String getName()
	{
		return name;
	}
	
	public CreateOrgRequest setName(String name)
	{
		this.name = name;
		return this;
	}
	
}
