package roth.infrastructure.data;

import java.io.PrintWriter;

import roth.infrastructure.config.Config;
import roth.infrastructure.config.Environment;
import roth.lib.map.rdb.Rdb;

public class Db extends Rdb
{
	protected static final InitHandler INFRASTRUCTURE_INIT = new InitHandler()
	{
		public void init(Db db, Environment environment)
		{
			Config config = environment.getConfig();
			db.init(config.getDbDriver(), config.getDbUrl(), config.getDbUsername(), config.getDbPassword());
		}
	};
	
	protected Environment environment;
	protected Config config;
	protected boolean debug;
	protected String serverPrefix;
	
	protected Db(Environment environment, InitHandler initHandler)
	{
		super();
		initHandler.init(this, environment);
		this.environment = environment;
		config = environment.getConfig();
		if(environment.isDev() && logWriter == null)
		{
			setLogWriter(new PrintWriter(System.out, true));
		}
		serverPrefix = "0";
	}
	
	public static Db get(Environment environment)
	{
		return new Db(environment, INFRASTRUCTURE_INIT);
	}
	
	public Environment getEnvironment()
	{
		return environment;
	}
	
	public Config getConfig()
	{
		return config;
	}
	
	public String getServerPrefix()
	{
		return serverPrefix;
	}
	
	public boolean isDebug()
	{
		return debug || environment.isDev();
	}
	
	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}
	
	protected static interface InitHandler
	{
		void init(Db db, Environment environment);
	}
	
}
