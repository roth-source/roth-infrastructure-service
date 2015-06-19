package roth.infrastructure.service.model;

import java.util.LinkedList;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class Select extends Field
{
	@Property(name = "options")
	protected LinkedList<Option> options = new LinkedList<Option>();
	
	@Property(name = "multiple")
	protected boolean multiple = false;
	
	@Property(name = "selected")
	protected boolean selected = false;
	
	public Select(String name, String label, boolean required)
	{
		super(name, label, "select", required);
	}
	
	public Select(String name, String label, boolean required, boolean multiple)
	{
		super(name, label, "select", required);
		this.multiple = multiple;
	}
	
	public LinkedList<Option> getOptions()
	{
		return options;
	}
	
	public boolean isMultiple()
	{
		return multiple;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public Select setOptions(LinkedList<Option> options)
	{
		this.options = options;
		return this;
	}
	
	public Select addOption(Option option)
	{
		if(option.selected)
		{
			this.selected = true;
		}
		this.options.add(option);
		return this;
	}
	
	public Select setMultiple(boolean multiple)
	{
		this.multiple = multiple;
		return this;
	}
	
	public Select setSelected(boolean selected)
	{
		this.selected = selected;
		return this;
	}
	
}
