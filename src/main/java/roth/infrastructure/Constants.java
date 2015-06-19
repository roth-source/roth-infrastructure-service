package roth.infrastructure;

public interface Constants
{
	String OPT					= "/opt";
	
	String JDK					= OPT + "/jdk";
	String JDK_DIST				= JDK + "/dist";
	String JDK_HOME				= JDK + "/home";
	String JAVA_HOME			= JDK_HOME + "/jre";
	
	String JETTY				= OPT + "/jetty";
	String JETTY_DIST			= JETTY + "/dist";
	String JETTY_HOME			= JETTY + "/home";
	String JETTY_BASE			= JETTY + "/base";
	String JETTY_BASE_START_D	= JETTY_BASE + "/start.d";
	String JETTY_BASE_CONF		= JETTY_BASE + "/conf";
	String JETTY_BASE_CERT		= JETTY_BASE + "/cert";
	String JETTY_BASE_LOGS		= JETTY_BASE + "/logs";
	String JETTY_BASE_WEBAPPS	= JETTY_BASE + "/webapps";
	String JETTY_BASE_RESOURCES	= JETTY_BASE + "/resources";
	String JETTY_BASE_TEMP		= JETTY_BASE + "/temp";
	
	String MYSQL				= OPT + "/mysql";
	String MYSQL_DIST			= MYSQL + "/dist";
	String MYSQL_HOME			= MYSQL + "/home";
	String MYSQL_BASE			= MYSQL + "/base";
	String MYSQL_BASE_CONF		= MYSQL_BASE + "/conf";
	String MYSQL_BASE_DATA		= MYSQL_BASE + "/data";
	
}
