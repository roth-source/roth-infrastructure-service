package roth.infrastructure.service.model;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class TextArea extends Field
{
	@Property(name = "rows")
	protected Integer rows;
	
	public TextArea(String name, String label, boolean required)
	{
		super(name, label, "textarea", required);
	}
	
	public Integer getRows()
	{
		return rows;
	}
	
	public TextArea setRows(Integer rows)
	{
		this.rows = rows;
		return this;
	}
	
}
