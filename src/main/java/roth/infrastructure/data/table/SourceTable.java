package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Source;
import roth.infrastructure.data.model.User;

public class SourceTable extends Table<Source, String>
{
	
	protected SourceTable(Db db, User user)
	{
		super(db, user, Source.class);
	}
	
	public static SourceTable get(Db db, User user)
	{
		return new SourceTable(db, user);
	}
	
}
