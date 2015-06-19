package roth.infrastructure.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpSession;

import roth.infrastructure.Constants;
import roth.infrastructure.config.Config;
import roth.infrastructure.config.Environment;
import roth.infrastructure.data.Db;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.type.UserType;
import roth.lib.net.http.annotation.Ajax;
import roth.lib.net.http.annotation.Api;
import roth.lib.net.http.service.HttpService;
import roth.lib.net.http.util.InitialContextUtil;

public abstract class Service extends HttpService implements Constants
{
	protected static final int POOL_SIZE 	= 10;
	protected static final String USER 		= "user";
	
	protected static ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
	protected static Environment environment;
	protected static Config config;
	protected static Db db;
	
	public Service()
	{
		if(environment == null)
		{
			environment = Environment.get(InitialContextUtil.getEnv());
			config = environment.getConfig();
			db = Db.get(environment);
		}
	}
	
	protected boolean hasUser()
	{
		HttpSession session = httpServletRequest.getSession();
		return session.getAttribute(USER) != null;
	}
	
	protected boolean hasAdmin()
	{
		return hasGlobalAdmin() || hasOrgAdmin();
	}
	
	protected boolean hasGlobalAdmin()
	{
		User user = getUser();
		if(user != null && UserType.GLOBAL_ADMIN.equals(user.getType()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	protected boolean hasOrgAdmin()
	{
		User user = getUser();
		if(user != null && UserType.ORG_ADMIN.equals(user.getType()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	protected User getUser()
	{
		User user = null;
		HttpSession session = httpServletRequest.getSession();
		Object object = session.getAttribute(USER);
		if(object instanceof User)
		{
			user = (User) object;
		}
		return user;
	}
	
	protected void setUser(User user)
	{
		if(user != null)
		{
			HttpSession session = initSession();
			session.setAttribute(USER, user);
		}
	}
	
	@Override
	public boolean isAjaxAuthenticated(Ajax ajax)
	{
		return hasUser();
	}
	
	@Override
	public boolean isApiAuthenticated(Api api)
	{
		return true;
	}
	
	@Override
	public boolean isAuthorized(Object request)
	{
		return true;
	}
	
}
