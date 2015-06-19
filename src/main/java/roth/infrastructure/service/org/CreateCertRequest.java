package roth.infrastructure.service.org;

import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class CreateCertRequest extends IdRequest
{
	@Property(name = "domainId")
	protected String domainId;
	
	@Property(name = "certificate")
	protected String certificate;
	
	@Property(name = "privateKey")
	protected String privateKey;
	
	@Property(name = "wildcard")
	protected Boolean wildcard = false;
	
	public CreateCertRequest()
	{
		
	}
	
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
	
	public CreateCertRequest setDomainId(String domainId)
	{
		this.domainId = domainId;
		return this;
	}
	
	public CreateCertRequest setCertificate(String certificate)
	{
		this.certificate = certificate;
		return this;
	}
	
	public CreateCertRequest setPrivateKey(String privateKey)
	{
		this.privateKey = privateKey;
		return this;
	}
	
	public CreateCertRequest setWildcard(Boolean wildcard)
	{
		this.wildcard = wildcard;
		return this;
	}
	
}
