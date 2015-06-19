package roth.infrastructure.util;

import java.util.Map;

import roth.infrastructure.data.Model;
import roth.lib.map.json.JsonConfig;
import roth.lib.map.json.JsonMapper;

public class JsonUtil
{
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final JsonConfig CONFIG 			= new JsonConfig().setTimeFormat(DEFAULT_FORMAT);
	public static final JsonConfig CONFIG_PRETTY 	= new JsonConfig().setTimeFormat(DEFAULT_FORMAT).setPrettyPrinting(true);
	public static final JsonConfig CONFIG_DEBUG 	= new JsonConfig().setTimeFormat(DEFAULT_FORMAT).setPrettyPrinting(true).setSerializeNulls(true);
	
	static
	{
		
	}
	
	private JsonUtil() {}
	
	public static String toJson(Model model, boolean prettyPrinting)
	{
		return JsonMapper.get().serialize(model, (prettyPrinting ? CONFIG_PRETTY : CONFIG));
	}
	
	public static String toJsonDebug(Model model)
	{
		return JsonMapper.get().serialize(model, CONFIG_DEBUG);
	}
	
	public static String toJson(Object object, boolean prettyPrinting)
	{
		return JsonMapper.get().serialize(object, (prettyPrinting ? CONFIG_PRETTY : CONFIG));
	}
	
	public static String toJsonDebug(Object object)
	{
		return JsonMapper.get().serialize(object, CONFIG_DEBUG);
	}
	
	public static String toJson(Map<String, ?> map, boolean prettyPrinting)
	{
		return JsonMapper.get().serialize(map, (prettyPrinting ? CONFIG_PRETTY : CONFIG));
	}
	
	public static String toJsonDebug(Map<String, ?> map)
	{
		return JsonMapper.get().serialize(map, CONFIG_DEBUG);
	}
	
}
