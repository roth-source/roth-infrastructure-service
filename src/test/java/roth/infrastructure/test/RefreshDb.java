package roth.infrastructure.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;

import roth.infrastructure.config.Environment;
import roth.infrastructure.data.Db;
import roth.lib.map.rdb.RdbConnection;
import roth.lib.util.IoUtil;


public class RefreshDb
{
	protected static Environment environment = Environment.get("dev");
	protected static Db db = Db.get(environment);
	
	public static void main(String[] args)
	{
		delete();
		init();
	}
	
	protected static void delete()
	{
		Db db = Db.get(environment);
		try(RdbConnection connection = db.getConnection();)
		{
			try(Statement statement = connection.createStatement();)
			{
				statement.execute("DROP DATABASE IF EXISTS `infrastructure`");
				statement.execute("CREATE DATABASE `infrastructure`");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	protected static void init()
	{
		try(RdbConnection connection = db.getConnection())
		{
			boolean installed = false;
			try(ResultSet resultSet = connection.getMetaData().getTables(null, null, "user", new String[] {"TABLE"}))
			{
				installed = resultSet.next();
			}
			if(!installed)
			{
				LinkedList<String> commands = getSqlCommands("roth/infrastructure/sql/create_infrastructure_generated.sql");
				try(Statement statement = connection.createStatement();)
				{
					for(String command : commands)
					{
						if(!command.trim().isEmpty())
						{
							statement.execute(command);
						}
					}
					connection.commit();
				}
				File seedDir = new File("misc/seed/");
				for(File file : seedDir.listFiles())
				{
					if(file.isFile() && !file.isHidden() && file.getName().endsWith(".sql"))
					{
						LinkedList<String> seedCommands = getSqlCommands(file);
						try(Statement statement = connection.createStatement();)
						{
							for(String command : seedCommands)
							{
								if(!command.trim().isEmpty())
								{
									statement.execute(command);
								}
							}
							connection.commit();
						}
					}
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	protected static LinkedList<String> getSqlCommands(String file)
	{
		LinkedList<String> commands = new LinkedList<String>();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try(InputStream input = classLoader.getResourceAsStream(file);)
		{
			commands = getSqlCommands(input);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return commands;
	}
	
	protected static LinkedList<String> getSqlCommands(File file)
	{
		LinkedList<String> commands = new LinkedList<String>();
		try(InputStream input = new FileInputStream(file);)
		{
			commands = getSqlCommands(input);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return commands;
	}
	
	protected static LinkedList<String> getSqlCommands(InputStream input) throws IOException
	{
		LinkedList<String> commands = new LinkedList<String>();
		String sql = new String(IoUtil.toBytes(input));
		commands = new LinkedList<String>(Arrays.asList(sql.split(";")));
		return commands;
	}
	
}
