package roth.infrastructure.config;

import java.io.File;

public class Config
{
	// DIRS
	protected File homeDir = new File(System.getProperty("user.home"));
	protected File sshDir = new File(homeDir, ".ssh/");
	protected File optDir = new File("/opt/");
	protected File dataDir = new File(optDir, "roth/infrastructure/");
	
	// FILES
	protected File publicKeyFile = new File(sshDir, "id_rsa.pub");
	
	// DATABASE
	protected String dbDriver = "com.mysql.jdbc.Driver";
	protected String dbUrl = "jdbc:mysql://127.0.0.1/infrastructure?createDatabaseIfNotExist=true";
	protected String dbUsername = "root";
	protected String dbPassword = "";
	
	public Config()
	{
		
	}
	
	public File getHomeDir()
	{
		return homeDir;
	}
	
	public File getSshDir()
	{
		return sshDir;
	}
	
	public File getOptDir()
	{
		return optDir;
	}
	
	public File getDataDir()
	{
		return dataDir;
	}
	
	public File getPublicKeyFile()
	{
		return publicKeyFile;
	}
	
	public String getDbDriver()
	{
		return dbDriver;
	}
	
	public String getDbUrl()
	{
		return dbUrl;
	}
	
	public String getDbUsername()
	{
		return dbUsername;
	}
	
	public String getDbPassword()
	{
		return dbPassword;
	}
	
	public static void main(String[] args)
	{
		
	}
	
}
