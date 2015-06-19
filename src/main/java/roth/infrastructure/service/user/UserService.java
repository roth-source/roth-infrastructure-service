package roth.infrastructure.service.user;

import roth.infrastructure.data.model.User;
import roth.infrastructure.data.table.UserTable;
import roth.infrastructure.data.type.UserType;
import roth.infrastructure.service.Service;
import roth.infrastructure.service.SuccessResponse;
import roth.infrastructure.util.DataUtil;
import roth.lib.map.rdb.RdbException;
import roth.lib.net.http.annotation.Ajax;
import roth.lib.net.http.annotation.Post;

@Post
public class UserService extends Service
{
	
	@Ajax(authenticated = false)
	public SuccessResponse initLogin()
	{
		SuccessResponse response = new SuccessResponse();
		if(environment.isDev())
		{
			if(!hasUser())
			{
				setUser(UserTable.get(db).findById("0pc1SziIymI3Lw"));
			}
		}
		response.setSuccess(UserTable.get(db).hasGlobalAdmin());
		return response;
	}
	
	@Ajax(authenticated = false)
	public SubmitLoginResponse submitLogin(SubmitLoginRequest request)
	{
		SubmitLoginResponse response = new SubmitLoginResponse();
		User user = UserTable.get(db).findByEmail(request.getEmail());
		if(user != null && request.getPassword() != null)
		{
			String password = DataUtil.digestPassword(request.getPassword());
			if(password.equals(user.getPassword()))
			{
				response.setSuccess(true);
				response.setUserType(user.getType().name());
				setUser(user);
			}
			else
			{
				response.setSuccess(false);
			}
		}
		else
		{
			response.setSuccess(false);
		}
		return response;
	}
	
	@Ajax(authenticated = false)
	public SuccessResponse createGlobalAdmin(CreateGlobalAdminRequest request)
	{
		SuccessResponse response = new SuccessResponse();
		if(!UserTable.get(db).hasGlobalAdmin())
		{
			try
			{
				new User(db, null, UserType.GLOBAL_ADMIN, request.getEmail(), DataUtil.digestPassword(request.getPassword())).insert();
				response.setSuccess(true);
			}
			catch(RdbException e)
			{
				response.setSuccess(false);
			}
		}
		else
		{
			response.setSuccess(false);
		}
		return response;
	}
	
}
