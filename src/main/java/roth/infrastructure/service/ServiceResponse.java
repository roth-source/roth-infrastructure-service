package roth.infrastructure.service;

import java.util.LinkedList;

import roth.infrastructure.service.model.Message;
import roth.infrastructure.util.JsonUtil;
import roth.lib.annotation.Property;
import roth.lib.net.http.service.HttpServiceResponse;

@SuppressWarnings("serial")
public class ServiceResponse extends HttpServiceResponse
{
	@Property(name = "errors")
	protected LinkedList<Message> errors;
	
	public ServiceResponse()
	{
		
	}
	
	public LinkedList<Message> getErrors()
	{
		return errors;
	}
	
	public ServiceResponse setErrors(LinkedList<Message> errors)
	{
		this.errors = errors;
		return this;
	}
	
	public ServiceResponse addError(Message error)
	{
		if(errors == null)
		{
			errors = new LinkedList<Message>(); 
		}
		errors.add(error);
		return this;
	}
	
	@Override
	public String toString()
	{
		return JsonUtil.toJsonDebug(this);
	}
	
}
