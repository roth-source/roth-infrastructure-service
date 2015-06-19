package roth.infrastructure.service.model;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class MenuColumn extends Column
{
	
	@Property(name = "toggle")
	protected String toggle;
	
	public MenuColumn(String toggle, String width)
	{
		super();
		this.toggle = toggle;
		setWidth(width);
		setType("menu");
	}
	
}
