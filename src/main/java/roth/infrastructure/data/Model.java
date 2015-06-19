package roth.infrastructure.data;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Objects;

import roth.infrastructure.data.model.Change;
import roth.infrastructure.data.model.Log;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.type.LogType;
import roth.infrastructure.util.JsonUtil;
import roth.lib.map.PropertyField;
import roth.lib.map.rdb.Rdb;
import roth.lib.map.rdb.RdbConnection;
import roth.lib.map.rdb.RdbException;
import roth.lib.map.rdb.RdbModel;
import roth.lib.map.rdb.sql.Where;
import roth.lib.util.IdUtil;
import roth.lib.util.StringUtil;

@SuppressWarnings("serial")
public abstract class Model extends RdbModel implements Reference
{
	protected transient Db db;
	protected transient User user;
	
	protected Model(Rdb rdb)
	{
		if(rdb instanceof Db)
		{
			this.db = (Db) rdb;
		}
	}
	
	protected Model(Db db, User user)
	{
		this(db);
		this.user = user;
	}
	
	@Override
	public Db getRdb()
	{
		return db;
	}
	
	@Override
	public User getUser()
	{
		return user;
	}
	
	public Model setUser(User user)
	{
		this.user = user;
		return this;
	}
	
	protected String genereateId()
	{
		return IdUtil.uuid(db.getServerPrefix());
	}
	
	public abstract String getId();
	public abstract Model mask();
	
	@Override
	public boolean isGenerated()
	{
		return false;
	}
	
	@Override
	public Where reference()
	{
		return reference(db.getMapper().getEntityName(getClass()) + "_id");
	}
	
	public Where reference(String name)
	{
		return Where.equals(name, getId());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends RdbModel> T insert()
	{
		if(isNew())
		{
			try(RdbConnection connection = db.getConnection())
			{
				try
				{
					insert(connection);
					connection.commit();
				}
				catch(SQLException e)
				{
					connection.rollback();
					throw new RdbException(e);
				}
			}
			catch(SQLException e)
			{
				throw new RdbException(e);
			}
		}
		return (T) this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends RdbModel> T insert(RdbConnection connection) throws SQLException
	{
		if(isNew())
		{
			super.insert(connection);
			String table = db.getMapper().getEntityName(getClass());
			Log log = new Log(db, user, LogType.INSERT, table, getId());
			LinkedList<Change> changes = new LinkedList<Change>();
			LinkedHashMap<String, PropertyField> propertyNameFieldMap = db.getMapper().getPropertyNameFieldMap(getClass());
			for(Entry<String, PropertyField> propertyNameFieldEntry : propertyNameFieldMap.entrySet())
			{
				String name = propertyNameFieldEntry.getKey();
				String newValue = null;
				try
				{
					Object newObject = propertyNameFieldEntry.getValue().getField().get(this);
					newValue = StringUtil.valueOf(newObject);
				}
				catch(Exception e)
				{
					
				}
				changes.add(new Change(db, log, name, null, newValue));
			}
			log.insert(connection);
			for(Change change : changes)
			{
				change.insert(connection);
			}
		}
		return (T) this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends RdbModel> T update()
	{
		if(isPersisted() && isDirty())
		{
			try(RdbConnection connection = db.getConnection())
			{
				try
				{
					update(connection);
					connection.commit();
				}
				catch(SQLException e)
				{
					connection.rollback();
					throw new RdbException(e);
				}
			}
			catch(SQLException e)
			{
				throw new RdbException(e);
			}
		}
		return (T) this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends RdbModel> T update(RdbConnection connection) throws SQLException
	{
		if(isPersisted() && isDirty())
		{
			T model = refresh();
			String table = db.getMapper().getEntityName(getClass());
			Log log = new Log(db, user, LogType.UPDATE, table, getId());
			LinkedList<Change> changes = new LinkedList<Change>();
			LinkedHashMap<String, PropertyField> propertyNameFieldMap = db.getMapper().getPropertyNameFieldMap(getClass());
			for(String name : getDirtyNames())
			{
				String oldValue = null;
				String newValue = null;
				try
				{
					Field field = propertyNameFieldMap.get(name).getField();
					Object oldObject = field.get(model);
					oldValue = StringUtil.valueOf(oldObject);
					Object newObject = field.get(this);
					newValue = StringUtil.valueOf(newObject);
				}
				catch(Exception e)
				{
					
				}
				if(!Objects.equals(oldValue, newValue))
				{
					changes.add(new Change(db, log, name, oldValue, newValue));
				}
			}
			super.update(connection);
			log.insert(connection);
			for(Change change : changes)
			{
				change.insert(connection);
			}
		}
		return (T) this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends RdbModel> T delete()
	{
		if(isPersisted())
		{
			try(RdbConnection connection = db.getConnection())
			{
				try
				{
					delete(connection);
					connection.commit();
				}
				catch(SQLException e)
				{
					connection.rollback();
					throw new RdbException(e);
				}
			}
			catch(SQLException e)
			{
				throw new RdbException(e);
			}
		}
		return (T) this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends RdbModel> T delete(RdbConnection connection) throws SQLException
	{
		if(isPersisted())
		{
			String table = db.getMapper().getEntityName(getClass());
			Log log = new Log(db, user, LogType.DELETE, table, getId());
			LinkedList<Change> changes = new LinkedList<Change>();
			LinkedHashMap<String, PropertyField> propertyNameFieldMap = db.getMapper().getPropertyNameFieldMap(getClass());
			for(Entry<String, PropertyField> propertyNameFieldEntry : propertyNameFieldMap.entrySet())
			{
				String name = propertyNameFieldEntry.getKey();
				String oldValue = null;
				try
				{
					Object newObject = propertyNameFieldEntry.getValue().getField().get(this);
					oldValue = StringUtil.valueOf(newObject);
				}
				catch(Exception e)
				{
					
				}
				changes.add(new Change(db, log, name, oldValue, null));
			}
			super.delete(connection);
			log.insert(connection);
			for(Change change : changes)
			{
				change.insert(connection);
			}
		}
		return (T) this;
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
