package roth.infrastructure.data.model;

import java.util.Calendar;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdOrgModel;
import roth.infrastructure.data.reference.RegistrarReference;
import roth.infrastructure.data.references.CertReferences;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "domain")
@SuppressWarnings("serial")
public class Domain extends IdOrgModel implements RegistrarReference, CertReferences
{
	@Property(name = "registrar_id")
	protected String registrarId;
	
	@Property(name = "name")
	protected String name;
	
	@Property(name = "expiresOn")
	protected Calendar expiresOn;
	
	protected Domain(Rdb rdb)
	{
		super(rdb);
	}
	
	public Domain(Db db, User user, Org org, String name, Calendar expiresOn)
	{
		super(db, user, org);
		this.name = name;
		this.expiresOn = expiresOn;
	}
	
	public Domain(Db db, Registrar registrar, String name, Calendar expiresOn)
	{
		super(db);
		this.registrarId = registrar.getId();
		this.name = name;
		this.expiresOn = expiresOn;
	}
	
	@Override
	public Domain mask()
	{
		return this;
	}
	
	@Override
	public String getRegistrarId()
	{
		return registrarId;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Calendar getExpiresOn()
	{
		return expiresOn;
	}
	
	public Domain setRegistrarId(String registrarId)
	{
		this.registrarId = setDirty("registrarId", this.registrarId, registrarId);
		return this;
	}
	
	public Domain setName(String name)
	{
		this.name = setDirty("name", this.name, name);
		return this;
	}
	
	public Domain setExpiresOn(Calendar expiresOn)
	{
		this.expiresOn = setDirty("expiresOn", this.expiresOn, expiresOn);
		return this;
	}
	
}
