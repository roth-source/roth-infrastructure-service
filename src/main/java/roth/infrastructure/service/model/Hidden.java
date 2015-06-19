package roth.infrastructure.service.model;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class Hidden extends Field
{
	@Property(name = "value")
	protected String value;
	
	public Hidden(String name, String value)
	{
		super(name, name, "hidden", true);
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public Hidden setValue(String value)
	{
		this.value = value;
		return this;
	}
	
}
