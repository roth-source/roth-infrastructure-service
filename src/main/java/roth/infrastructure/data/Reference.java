package roth.infrastructure.data;

import roth.infrastructure.data.model.User;
import roth.lib.map.rdb.sql.Where;

public interface Reference
{
	
	Db getRdb();
	User getUser();
	Where reference();
	
}
