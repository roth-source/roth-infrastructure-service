package roth.infrastructure.service;

import roth.infrastructure.service.ServiceResponse;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class SuccessResponse extends ServiceResponse
{
	@Property(name = "success")
	protected boolean success;
	
	public SuccessResponse()
	{
		
	}
	
	public boolean isSuccess()
	{
		return success;
	}
	
	public SuccessResponse setSuccess(boolean success)
	{
		this.success = success;
		return this;
	}
	
}
