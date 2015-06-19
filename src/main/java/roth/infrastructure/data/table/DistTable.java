package roth.infrastructure.data.table;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Dist;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.type.DistType;
import roth.lib.map.rdb.sql.Select;

public class DistTable extends Table<Dist, String>
{
	
	protected DistTable(Db db, User user)
	{
		super(db, user, Dist.class);
	}
	
	public static DistTable get(Db db, User user)
	{
		return new DistTable(db, user);
	}
	
	public Dist findByName(DistType distType, String name)
	{
		Select select = select();
		select.whereEquals("type", distType.name());
		select.whereEquals("name", name);
		return findBy(select);
	}
	
	public LinkedList<Dist> findAllByType(DistType distType)
	{
		Select select = select();
		select.whereEquals("type", distType.name());
		select.orderByDesc("name");
		return findAllBy(select);
	}
	
}
