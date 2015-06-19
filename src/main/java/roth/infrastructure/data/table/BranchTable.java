package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Branch;
import roth.infrastructure.data.model.User;

public class BranchTable extends Table<Branch, String>
{
	
	protected BranchTable(Db db, User user)
	{
		super(db, user, Branch.class);
	}
	
	public static BranchTable get(Db db, User user)
	{
		return new BranchTable(db, user);
	}
	
}
