package roth.infrastructure.config;

import java.io.File;


public class DevConfig extends Config
{
	
	public DevConfig()
	{
		optDir = new File(".");
		dataDir = new File("roth/infrastructure/");
	}
	
}
