package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdOrgModel;
import roth.infrastructure.data.reference.UserReference;
import roth.infrastructure.data.table.UserTable;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "member")
@SuppressWarnings("serial")
public class Member extends IdOrgModel implements UserReference
{
	@Property(name = "user_id")
	protected String userId;
	
	@Property(name = "user", rdb = false)
	protected User memberUser;
	
	protected Member(Rdb rdb)
	{
		super(rdb);
	}
	
	public Member(Db db, User user, Org org, User memberUser)
	{
		super(db, user, org);
		this.userId = memberUser.getId();
	}
	
	@Override
	public Member mask()
	{
		return this;
	}
	
	public Member maskUser()
	{
		this.memberUser = getMemberUser().mask();
		return this;
	}
	
	@Override
	public String getUserId()
	{
		return userId;
	}
	
	public User getMemberUser()
	{
		return UserTable.get(db).findById(userId);
	}
	
	public Member setUserId(String userId)
	{
		this.userId = setDirty("userId", this.userId, userId);
		return this;
	}
	
}
