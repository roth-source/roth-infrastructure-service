package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.model.Change;
import roth.lib.annotation.Id;
import roth.lib.map.rdb.RdbTable;
import roth.lib.map.rdb.sql.Where;

public class ChangeTable extends RdbTable<Change>
{
	protected static final String ID = "id";
	
	protected Db db;
	
	protected ChangeTable(Db db)
	{
		super(Change.class);
		this.db = db;
	}
	
	public static ChangeTable get(Db db)
	{
		return new ChangeTable(db);
	}
	
	@Override
	public Db getRdb()
	{
		return db;
	}
	
	public Change findById(Id id)
	{
		return findBy(Where.equals(ID, id));
	}
	
}
