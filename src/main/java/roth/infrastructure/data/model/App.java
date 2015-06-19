package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdOrgModel;
import roth.infrastructure.data.references.AppSettingReferences;
import roth.infrastructure.data.references.EnvReferences;
import roth.infrastructure.data.references.ReleaseReferences;
import roth.infrastructure.data.references.SourceReferences;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "app")
@SuppressWarnings("serial")
public class App extends IdOrgModel implements EnvReferences, SourceReferences, ReleaseReferences, AppSettingReferences
{
	@Property(name = "name")
	protected String name;
	
	protected App(Rdb rdb)
	{
		super(rdb);
	}
	
	public App(Db db, User user, Org org, String name)
	{
		super(db, user, org);
		this.name = name;
	}
	
	@Override
	public App mask()
	{
		return this;
	}
	
	public String getName()
	{
		return name;
	}
	
	public App setName(String name)
	{
		this.name = setDirty("name", this.name, name);
		return this;
	}
	
}
