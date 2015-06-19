package roth.infrastructure.data.table;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.App;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.Server;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.type.ServerType;
import roth.lib.map.rdb.sql.Select;

public class ServerTable extends Table<Server, String>
{
	
	protected ServerTable(Db db, User user)
	{
		super(db, user, Server.class);
	}
	
	public static ServerTable get(Db db, User user)
	{
		return new ServerTable(db, user);
	}
	
	public LinkedList<Server> findAllByOrg(Org org)
	{
		Select select = select();
		select.join("host", "id", "server", "host_id");
		select.whereEquals("host", "org_id", org.getId());
		return findAllBy(select);
	}
	
	public LinkedList<Server> findAllByAppAndServerType(App app, ServerType serverType)
	{
		Select select = select();
		select.join("app_server", "server_id", "server", "id");
		select.whereEquals("app_server", "app_id", app.getId());
		select.whereEquals("server", "type", serverType.name());
		return findAllBy(select);
	}
	
	public LinkedList<Server> findAllAvailableByServerType(ServerType serverType)
	{
		Select select = select();
		select.joinLeft("app_server", "server_id", "server", "id");
		select.whereIsNull("app_server", "id");
		select.whereEquals("server", "type", serverType.name());
		return findAllBy(select);
	}
	
	public LinkedList<Server> findAllByHostId(String id)
	{
		Select select = select();
		select.whereEquals("host_id", id);
		return findAllBy(select);
	}
	
}
