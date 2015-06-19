package roth.infrastructure.data;

import roth.infrastructure.data.model.App;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.reference.AppReference;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@SuppressWarnings("serial")
public abstract class IdAppModel extends IdModel implements AppReference
{
	@Property(name = "app_id")
	protected String appId;
	
	protected IdAppModel(Rdb rdb)
	{
		super(rdb);
	}
	
	public IdAppModel(Db db, User user, App app)
	{
		super(db, user);
		this.appId = app.getId();
	}
	
	@Override
	public String getAppId()
	{
		return appId;
	}
	
	public IdAppModel setAppId(String appId)
	{
		this.appId = setDirty("appId", this.appId, appId);
		return this;
	}
	
}
