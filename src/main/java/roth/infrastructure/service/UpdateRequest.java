package roth.infrastructure.service;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class UpdateRequest extends IdRequest
{
	@Property(name = "key")
	protected String key;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "value")
	protected String value;
	
	public UpdateRequest()
	{
		
	}
	
	public String getKey()
	{
		return key;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public UpdateRequest setKey(String key)
	{
		this.key = key;
		return this;
	}
	
	public UpdateRequest setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public UpdateRequest setValue(String value)
	{
		this.value = value;
		return this;
	}
	
}
