package roth.infrastructure.service;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class IdResponse extends ServiceResponse
{
	@Property(name = "id")
	protected String id;
	
	public IdResponse()
	{
		
	}
	
	public String getId()
	{
		return id;
	}
	
	public IdResponse setId(String id)
	{
		this.id = id;
		return this;
	}
	
}
