package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.User;

public class OrgTable extends Table<Org, String>
{
	
	protected OrgTable(Db db, User user)
	{
		super(db, user, Org.class);
	}
	
	public static OrgTable get(Db db, User user)
	{
		return new OrgTable(db, user);
	}
	
}
