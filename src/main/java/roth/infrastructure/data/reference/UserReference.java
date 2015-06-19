package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.table.UserTable;

public interface UserReference extends Reference
{
	
	String getUserId();
	
	default User getUser()
	{
		return UserTable.get(getRdb(), getUser()).findById(getUserId());
	}
	
}
