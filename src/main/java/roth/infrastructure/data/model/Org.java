package roth.infrastructure.data.model;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdModel;
import roth.infrastructure.data.references.AppReferences;
import roth.infrastructure.data.references.AuthorityReferences;
import roth.infrastructure.data.references.DomainReferences;
import roth.infrastructure.data.references.EnvReferences;
import roth.infrastructure.data.references.HostReferences;
import roth.infrastructure.data.references.MemberReferences;
import roth.infrastructure.data.references.NameserverReferences;
import roth.infrastructure.data.references.OrgSettingReferences;
import roth.infrastructure.data.references.PackageReferences;
import roth.infrastructure.data.references.RegistrarReferences;
import roth.infrastructure.data.references.RepoReferences;
import roth.infrastructure.data.references.ServerReferences;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "org")
@SuppressWarnings("serial")
public class Org extends IdModel implements MemberReferences, AppReferences, HostReferences, ServerReferences, RegistrarReferences, NameserverReferences, DomainReferences, AuthorityReferences, EnvReferences, RepoReferences, PackageReferences, OrgSettingReferences
{
	@Property(name = "name")
	protected String name;
	
	@Property(name = "apps", rdb = false)
	protected LinkedList<App> apps = new LinkedList<App>();
	
	protected Org(Rdb rdb)
	{
		super(rdb);
	}
	
	public Org(Db db, User user, String name)
	{
		super(db, user);
		this.name = name;
		active = true;
	}
	
	@Override
	public Org mask()
	{
		return this;
	}
	
	public Org maskApps()
	{
		this.apps.clear();
		for(App app : getApps())
		{
			this.apps.add(app.mask());
		}
		return this;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Org setName(String name)
	{
		this.name = setDirty("name", this.name, name);
		return this;
	}
	
}
