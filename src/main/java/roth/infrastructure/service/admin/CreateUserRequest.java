package roth.infrastructure.service.admin;

import roth.infrastructure.service.ServiceRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateUserRequest extends ServiceRequest
{
	@Property(name = "type")
	protected String type;
	
	@Property(name = "email")
	protected String email;
	
	@Property(name = "password")
	protected String password;
	
	public CreateUserRequest()
	{
		
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public CreateUserRequest setType(String type)
	{
		this.type = type;
		return this;
	}
	
	public CreateUserRequest setEmail(String email)
	{
		this.email = email;
		return this;
	}
	
	public CreateUserRequest setPassword(String password)
	{
		this.password = password;
		return this;
	}
	
}
