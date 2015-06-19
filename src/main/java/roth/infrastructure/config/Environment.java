package roth.infrastructure.config;


public enum Environment
{
	PROD			(new Config()),
	DEV				(new DevConfig()),
	;
	
	protected Config config;
	
	Environment(Config config)
	{
		this.config = config;
	}
	
	public Config getConfig()
	{
		return config;
	}
	
	public static Environment get(String name)
	{
		Environment environment = DEV;
		try
		{
			name = name.trim().toUpperCase();
			environment = Environment.valueOf(name);
		}
		catch(Exception e)
		{
			
		}
		return environment;
	}
	
	public String getName()
	{
		String name = name().toLowerCase();
		return name;
	}
	
	public boolean isDev()
	{
		return this == DEV;
	}
	
	public boolean isProd()
	{
		return this == PROD;
	}
	
}
