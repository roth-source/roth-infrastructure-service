package roth.infrastructure.service.model;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class Date extends Field
{
	@Property(name = "format")
	protected String format;
	
	@Property(name = "validate")
	protected String validate;
	
	public Date(String name, String label, boolean required)
	{
		this(name, label, required, "yyyy-mm-dd");
	}

	public Date(String name, String label, boolean required, String format)
	{
		super(name, label, "date", required);
		this.format = format;
		this.validate = "date";
	}
	
	public String getFormat()
	{
		return format;
	}
	
	public String getValidate()
	{
		return validate;
	}
	
	public Date setFormat(String format)
	{
		this.format = format;
		return this;
	}
	
	public Date setValidate(String validate)
	{
		this.validate = validate;
		return this;
	}
	
}
