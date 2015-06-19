package roth.infrastructure.data.table;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.type.UserType;
import roth.lib.map.rdb.sql.Select;
import roth.lib.map.rdb.sql.Where;

public class UserTable extends Table<User, String>
{
	
	protected UserTable(Db db, User user)
	{
		super(db, user, User.class);
	}
	
	public static UserTable get(Db db)
	{
		return new UserTable(db, null);
	}
	
	public static UserTable get(Db db, User user)
	{
		return new UserTable(db, user);
	}
	
	public boolean hasGlobalAdmin()
	{
		return countGlobalAdmin() > 0;
	}
	
	public int countGlobalAdmin()
	{
		Select select = select();
		select.whereEquals("type", UserType.GLOBAL_ADMIN.toString());
		return count(select);
	}
	
	public User findByEmail(String email)
	{
		return findBy(Where.equals("email", email));
	}
	
	public LinkedList<User> findAllByOrg(Org org)
	{
		Select select = select();
		select.join("member", "user_id", "user", "id");
		select.whereEquals("member", "org_id", org.getId());
		select.orderByAsc("type").orderByAsc("email");
		return findAllBy(select);
	}
	
	public LinkedList<User> findAllAvailable()
	{
		Select select = select();
		select.joinLeft("member", "user_id", "user", "id");
		select.whereIsNull("member", "id");
		select.orderByAsc("type").orderByAsc("email");
		return findAllBy(select);
	}
	
}
