package roth.infrastructure.service.app;

import java.util.LinkedList;

import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class SetupServiceServersRequest extends IdRequest
{
	@Property(name = "serverIds")
	protected LinkedList<String> serverIds = new LinkedList<String>();
	
	public SetupServiceServersRequest()
	{
		
	}
	
	public LinkedList<String> getServerIds()
	{
		return serverIds;
	}
	
	public void setServerIds(LinkedList<String> serverIds)
	{
		this.serverIds = serverIds;
	}
	
}
