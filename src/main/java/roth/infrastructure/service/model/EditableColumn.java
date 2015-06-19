package roth.infrastructure.service.model;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public abstract class EditableColumn extends Column
{
	@Property(name = "service")
	protected String service;
	
	@Property(name = "method")
	protected String method;
	
	@Property(name = "name")
	protected String name;
	
	public EditableColumn(String label, String width, String field, String name, String service, String method, String type)
	{
		super(label, width, field);
		this.name = name;
		this.service = service;
		this.method = method;
		this.type = type;
	}
	
	public EditableColumn(String label, String width, Renderer renderer, String name, String service, String method, String type)
	{
		super(label, width, renderer);
		this.name = name;
		this.service = service;
		this.method = method;
		this.type = type;
	}
	
	public String getService()
	{
		return service;
	}
	
	public String getMethod()
	{
		return method;
	}
	
	public String getName()
	{
		return name;
	}
	
	public EditableColumn setService(String service)
	{
		this.service = service;
		return this;
	}
	
	public EditableColumn setMethod(String method)
	{
		this.method = method;
		return this;
	}
	
	public EditableColumn setName(String name)
	{
		this.name = name;
		return this;
	}
	
}
