package roth.infrastructure.service;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import roth.infrastructure.Constants;
import roth.infrastructure.service.admin.AdminService;
import roth.infrastructure.service.app.AppService;
import roth.infrastructure.service.layout.LayoutService;
import roth.infrastructure.service.model.Message;
import roth.infrastructure.service.org.OrgService;
import roth.infrastructure.service.provider.ProviderService;
import roth.infrastructure.service.user.UserService;
import roth.lib.map.SerialMapper;
import roth.lib.map.json.JsonConfig;
import roth.lib.map.json.JsonMapper;
import roth.lib.net.http.endpoint.HttpEndpoint;
import roth.lib.net.http.endpoint.HttpError;
import roth.lib.net.http.endpoint.HttpErrorType;
import roth.lib.net.http.service.HttpService;

@SuppressWarnings("serial")
public class Endpoint extends HttpEndpoint<JsonConfig, JsonConfig> implements Constants
{
	protected static JsonMapper jsonMapper = new JsonMapper();
	protected static JsonConfig jsonConfig = new JsonConfig();
	
	@Override
	protected HttpService getService(HttpServletRequest request, HttpServletResponse response, String serviceName)
	{
		HttpService service = null;
		ServiceType serviceType = ServiceType.get(serviceName);
		switch(serviceType)
		{
			case USER:
			{
				service = new UserService();
				break;
			}
			case LAYOUT:
			{
				service = new LayoutService();
				break;
			}
			case ADMIN:
			{
				service = new AdminService();
				break;
			}
			case PROVIDER:
			{
				service = new ProviderService();
				break;
			}
			case ORG:
			{
				service = new OrgService();
				break;
			}
			case APP:
			{
				service = new AppService();
				break;
			}
			case NOT_FOUND:
			{
				
				break;
			}
		}
		return service;
	}
	
	@Override
	protected SerialMapper<JsonConfig> getRequestMapper(HttpServletRequest request, HttpServletResponse response)
	{
		return jsonMapper;
	}
	
	@Override
	protected JsonConfig getRequestConfig(HttpServletRequest request, HttpServletResponse response)
	{
		return jsonConfig;
	}
	
	@Override
	protected SerialMapper<JsonConfig> getResponseMapper(HttpServletRequest request, HttpServletResponse response)
	{
		return jsonMapper;
	}
	
	@Override
	protected JsonConfig getResponseConfig(HttpServletRequest request, HttpServletResponse response)
	{
		return jsonConfig;
	}
	
	@Override
	protected boolean isOriginAllowed(HttpServletRequest request, HttpServletResponse response)
	{
		return true;
	}
	
	@Override
	protected void exception(HttpServletRequest request, HttpServletResponse response, Exception e)
	{
		e.printStackTrace();
		ServiceResponse serviceResponse = new ServiceResponse();
		// CHECK EXCEPTION TYPES
		serviceResponse.addError(new Message(e.getMessage()));
		printResponse(request, response, serviceResponse);
	}
	
	@Override
	protected void errors(HttpServletRequest request, HttpServletResponse response, LinkedList<HttpError> errors)
	{
		ServiceResponse serviceResponse = new ServiceResponse();
		
		for(HttpError error : errors)
		{
			HttpErrorType errorType = error.getErrorType();
			serviceResponse.addError(new Message(errorType.name()));
			/*
			switch(errorType)
			{
				case HTTP_METHOD_MISSING:
				case HTTP_METHOD_NOT_SUPPORTED:
				case HTTP_METHOD_NOT_IMPLEMENTED:
				case HTTP_ORIGIN_UNSUPPORTED:
					serviceResponse.addError(new MessageModel(MessageType.CONNECTION_ERROR));
					break;
				case SERVICE_METHOD_NAME_MISSING:
				case SERVICE_METHOD_MISSING:
				case SERVICE_METHOD_PARAMETER_INVALID:
				case SERVICE_AJAX_INVALID:
				case SERVICE_CSRF_TOKEN_INVALID:
				case SERVICE_API_INVALID:
				case SERVICE_NOT_IMPLEMENTED:
					serviceResponse.addError(new MessageModel(MessageType.SERVICE_ERROR));
					break;
				case REQUEST_FIELD_REQUIRED:
				case REQUEST_FIELD_INVALID:
					serviceResponse.addError(new MessageModel(MessageType.REQUEST_ERROR));
					break;
			}
			*/
		}
		
		printResponse(request, response, serviceResponse);
	}
	
	protected void printResponse(HttpServletRequest request, HttpServletResponse response, ServiceResponse serviceResponse)
	{
		try 
		{
			getResponseMapper(request, response).serialize(serviceResponse, response.getOutputStream(), getResponseConfig(request, response));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
