package roth.infrastructure.service;

import roth.infrastructure.util.JsonUtil;
import roth.lib.net.http.service.HttpServiceRequest;

@SuppressWarnings("serial")
public class ServiceRequest extends HttpServiceRequest
{
	
	public ServiceRequest()
	{
		
	}
	
	@Override
	public String toString()
	{
		return JsonUtil.toJsonDebug(this);
	}
	
}
