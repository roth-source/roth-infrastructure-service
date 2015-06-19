package roth.infrastructure.service.layout;

import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.Member;
import roth.infrastructure.data.model.User;
import roth.infrastructure.service.Service;
import roth.lib.net.http.annotation.Ajax;
import roth.lib.net.http.annotation.Post;

@Post
public class LayoutService extends Service
{
	
	@Ajax(authenticated = true)
	public InitLayoutResponse initLayout()
	{
		InitLayoutResponse response = new InitLayoutResponse();
		User user = getUser();
		if(user != null)
		{
			response.setUserType(user.getType().name());
			for(Member orgUser : user.getMembers())
			{
				Org org = orgUser.getOrg();
				if(org != null)
				{
					response.addOrg(org.mask().maskApps());
				}
			}
		}
		return response;
	}
	
}
