package roth.infrastructure.util;

import java.util.Base64;
import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import roth.lib.api.digitalocean.client.DropletActionClient;
import roth.lib.api.digitalocean.client.DropletClient;
import roth.lib.api.digitalocean.client.ImageClient;
import roth.lib.api.digitalocean.client.KeyClient;
import roth.lib.api.digitalocean.client.RegionClient;
import roth.lib.api.digitalocean.client.SizeClient;
import roth.lib.api.digitalocean.data.model.Action;
import roth.lib.api.digitalocean.data.model.Droplet;
import roth.lib.api.digitalocean.data.model.Image;
import roth.lib.api.digitalocean.data.model.Key;
import roth.lib.api.digitalocean.data.model.Region;
import roth.lib.api.digitalocean.data.model.Size;
import roth.lib.api.digitalocean.data.request.DropletRequest;
import roth.lib.api.digitalocean.data.request.KeyRequest;
import roth.lib.api.digitalocean.data.response.ActionResponse;
import roth.lib.api.digitalocean.data.response.DropletResponse;
import roth.lib.api.digitalocean.data.response.ImagesResponse;
import roth.lib.api.digitalocean.data.response.KeyResponse;
import roth.lib.api.digitalocean.data.response.KeysResponse;
import roth.lib.api.digitalocean.data.response.RegionsResponse;
import roth.lib.api.digitalocean.data.response.SizesResponse;
import roth.lib.util.MessageDigestUtil;
import roth.lib.util.NumberUtil;

public class DigitalOceanUtil
{
	
	protected static boolean debug = false;
	
	protected DigitalOceanUtil()
	{
		
	}
	
	public static LinkedList<Region> getRegions(String token)
	{
		RegionsResponse response = new RegionClient(token, debug).getRegions();
		if(response != null)
		{
			return response.getRegions();
		}
		return null;
	}
	
	public static LinkedList<Size> getSizes(String token)
	{
		SizesResponse response = new SizeClient(token, debug).getSizes();
		if(response != null)
		{
			return response.getSizes();
		}
		return null;
	}
	
	public static LinkedList<Image> getImages(String token)
	{
		ImagesResponse response = new ImageClient(token, debug).getDistributionImages();
		if(response != null)
		{
			return response.getImages();
		}
		return null;
	}
	
	public static LinkedList<Key> getKeys(String token)
	{
		KeysResponse response = new KeyClient(token, debug).getKeys();
		if(response != null)
		{
			return response.getKeys();
		}
		return null;
	}
	
	public static DropletResponse createServer(String token, String name, String region, String size, String image, String...keys)
	{
		LinkedList<Integer> sshKeys = new LinkedList<Integer>();
		if(keys != null)
		{
			for(String key : keys)
			{
				Integer sshKey = NumberUtil.parseInteger(key);
				if(sshKey != null)
				{
					sshKeys.add(sshKey);
				}
			}
		}
		return createServer(token, name, region, size, image, sshKeys);
	}
	
	public static DropletResponse createServer(String token, String name, String region, String size, String image, Collection<Integer> keys)
	{
		return new DropletClient(token, debug).createDroplet(new DropletRequest(name, region, size, image).setSshKeys(new LinkedList<Integer>(keys)));
	}
	
	public static Droplet getDroplet(String token, Integer dropletId)
	{
		DropletResponse response = new DropletClient(token, debug).getDroplet(dropletId);
		if(response != null)
		{
			return response.getDroplet();
		}
		return null;
	}
	
	public static Action getDropletAction(String token, Integer dropletId, Integer actionId)
	{
		ActionResponse response = new DropletActionClient(token, debug).getDropletAction(dropletId, actionId);
		if(response != null)
		{
			return response.getAction();
		}
		return null;
	}
	
	public static void deleteServer(String token, Integer id)
	{
		new DropletClient(token, debug).deleteDroplet(id); 
	}
	
	public static Key getKey(String token, String publicKey)
	{
		String fingerprint = getPublicKeyFingerprint(publicKey);
		KeyResponse response = new KeyClient(token, debug).getKey(fingerprint);
		if(response != null)
		{
			return response.getSshKey();
		}
		return null;
	}
	
	public static Key createKey(String token, String name, String publicKey)
	{
		KeyResponse response = new KeyClient(token, debug).createKey(new KeyRequest(name, publicKey));
		if(response != null)
		{
			return response.getSshKey();
		}
		return null;
	}
	
	public static Key setupKey(String token, String name, String publicKey)
	{
		Key key = getKey(token, publicKey);
		if(key == null)
		{
			key = createKey(token, name, publicKey);
		}
		return key;
	}
	
	public static String getPublicKeyFingerprint(String publicKey)
	{
		String fingerprint = null;
		Matcher matcher = Pattern.compile("\\s(\\S+?)\\s").matcher(publicKey);
		if(matcher.find())
		{
			String encodedKey = matcher.group(1);
			byte[] decodedKey = Base64.getDecoder().decode(encodedKey.getBytes());
			String hashedKey = MessageDigestUtil.digestMd5Base16(decodedKey);
			StringBuilder builder = new StringBuilder();
			String seperator = "";
			for(int i = 0; i < hashedKey.length(); i = i + 2)
			{
				builder.append(seperator);
				builder.append(hashedKey.subSequence(i, i + 2));
				seperator = ":";
			}
			fingerprint = builder.toString();
		}
		return fingerprint;
	}
	
	public static void main(String[] args)
	{
		//String token = "789e9aad08d1b141824b467932bb7757be65b61efb2ddb2a4d9660ea58711336";
		//getRegions(token);
		//getSizes(token);
		//getImages(token);
		//getKeys(token);
		//createServer(token, "testApi", "sfo1", "512mb", "ubuntu-15-04-x64", Arrays.asList(781971));
		//deleteServer(token, 5092932);
		//Key key = setupKey(token, "infrastructure", "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC2HMlIKl3SbqE4LZ96Kre34sU8joGaHWUIWb3EcqMc3Vf/JY8c+jYxtxtXdJN7MKr5as38JElsP+flvdnDVekv9CZg0aLwp0I3sa3pbPHIVxu6+q95OzouGnk22iVJpJwkMgQ8uTVZWlqLUwl1ehH3OPEGmnVcX1u8Lrm39uRfqf2wPxtlH2RZW9xGmluLfwVZ05NtXmQdm8kcfqLkxikX9u4i3m8LUdZiiTQ2gKJ0XVT+YZ00K9/r9IZoPBcvO6M1sCG+FmRl60zVwMt8UcsKdrY0Zv5yV1SQHykc/Y+5U0TU6nUQN8+kKRsC4J05QzwkU9RhhZewj5JRokxX3dbj User@office-mini.local");
		//System.out.println(key.getId());
		//deleteServer(token, 5203528);
		
	}
	
}
