package roth.infrastructure.service.user;

import roth.infrastructure.service.ServiceRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class SubmitLoginRequest extends ServiceRequest
{
	@Property(name = "email")
	protected String email;
	
	@Property(name = "password")
	protected String password;
	
	public SubmitLoginRequest()
	{
		
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public SubmitLoginRequest setEmail(String email)
	{
		this.email = email;
		return this;
	}
	
	public SubmitLoginRequest setPassword(String password)
	{
		this.password = password;
		return this;
	}
	
}
