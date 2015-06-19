package roth.infrastructure.service.app;

import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class SetupStaticServerRequest extends IdRequest
{
	@Property(name = "serverId")
	protected String serverId;
	
	public SetupStaticServerRequest()
	{
		
	}
	
	public String getServerId()
	{
		return serverId;
	}
	
	public SetupStaticServerRequest setServerId(String serverId)
	{
		this.serverId = serverId;
		return this;
	}
	
}
