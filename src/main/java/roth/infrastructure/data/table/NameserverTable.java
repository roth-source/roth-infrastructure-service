package roth.infrastructure.data.table;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Nameserver;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.type.NameserverType;
import roth.lib.map.rdb.sql.Select;

public class NameserverTable extends Table<Nameserver, String>
{
	
	protected NameserverTable(Db db, User user)
	{
		super(db, user, Nameserver.class);
	}
	
	public static NameserverTable get(Db db, User user)
	{
		return new NameserverTable(db, user);
	}
	
	public LinkedList<Nameserver> findAllByOrgAndType(Org org, NameserverType type)
	{
		Select select = select();
		select.whereEquals("org_id", org.getId());
		select.whereEquals("type", type.name());
		return findAllBy(select);
	}
	
}
