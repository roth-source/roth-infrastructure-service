package roth.infrastructure.test.data;

import java.io.Serializable;

import roth.lib.annotation.Entity;
import roth.lib.annotation.Id;
import roth.lib.annotation.Property;
import roth.lib.util.IdUtil;

@Entity(name = "dist")
@SuppressWarnings("serial")
public class Dist implements Serializable
{
	@Id
	@Property(name = "id")
	protected String id;
	
	@Property(name = "active")
	protected boolean active;
	
	@Property(name = "type")
	protected DistType type;
	
	@Property(name = "name")
	protected String name;
	
	protected Dist()
	{
		
	}
	
	public Dist(DistType type, String name)
	{
		this.id = IdUtil.uuid("0");
		this.active = true;
		this.type = type;
		this.name = name;
	}
	
	public String getId()
	{
		return id;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public DistType getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Dist setId(String id)
	{
		this.id = id;
		return this;
	}
	
	public Dist setActive(boolean active)
	{
		this.active = active;
		return this;
	}
	
	public Dist setType(DistType type)
	{
		this.type = type;
		return this;
	}
	
	public Dist setName(String name)
	{
		this.name = name;
		return this;
	}
	
}
