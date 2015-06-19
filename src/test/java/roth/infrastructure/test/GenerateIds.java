package roth.infrastructure.test;

import roth.lib.util.IdUtil;

public class GenerateIds
{
	
	public static void main(String[] args)
	{
		for(int i = 0; i < 10; i++)
		{
			System.out.println(IdUtil.uuid("0"));
		}
	}
	
}
