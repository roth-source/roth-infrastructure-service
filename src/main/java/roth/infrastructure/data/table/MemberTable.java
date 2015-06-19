package roth.infrastructure.data.table;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.Member;
import roth.infrastructure.data.model.User;
import roth.lib.map.rdb.sql.Select;

public class MemberTable extends Table<Member, String>
{
	
	protected MemberTable(Db db, User user)
	{
		super(db, user, Member.class);
	}
	
	public static MemberTable get(Db db, User user)
	{
		return new MemberTable(db, user);
	}
	
	public LinkedList<Member> findAllByOrg(Org org)
	{
		Select select = select();
		select.whereEquals("org_id", org.getId());
		return findAllBy(select);
	}
	
	public Member findByOrgAndUser(Org org, User user)
	{
		Select select = select();
		select.whereEquals("org_id", org.getId());
		select.whereEquals("user_id", user.getId());
		return findBy(select);
	}
	
}
