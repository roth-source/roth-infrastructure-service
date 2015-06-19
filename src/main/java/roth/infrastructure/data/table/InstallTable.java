package roth.infrastructure.data.table;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.App;
import roth.infrastructure.data.model.Install;
import roth.infrastructure.data.model.Dist;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.type.DistType;
import roth.lib.map.rdb.sql.Select;

public class InstallTable extends Table<Install, String>
{
	
	protected InstallTable(Db db, User user)
	{
		super(db, user, Install.class);
	}
	
	public static InstallTable get(Db db, User user)
	{
		return new InstallTable(db, user);
	}
	
	public Install findByAppAndDist(App app, Dist dist)
	{
		Select select = select();
		select.whereEquals("app_id", app.getId());
		select.whereEquals("dist_id", dist.getId());
		return findBy(select);
	}
	
	public LinkedList<Install> findAllByAppAndDistType(App app, DistType distType)
	{
		Select select = select();
		select.join("dist", "id", "install", "dist_id");
		select.whereEquals("install", "app_id", app.getId());
		select.whereEquals("dist", "type", distType.name());
		return findAllBy(select);
	}
	
	public Install findByAppAndDistType(App app, DistType distType)
	{
		return findAllByAppAndDistType(app, distType).peekFirst();
	}
	
}
