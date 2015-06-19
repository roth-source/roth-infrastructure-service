package roth.infrastructure.service.user;

import roth.infrastructure.service.ServiceRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateGlobalAdminRequest extends ServiceRequest
{
	@Property(name = "email")
	protected String email;
	
	@Property(name = "password")
	protected String password;
	
	@Property(name = "confirm")
	protected String confirm;
	
	public CreateGlobalAdminRequest()
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
	
	public String getConfirm()
	{
		return confirm;
	}
	
	public CreateGlobalAdminRequest setEmail(String email)
	{
		this.email = email;
		return this;
	}
	
	public CreateGlobalAdminRequest setPassword(String password)
	{
		this.password = password;
		return this;
	}
	
	public CreateGlobalAdminRequest setConfirm(String confirm)
	{
		this.confirm = confirm;
		return this;
	}
	
}
