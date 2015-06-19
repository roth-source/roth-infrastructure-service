package roth.infrastructure.data.model;

import java.util.Calendar;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdModel;
import roth.infrastructure.data.reference.DomainReference;
import roth.infrastructure.util.DataUtil;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "cert")
@SuppressWarnings("serial")
public class Cert extends IdModel implements DomainReference
{
	@Property(name = "domain_id")
	protected String domainId;
	
	@Property(name = "certificate")
	protected String certificate;
	
	@Property(name = "privateKey", json = false)
	protected String privateKey;
	
	@Property(name = "wildcard")
	protected Boolean wildcard;
	
	@Property(name = "startsOn")
	protected Calendar startsOn;
	
	@Property(name = "endsOn")
	protected Calendar endsOn;
	
	@Property(name = "domain")
	protected Domain domain;
	
	protected Cert(Rdb rdb)
	{
		super(rdb);
	}
	
	public Cert(Db db, User user, Domain domain, String certificate, String privateKey, Boolean wildcard)
	{
		super(db, user);
		this.domainId = domain.getId();
		this.certificate = certificate;
		this.privateKey = privateKey;
		this.wildcard = wildcard;
	}
	
	@Override
	public Cert mask()
	{
		this.certificate = DataUtil.mask(certificate, 10);
		return this;
	}
	
	public Cert maskDomain()
	{
		this.domain = getDomain();
		return this;
	}
	
	@Override
	public String getDomainId()
	{
		return domainId;
	}
	
	public String getCertificate()
	{
		return certificate;
	}
	
	public String getPrivateKey()
	{
		return privateKey;
	}
	
	public Boolean getWildcard()
	{
		return wildcard;
	}
	
	public Calendar getStartsOn()
	{
		return startsOn;
	}
	
	public Calendar getEndsOn()
	{
		return endsOn;
	}
	
	public Cert setDomainId(String domainId)
	{
		this.domainId = setDirty("domainId", this.domainId, domainId);
		return this;
	}
	
	public Cert setCertificate(String certificate)
	{
		this.certificate = setDirty("certificate", this.certificate, certificate);
		return this;
	}
	
	public Cert setPrivateKey(String privateKey)
	{
		this.privateKey = setDirty("privateKey", this.privateKey, privateKey);
		return this;
	}
	
	public Cert setWildcard(Boolean wildcard)
	{
		this.wildcard = setDirty("wildcard", this.wildcard, wildcard);
		return this;
	}
	
	public Cert setStartsOn(Calendar startsOn)
	{
		this.startsOn = setDirty("startsOn", this.startsOn, startsOn);
		return this;
	}
	
	public Cert setEndsOn(Calendar endsOn)
	{
		this.endsOn = setDirty("endsOn", this.endsOn, endsOn);
		return this;
	}
	
}
