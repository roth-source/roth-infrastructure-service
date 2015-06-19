package roth.infrastructure.service.model;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class Text extends Field
{
	@Property(name = "filter")
	protected String filter;
	
	@Property(name = "validate")
	protected String validate;
	
	@Property(name = "value")
	protected String value;
	
	public Text(String name, String label, String type, boolean required)
	{
		super(name, label, type, required);
	}
	
	public String getFilter()
	{
		return filter;
	}
	
	public String getValidate()
	{
		return validate;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public Text setFilter(String filter)
	{
		this.filter = filter;
		return this;
	}
	
	public Text setValidate(String validate)
	{
		this.validate = validate;
		return this;
	}
	
	public Text setValue(String value)
	{
		this.value = value;
		return this;
	}
	
}
