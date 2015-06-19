package roth.infrastructure.service.model;

import java.io.Serializable;
import java.util.LinkedList;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class Column implements Serializable
{
	@Property(name = "type")
	protected String type;
	
	@Property(name = "label")
	protected String label;
	
	@Property(name = "width")
	protected String width;
	
	@Property(name = "renderers")
	protected LinkedList<Renderer> renderers = new LinkedList<Renderer>();
	
	protected Column()
	{
		
	}
	
	public Column(String label, String width)
	{
		this.label = label;
		this.width = width;
	}
	
	public Column(String label, String width, String field)
	{
		this.label = label;
		this.width = width;
		this.renderers.add(new Renderer(field));
	}
	
	public Column(String label, String width, Renderer renderer)
	{
		this.label = label;
		this.width = width;
		this.renderers.add(renderer);
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public String getWidth()
	{
		return width;
	}
	
	public LinkedList<Renderer> getRenderers()
	{
		return renderers;
	}
	
	public Column setType(String type)
	{
		this.type = type;
		return this;
	}
	
	public Column setLabel(String label)
	{
		this.label = label;
		return this;
	}
	
	public Column setWidth(String width)
	{
		this.width = width;
		return this;
	}
	
	public Column setRenderers(LinkedList<Renderer> renderers)
	{
		this.renderers = renderers;
		return this;
	}
	
	public Column addRenderer(Renderer renderer)
	{
		this.renderers.add(renderer);
		return this;
	}
	
}
