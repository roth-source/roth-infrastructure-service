package roth.infrastructure.service.model;

import java.io.Serializable;
import java.util.LinkedList;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class Form implements Serializable
{
	@Property(name = "title")
	protected String title;
	
	@Property(name = "label")
	protected String label;
	
	@Property(name = "placeholder")
	protected String placeholder;
	
	@Property(name = "groups")
	protected LinkedList<Group> groups = new LinkedList<Group>();
	
	@Property(name = "labelSize")
	protected Integer labelSize = 3;
	
	@Property(name = "fieldSize")
	protected Integer fieldSize = 5;
	
	public Form()
	{
		
	}
	
	public Form(String title, String label)
	{
		this.title = title;
		this.label = label;
		this.placeholder = label;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public String getPlaceholder()
	{
		return placeholder;
	}
	
	public Integer getLabelSize()
	{
		return labelSize;
	}
	
	public Integer getFieldSize()
	{
		return fieldSize;
	}
	
	public LinkedList<Group> getGroups()
	{
		return groups;
	}
	
	public Form setTitle(String title)
	{
		this.title = title;
		return this;
	}
	
	public Form setLabel(String label)
	{
		this.label = label;
		return this;
	}
	
	public Form setPlaceholder(String placeholder)
	{
		this.placeholder = placeholder;
		return this;
	}
	
	public Form setLabelSize(Integer labelSize)
	{
		this.labelSize = labelSize;
		return this;
	}
	
	public Form setFieldSize(Integer fieldSize)
	{
		this.fieldSize = fieldSize;
		return this;
	}
	
	public Form setGroups(LinkedList<Group> groups)
	{
		this.groups = groups;
		return this;
	}
	
	public Form addGroup(Group group)
	{
		this.groups.add(group);
		return this;
	}
	
}
