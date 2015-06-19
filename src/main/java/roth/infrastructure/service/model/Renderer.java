package roth.infrastructure.service.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class Renderer implements Serializable
{
	@Property(name = "name")
	protected String name;
	
	@Property(name = "fields")
	protected LinkedList<String> fields = new LinkedList<String>();
	
	@Property(name = "options")
	protected LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
	
	public Renderer()
	{
		
	}
	
	public Renderer(String field)
	{
		this.fields.add(field);
	}
	
	public Renderer(String name, String...fields)
	{
		this.name = name;
		this.fields.addAll(Arrays.asList(fields));
	}
	
	public String getName()
	{
		return name;
	}
	
	public LinkedList<String> getFields()
	{
		return fields;
	}
	
	public LinkedHashMap<String, String> getOptions()
	{
		return options;
	}
	
	public Renderer setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public Renderer setFields(LinkedList<String> fields)
	{
		this.fields = fields;
		return this;
	}
	
	public Renderer addField(String field)
	{
		this.fields.add(field);
		return this;
	}
	
	public Renderer setOptions(LinkedHashMap<String, String> options)
	{
		this.options = options;
		return this;
	}
	
	public Renderer addOption(String name, String value)
	{
		this.options.put(name, value);
		return this;
	}
	
}
