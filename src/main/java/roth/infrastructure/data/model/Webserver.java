package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdModel;
import roth.infrastructure.data.reference.EnvReference;
import roth.infrastructure.data.reference.ServerReference;
import roth.infrastructure.data.references.ContextReferences;
import roth.infrastructure.data.references.VirtualhostReferences;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "webserver")
@SuppressWarnings("serial")
public class Webserver extends IdModel implements ServerReference, EnvReference, VirtualhostReferences, ContextReferences
{
	@Property(name = "server_id")
	protected String serverId;
	
	@Property(name = "env_id")
	protected String envId;
	
	protected Webserver(Rdb rdb)
	{
		super(rdb);
	}
	
	public Webserver(Db db, User user, Server server, Env env)
	{
		super(db, user);
		this.serverId = server.getId();
		this.envId = env.getId();
	}
	
	@Override
	public Webserver mask()
	{
		return this;
	}
	
	@Override
	public String getServerId()
	{
		return serverId;
	}
	
	@Override
	public String getEnvId()
	{
		return envId;
	}
	
	public Webserver setServerId(String serverId)
	{
		this.serverId = setDirty("serverId", this.serverId, serverId);
		return this;
	}
	
	public Webserver setEnvId(String envId)
	{
		this.envId = setDirty("envId", this.envId, envId);
		return this;
	}
	
}
