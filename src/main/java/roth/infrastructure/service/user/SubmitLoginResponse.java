package roth.infrastructure.service.user;

import roth.infrastructure.service.SuccessResponse;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class SubmitLoginResponse extends SuccessResponse
{
	@Property(name = "userType")
	protected String userType;
	
	public SubmitLoginResponse()
	{
		
	}
	
	public String getUserType()
	{
		return userType;
	}
	
	public SubmitLoginResponse setUserType(String userType)
	{
		this.userType = userType;
		return this;
	}
	
}
