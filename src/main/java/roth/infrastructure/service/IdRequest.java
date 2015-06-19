package roth.infrastructure.service;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class IdRequest extends ServiceRequest
{
	@Property(name = "id")
	protected String id;
	
	public IdRequest()
	{
		
	}
	
	public String getId()
	{
		return id;
	}
	
	public IdRequest setId(String id)
	{
		this.id = id;
		return this;
	}
	
}
