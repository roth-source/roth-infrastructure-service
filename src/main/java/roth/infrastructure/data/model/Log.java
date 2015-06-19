package roth.infrastructure.data.model;

import java.util.Calendar;
import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.table.ChangeTable;
import roth.infrastructure.data.type.LogType;
import roth.infrastructure.util.JsonUtil;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Id;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;
import roth.lib.map.rdb.RdbModel;
import roth.lib.map.rdb.sql.Where;
import roth.lib.util.CalendarUtil;
import roth.lib.util.IdUtil;

@Entity(name = "log")
@SuppressWarnings("serial")
public class Log extends RdbModel
{
	protected transient Db db;
	
	@Id
	@Property(name = "id")
	protected String id;
	
	@Property(name = "active")
	protected boolean active;
	
	@Property(name = "type")
	protected LogType type;
	
	@Property(name = "user_id")
	protected String userId;
	
	@Property(name = "table")
	protected String table;
	
	@Property(name = "key")
	protected String key;
	
	@Property(name = "loggedOn")
	protected Calendar loggedOn;
	
	protected Log(Rdb rdb)
	{
		if(rdb instanceof Db)
		{
			this.db = (Db) rdb;
		}
	}
	
	public Log(Db db, User user, LogType type, String table, String key)
	{
		this(db);
		this.id = IdUtil.uuid(db.getServerPrefix());
		this.active = true;
		this.userId = user.getId();
		this.type = type;
		this.table = table;
		this.key = key;
		this.loggedOn = CalendarUtil.now();
	}
	
	@Override
	public Db getRdb()
	{
		return db;
	}

	@Override
	public boolean isGenerated()
	{
		return false;
	}
	
	public String getId()
	{
		return id;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public LogType getType()
	{
		return type;
	}
	
	public String getUserId()
	{
		return userId;
	}
	
	public String getTable()
	{
		return table;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public Calendar getLoggedOn()
	{
		return loggedOn;
	}
	
	public LinkedList<Change> getChanges()
	{
		return ChangeTable.get(db).findAllBy(Where.equals("log_id", id));
	}
	
	public String toJson()
	{
		return toJson(false);
	}
	
	public String toJson(boolean prettyPrinting)
	{
		return JsonUtil.toJson(this, prettyPrinting);
	}
	
	public String toString()
	{
		return JsonUtil.toJsonDebug(this);
	}
	
}
