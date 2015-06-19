package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdModel;
import roth.infrastructure.data.reference.DeployReference;
import roth.infrastructure.data.reference.WebserverReference;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "context")
@SuppressWarnings("serial")
public class Context extends IdModel implements WebserverReference, DeployReference
{
	@Property(name = "webserver_id")
	protected String webserverId;
	
	@Property(name = "deploy_id")
	protected String deployId;
	
	@Property(name = "path")
	protected String path;
	
	protected Context(Rdb rdb)
	{
		super(rdb);
	}
	
	public Context(Db db, User user, Webserver webserver, Deploy deploy, String path)
	{
		super(db, user);
		this.webserverId = webserver.getId();
		this.deployId = deploy.getId();
		this.path = path;
	}
	
	@Override
	public Context mask()
	{
		return this;
	}
	
	@Override
	public String getWebserverId()
	{
		return webserverId;
	}
	
	@Override
	public String getDeployId()
	{
		return deployId;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public Context setWebserverId(String webserverId)
	{
		this.webserverId = setDirty("webserverId", this.webserverId, webserverId);
		return this;
	}
	
	public Context setDeployId(String deployId)
	{
		this.deployId = setDirty("deployId", this.deployId, deployId);
		return this;
	}
	
	public Context setPath(String path)
	{
		this.path = setDirty("path", this.path, path);
		return this;
	}
	
}
