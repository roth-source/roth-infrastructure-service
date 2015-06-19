package roth.infrastructure.service.model;

import java.util.LinkedList;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class SelectColumn extends EditableColumn
{
	@Property(name = "options")
	protected LinkedList<Option> options = new LinkedList<Option>();
	
	public SelectColumn(String label, String width, String field, String name, String service, String method)
	{
		super(label, width, field, name, service, method, "select");
	}
	
	public LinkedList<Option> getOptions()
	{
		return options;
	}
	
	public SelectColumn setOptions(LinkedList<Option> options)
	{
		this.options = options;
		return this;
	}
	
	public SelectColumn addOption(Option option)
	{
		this.options.add(option);
		return this;
	}
	
}
