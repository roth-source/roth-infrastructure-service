package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.model.Log;
import roth.lib.annotation.Id;
import roth.lib.map.rdb.RdbTable;
import roth.lib.map.rdb.sql.Where;

public class LogTable extends RdbTable<Log>
{
	protected static final String ID = "id";
	
	protected Db db;
	
	protected LogTable(Db db)
	{
		super(Log.class);
		this.db = db;
	}
	
	public static LogTable get(Db db)
	{
		return new LogTable(db);
	}
	
	@Override
	public Db getRdb()
	{
		return db;
	}
	
	public Log findById(Id id)
	{
		return findBy(Where.equals(ID, id));
	}
	
}
