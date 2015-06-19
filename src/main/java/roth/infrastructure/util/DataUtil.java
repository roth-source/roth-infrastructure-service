package roth.infrastructure.util;

import roth.lib.util.MessageDigestUtil;

public class DataUtil
{
	
	protected DataUtil()
	{
		
	}
	
	public static String digestPassword(String password)
	{
		return MessageDigestUtil.digestSha256Base62(password);
	}
	
	public static String mask(String data, int length)
	{
		return data != null ? data.subSequence(0, Math.min(length, data.length())) + "..." : "";
	}
	
	public static String maskEnd(String data, int length)
	{
		return data != null ? "..." + data.subSequence(data.length() - Math.min(length, data.length()), data.length()) : "";
	}
	
}
