package roth.infrastructure.data;

import java.util.Collection;
import java.util.LinkedList;

import roth.infrastructure.data.model.User;
import roth.lib.map.rdb.RdbTable;
import roth.lib.map.rdb.sql.Select;
import roth.lib.map.rdb.sql.Where;

public abstract class Table<T extends Model, Id> extends RdbTable<T>
{
	protected static final String ID = "id";
	
	protected Db db;
	protected User user;
	
	protected Table(Db db, User user, Class<T> klass)
	{
		super(klass);
		this.db = db;
		this.user = user;
	}
	
	@Override
	public Db getRdb()
	{
		return db;
	}
	
	public T findById(Id id)
	{
		return findBy(Where.equals(ID, id));
	}
	
	@Override
	public T findBy(Select select)
	{
		return setUser(super.findBy(select));
	}
	
	@Override
	public T findBy(String sql)
	{
		return setUser(super.findBy(sql));
	}
	
	@Override
	public T findBy(String sql, Collection<Object> values)
	{
		return setUser(super.findBy(sql, values));
	}
	
	protected T setUser(T model)
	{
		model.setUser(user);
		return model;
	}
	
	@Override
	public LinkedList<T> findAllBy(Select select)
	{
		return setUser(super.findAllBy(select));
	}
	
	@Override
	public LinkedList<T> findAllBy(String sql)
	{
		return setUser(super.findAllBy(sql));
	}
	
	@Override
	public LinkedList<T> findAllBy(String sql, Collection<Object> values)
	{
		return setUser(super.findAllBy(sql, values));
	}
	
	protected LinkedList<T> setUser(LinkedList<T> models)
	{
		for(T model : models)
		{
			model.setUser(user);
		}
		return models;
	}
	
}
