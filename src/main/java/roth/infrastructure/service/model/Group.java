package roth.infrastructure.service.model;

import java.io.Serializable;
import java.util.LinkedList;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class Group implements Serializable
{
	@Property(name = "id")
	protected String id;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "submit")
	protected String submit;
	
	@Property(name = "service")
	protected String service;
	
	@Property(name = "method")
	protected String method;
	
	@Property(name = "success")
	protected String success;
	
	@Property(name = "fields")
	protected LinkedList<Field> fields = new LinkedList<Field>();
	
	@Property(name = "labelSize")
	protected Integer labelSize = 3;
	
	@Property(name = "fieldSize")
	protected Integer fieldSize = 5;
	
	public Group(String id, String name, String submit, String service, String method)
	{
		this.id = id;
		this.name = name;
		this.submit = submit;
		this.service = service;
		this.method = method;
	}
	
	public String getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getSubmit()
	{
		return submit;
	}
	
	public String getService()
	{
		return service;
	}
	
	public String getMethod()
	{
		return method;
	}
	
	public String getSuccess()
	{
		return success;
	}
	
	public LinkedList<Field> getFields()
	{
		return fields;
	}
	
	public Integer getLabelSize()
	{
		return labelSize;
	}
	
	public Integer getFieldSize()
	{
		return fieldSize;
	}
	
	public Group setId(String id)
	{
		this.id = id;
		return this;
	}
	
	public Group setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public Group setSubmit(String submit)
	{
		this.submit = submit;
		return this;
	}
	
	public Group setService(String service)
	{
		this.service = service;
		return this;
	}
	
	public Group setMethod(String method)
	{
		this.method = method;
		return this;
	}
	
	public Group setSuccess(String success)
	{
		this.success = success;
		return this;
	}
	
	public Group setFields(LinkedList<Field> fields)
	{
		this.fields = fields;
		return this;
	}
	
	public Group addField(Field field)
	{
		this.fields.add(field);
		return this;
	}
	
	public Group setLabelSize(Integer labelSize)
	{
		this.labelSize = labelSize;
		return this;
	}
	
	public Group setFieldSize(Integer fieldSize)
	{
		this.fieldSize = fieldSize;
		return this;
	}
	
}
