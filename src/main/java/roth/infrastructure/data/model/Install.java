package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdModel;
import roth.infrastructure.data.reference.DistReference;
import roth.infrastructure.data.reference.ServerReference;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "install")
@SuppressWarnings("serial")
public class Install extends IdModel implements DistReference, ServerReference
{
	@Property(name = "dist_id")
	protected String distId;
	
	@Property(name = "server_id")
	protected String serverId;
	
	protected Install(Rdb rdb)
	{
		super(rdb);
	}
	
	public Install(Db db, User user, Dist dist, Server server)
	{
		super(db, user);
		this.distId = dist.getId();
		this.serverId = server.getId();
	}
	
	@Override
	public Install mask()
	{
		return this;
	}
	
	@Override
	public String getDistId()
	{
		return distId;
	}
	
	@Override
	public String getServerId()
	{
		return serverId;
	}
	
	public Install setDistId(String distId)
	{
		this.distId = setDirty("distId", this.distId, distId);
		return this;
	}
	
	public Install setServerId(String serverId)
	{
		this.serverId = setDirty("serverId", this.serverId, serverId);
		return this;
	}
	
}
