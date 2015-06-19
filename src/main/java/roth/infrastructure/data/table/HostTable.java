package roth.infrastructure.data.table;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Host;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.type.HostType;
import roth.lib.map.rdb.sql.Select;

public class HostTable extends Table<Host, String>
{
	
	protected HostTable(Db db, User user)
	{
		super(db, user, Host.class);
	}
	
	public static HostTable get(Db db, User user)
	{
		return new HostTable(db, user);
	}
	
	public LinkedList<Host> findAllByOrgAndType(Org org, HostType hostType)
	{
		Select select = select();
		select.whereEquals("org_id", org.getId());
		select.whereEquals("type", hostType.name());
		return findAllBy(select);
	}
	
}
