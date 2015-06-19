package roth.infrastructure.service.org;

import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateMemberRequest extends IdRequest
{
	@Property(name = "userId")
	protected String userId;
	
	public CreateMemberRequest()
	{
		
	}
	
	public String getUserId()
	{
		return userId;
	}
	
	public CreateMemberRequest setUserId(String userId)
	{
		this.userId = userId;
		return this;
	}
	
}
