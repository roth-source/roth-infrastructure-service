package roth.infrastructure.test;

import roth.infrastructure.test.data.DistType;
import roth.infrastructure.test.data.Dist;
import roth.lib.map.json.JsonConfig;
import roth.lib.map.json.JsonMapper;

public class EnumTest
{
	
	public static void main(String[] args)
	{
		JsonMapper mapper = new JsonMapper();
		JsonConfig config = new JsonConfig().setPrettyPrinting(true);
		
		Dist dist = new Dist(DistType.JDK, "jdk-name");
		String serializedDist = mapper.serialize(dist, config);
		System.out.println(serializedDist);
		
		Dist deserializedDist = mapper.deserialize(serializedDist, Dist.class);
		System.out.println(deserializedDist);
	}
	
}
