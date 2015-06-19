package roth.infrastructure.service;

import roth.infrastructure.service.ServiceResponse;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class UpdateResponse extends ServiceResponse
{
	@Property(name = "status")
	protected String status;
	
	@Property(name = "msg")
	protected String message;
	
	public UpdateResponse()
	{
		
	}
	
	public boolean isSuccess()
	{
		return status == null || !status.equals("error");
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public UpdateResponse setSuccess(boolean success)
	{
		if(!success)
		{
			this.status = "error";
			this.message = "failed to update";
		}
		return this;
	}
	
	public UpdateResponse setStatus(String status)
	{
		this.status = status;
		return this;
	}
	
	public UpdateResponse setMessage(String message)
	{
		this.message = message;
		return this;
	}
	
}
