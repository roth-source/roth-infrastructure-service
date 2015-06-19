package roth.infrastructure.service.layout;

import java.util.LinkedList;

import roth.infrastructure.data.model.Org;
import roth.infrastructure.service.ServiceResponse;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class InitLayoutResponse extends ServiceResponse
{
	@Property(name = "userType")
	protected String userType;
	
	@Property(name = "orgs")
	protected LinkedList<Org> orgs = new LinkedList<Org>();
	
	public InitLayoutResponse()
	{
		
	}
	
	public String getUserType()
	{
		return userType;
	}
	
	public LinkedList<Org> getOrgs()
	{
		return orgs;
	}
	
	public InitLayoutResponse setUserType(String userType)
	{
		this.userType = userType;
		return this;
	}
	
	public InitLayoutResponse setOrgs(LinkedList<Org> orgs)
	{
		this.orgs = orgs;
		return this;
	}
	
	public InitLayoutResponse addOrg(Org org)
	{
		this.orgs.add(org);
		return this;
	}
	
}
