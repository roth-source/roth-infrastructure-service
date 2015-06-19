package roth.infrastructure.service.model;

import java.io.Serializable;
import java.util.LinkedHashMap;

import roth.infrastructure.service.type.MessageType;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class Message implements Serializable
{
	@Property(name = "id")
	protected String id;
	
	@Property(name = "parameters")
	protected LinkedHashMap<String, String> parameters;

	public Message(String id)
	{
		this.id = id;
	}
	
	public Message(MessageType messageType)
	{
		this.id = messageType.toString();
	}
	
	public Message(MessageType messageType, LinkedHashMap<String, String> parameters)
	{
		this.id = messageType.toString();
		this.parameters = parameters;
	}

	public Message addParameter(String key, String value)
	{
		if(parameters == null)
		{
			parameters = new LinkedHashMap<String, String>(); 
		}
		parameters.put(key, value);
		return this;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public LinkedHashMap<String, String> getParameters()
	{
		return parameters;
	}
	
	public void setParameters(LinkedHashMap<String, String> parameters)
	{
		this.parameters = parameters;
	}
	
}
