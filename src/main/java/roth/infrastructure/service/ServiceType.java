package roth.infrastructure.service;

public enum ServiceType
{
	USER,
	LAYOUT,
	ADMIN,
	PROVIDER,
	ORG,
	APP,
	NOT_FOUND,
	;

	public static ServiceType get(String serviceName)
	{
		ServiceType serviceType = NOT_FOUND;
		try
		{
			serviceType = ServiceType.valueOf(serviceName.toUpperCase());
		}
		catch(Exception e)
		{
			
		}
		return serviceType;
	}
	
}
