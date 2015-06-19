package roth.infrastructure.data;

import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.User;
import roth.infrastructure.util.DataUtil;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;
import roth.lib.util.EnumUtil;

@SuppressWarnings("serial")
public abstract class Provider extends IdOrgModel
{
	@Property(name = "name")
	protected String name;
	
	@Property(name = "apiKey")
	protected String apiKey;
	
	@Property(name = "username")
	protected String username;
	
	protected Provider(Rdb rdb)
	{
		super(rdb);
	}
	
	protected Provider(Db db, User user, Org org, String name)
	{
		super(db, user, org);
		this.name = name;
	}
	
	protected Provider(Db db, User user, Org org, String name, String apiKey)
	{
		super(db, user, org);
		this.name = name;
		this.apiKey = apiKey;
	}
	
	@Override
	public Provider mask()
	{
		this.apiKey = DataUtil.mask(apiKey, 10);
		return this;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getApiKey()
	{
		return apiKey;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public abstract Enum<?> getType();
	
	public String getDisplayName()
	{
		return EnumUtil.toString(getType()) + (name != null && !name.isEmpty() ? " " + name : "");
	}
	
	public Provider setName(String name)
	{
		this.name = setDirty("name", this.name, name);
		return this;
	}
	
	public Provider setApiKey(String apiKey)
	{
		this.apiKey = setDirty("apiKey", this.apiKey, apiKey);
		return this;
	}
	
	public Provider setUsername(String username)
	{
		this.username = setDirty("username", this.username, username);
		return this;
	}
	
}
