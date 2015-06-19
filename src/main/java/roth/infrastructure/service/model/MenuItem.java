package roth.infrastructure.service.model;

import java.io.Serializable;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class MenuItem implements Serializable
{
	@Property(name = "label")
	protected String label;
	
	@Property(name = "service")
	protected String service;
	
	@Property(name = "method")
	protected String method;
	
	public MenuItem(String name, String value)
	{
		
	}
	
}
