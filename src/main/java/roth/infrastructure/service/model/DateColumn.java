package roth.infrastructure.service.model;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class DateColumn extends EditableColumn
{
	@Property(name = "format")
	protected String format;
	
	public DateColumn(String label, String width, String field, String name, String service, String method)
	{
		this(label, width, field, name, service, method, "yyyy-mm-dd");
	}
	
	public DateColumn(String label, String width, String field, String name, String service, String method, String format)
	{
		super(label, width, new Renderer("date", field), name, service, method, "date");
		this.format = format;
	}
	
	public String getFormat()
	{
		return format;
	}
	
	public DateColumn setFormat(String format)
	{
		this.format = format;
		return this;
	}
	
}
