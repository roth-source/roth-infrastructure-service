package roth.infrastructure.util;

import java.util.LinkedList;

import roth.lib.api.linode.client.UtilityClient;
import roth.lib.api.linode.data.model.Datacenter;
import roth.lib.api.linode.data.model.Distribution;
import roth.lib.api.linode.data.model.Plan;

public class LinodeUtil
{
	protected static boolean debug = false;
	
	protected LinodeUtil()
	{
		
	}
	
	public static LinkedList<Datacenter> getDatacenters(String apiKey)
	{
		return new UtilityClient(apiKey, debug).getAvailDatacenters().getData();
	}
	
	public static LinkedList<Plan> getPlans(String apiKey)
	{
		return new UtilityClient(apiKey, debug).getAvailPlans().getData();
	}
	
	public static LinkedList<Distribution> getDistributions(String apiKey)
	{
		return new UtilityClient(apiKey, debug).getAvailDistributions().getData();
	}
	
	public static void createServer(String apiKey, String label, String datacenterId, String planId, String distributionId)
	{
		
	}
	
	public static void main(String[] args)
	{
		String apiKey = "CauyJWQg2i4oODeB6YReDPMCU6L0DLSlE6xSwUiO9QaWBMD1vnYRtmciLkYTD1jU";
		//getDatacenters(apiKey);
		getPlans(apiKey);
		//getDistributions(apiKey);
	}
	
}
