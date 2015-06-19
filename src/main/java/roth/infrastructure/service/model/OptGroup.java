package roth.infrastructure.service.model;

import java.util.LinkedList;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class OptGroup extends Option
{
	@Property(name = "options")
	protected LinkedList<Option> options = new LinkedList<Option>();
	
	public OptGroup(String name)
	{
		super(name, null);
	}
	
	public LinkedList<Option> getOptions()
	{
		return options;
	}
	
	public OptGroup setOptions(LinkedList<Option> options)
	{
		this.options = options;
		return this;
	}
	
	public OptGroup addOption(Option option)
	{
		this.options.add(option);
		return this;
	}
	
}
