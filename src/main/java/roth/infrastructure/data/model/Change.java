package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.util.JsonUtil;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Id;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;
import roth.lib.map.rdb.RdbModel;
import roth.lib.util.IdUtil;

@Entity(name = "change")
@SuppressWarnings("serial")
public class Change extends RdbModel
{
	protected transient Db db;
	
	@Id
	@Property(name = "id")
	protected String id;
	
	@Property(name = "active")
	protected boolean active;
	
	@Property(name = "log_id")
	protected String logId;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "oldValue")
	protected String oldValue;
	
	@Property(name = "newValue")
	protected String newValue;
	
	protected Change(Rdb rdb)
	{
		if(rdb instanceof Db)
		{
			this.db = (Db) rdb;
		}
	}
	
	public Change(Db db, Log log, String name, String oldValue, String newValue)
	{
		this(db);
		this.id = IdUtil.uuid(db.getServerPrefix());
		this.active = true;
		this.logId = log.getId();
		this.name = name;
		this.oldValue = oldValue;
		this.newValue = newValue;
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
	
	public String getLogId()
	{
		return logId;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getOldValue()
	{
		return oldValue;
	}
	
	public String getNewValue()
	{
		return newValue;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public void setActive(boolean active)
	{
		this.active = active;
	}
	
	public void setLogId(String logId)
	{
		this.logId = logId;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setOldValue(String oldValue)
	{
		this.oldValue = oldValue;
	}
	
	public void setNewValue(String newValue)
	{
		this.newValue = newValue;
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
