package roth.infrastructure.data.table;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Authority;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.type.AuthorityType;
import roth.lib.map.rdb.sql.Select;

public class AuthorityTable extends Table<Authority, String>
{
	
	protected AuthorityTable(Db db, User user)
	{
		super(db, user, Authority.class);
	}
	
	public static AuthorityTable get(Db db, User user)
	{
		return new AuthorityTable(db, user);
	}
	
	public LinkedList<Authority> findAllByOrgAndType(Org org, AuthorityType type)
	{
		Select select = select();
		select.whereEquals("org_id", org.getId());
		select.whereEquals("type", type.name());
		return findAllBy(select);
	}
	
}
