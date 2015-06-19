package roth.infrastructure.service.model;

import java.io.Serializable;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public abstract class Field implements Serializable
{
	@Property(name = "name")
	protected String name;
	
	@Property(name = "label")
	protected String label;
	
	@Property(name = "placeholder")
	protected String placeholder;
	
	@Property(name = "type")
	protected String type;
	
	@Property(name = "required")
	protected boolean required;
	
	public Field(String name, String label, String type, boolean required)
	{
		this.name = name;
		this.label = label;
		this.placeholder = label;
		this.type = type;
		this.required = required;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public String getPlaceholder()
	{
		return placeholder;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public String getType()
	{
		return type;
	}
	
	public boolean idRequired()
	{
		return required;
	}
	
	public Field setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public Field seLabel(String label)
	{
		this.label = label;
		return this;
	}
	
	public Field setPlaceholder(String placeholder)
	{
		this.placeholder = placeholder;
		return this;
	}
	
	public Field setType(String type)
	{
		this.type = type;
		return this;
	}
	
	public Field setRequired(boolean required)
	{
		this.required = required;
		return this;
	}
	
}
