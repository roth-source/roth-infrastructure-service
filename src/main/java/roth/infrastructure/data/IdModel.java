package roth.infrastructure.data;

import roth.infrastructure.data.model.User;
import roth.lib.annotation.Id;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@SuppressWarnings("serial")
public abstract class IdModel extends Model
{
	@Id
	@Property(name = "id")
	protected String id;
	
	@Property(name = "active")
	protected boolean active;
	
	protected IdModel(Rdb rdb)
	{
		super(rdb);
	}
	
	protected IdModel(Db db, User user)
	{
		super(db, user);
		this.active = true;
	}
	
	@Override
	public String getId()
	{
		return id;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public IdModel setActive(boolean active)
	{
		this.active = setDirty("active", this.active, active);
		return this;
	}
	
	@Override
	public void preInsert()
	{
		if(id == null)
		{
			id = genereateId();
		}
		super.preInsert();
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object object)
	{
		if(this == object)
		{
			return true;
		}
		if(object == null)
		{
			return false;
		}
		if(getClass() != object.getClass())
		{
			return false;
		}
		IdModel model = (IdModel) object;
		if(id == null)
		{
			if(model.id != null)
			{
				return false;
			}
		}
		else if(!id.equals(model.id))
		{
			return false;
		}
		return true;
	}
	
}
