package roth.infrastructure.service.org;

import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateServerRequest extends IdRequest
{
	@Property(name = "hostId")
	protected String hostId;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "ip")
	protected String ip;
	
	@Property(name = "password")
	protected String password;
	
	@Property(name = "confirm")
	protected String confirm;
	
	@Property(name = "datacenter")
	protected String datacenter;
	
	@Property(name = "plan")
	protected String plan;
	
	@Property(name = "distribution")
	protected String distribution;
	
	public CreateServerRequest()
	{
		
	}
	
	public String getHostId()
	{
		return hostId;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getIp()
	{
		return ip;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getConfirm()
	{
		return confirm;
	}
	
	public String getDatacenter()
	{
		return datacenter;
	}
	
	public String getPlan()
	{
		return plan;
	}
	
	public String getDistribution()
	{
		String distribution = null;
		if(this.distribution != null)
		{
			int index = this.distribution.indexOf(":");
			if(index > 0)
			{
				distribution = this.distribution.substring(index + 1);
			}
		}
		return distribution;
	}
	
	public String getServerType()
	{
		String serverType = null;
		if(this.distribution != null)
		{
			int index = this.distribution.indexOf(":");
			if(index > 0)
			{
				serverType = this.distribution.substring(0, index);
			}
		}
		return serverType;
	}
	
	public CreateServerRequest setHostId(String hostId)
	{
		this.hostId = hostId;
		return this;
	}
	
	public CreateServerRequest setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public CreateServerRequest setIp(String ip)
	{
		this.ip = ip;
		return this;
	}
	
	public CreateServerRequest setPassword(String password)
	{
		this.password = password;
		return this;
	}
	
	public CreateServerRequest setConfirm(String confirm)
	{
		this.confirm = confirm;
		return this;
	}
	
	public CreateServerRequest setDatacenter(String datacenter)
	{
		this.datacenter = datacenter;
		return this;
	}
	
	public CreateServerRequest setPlan(String plan)
	{
		this.plan = plan;
		return this;
	}
	
	public CreateServerRequest setDistribution(String distribution)
	{
		this.distribution = distribution;
		return this;
	}
	
}
