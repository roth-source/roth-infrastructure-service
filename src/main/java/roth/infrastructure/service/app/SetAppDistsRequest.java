package roth.infrastructure.service.app;

import roth.infrastructure.service.IdRequest;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class SetAppDistsRequest extends IdRequest
{
	@Property(name = "jdkDist")
	protected String jdkDist;
	
	@Property(name = "jettyDist")
	protected String jettyDist;
	
	@Property(name = "mysqlDist")
	protected String mysqlDist;
	
	@Property(name = "mavenDist")
	protected String mavenDist;
	
	public SetAppDistsRequest()
	{
		
	}
	
	public String getJdkDist()
	{
		return jdkDist;
	}
	
	public String getJettyDist()
	{
		return jettyDist;
	}
	
	public String getMysqlDist()
	{
		return mysqlDist;
	}
	
	public String getMavenDist()
	{
		return mavenDist;
	}
	
	public SetAppDistsRequest setJdkDist(String jdkDist)
	{
		this.jdkDist = jdkDist;
		return this;
	}
	
	public SetAppDistsRequest setJettyDist(String jettyDist)
	{
		this.jettyDist = jettyDist;
		return this;
	}
	
	public SetAppDistsRequest setMysqlDist(String mysqlDist)
	{
		this.mysqlDist = mysqlDist;
		return this;
	}
	
	public SetAppDistsRequest setMavenDist(String mavenDist)
	{
		this.mavenDist = mavenDist;
		return this;
	}
	
}
