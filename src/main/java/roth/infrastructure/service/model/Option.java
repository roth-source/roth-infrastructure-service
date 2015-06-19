package roth.infrastructure.service.model;

import java.io.Serializable;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class Option implements Serializable
{
	@Property(name = "name")
	protected String name;
	
	@Property(name = "value")
	protected String value;
	
	@Property(name = "selected")
	protected Boolean selected;
	
	public Option(String name, String value)
	{
		this(name, value, false);
	}
	
	public Option(String name, String value, Boolean selected)
	{
		this.name = name;
		this.value = value;
		this.selected = selected;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public Option setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public Option setValue(String value)
	{
		this.value = value;
		return this;
	}
	
}
