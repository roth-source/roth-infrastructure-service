package roth.infrastructure.service.app;

import java.util.LinkedList;

import roth.infrastructure.data.model.App;
import roth.infrastructure.data.model.Env;
import roth.infrastructure.data.model.Member;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.model.Webserver;
import roth.infrastructure.data.table.AppTable;
import roth.infrastructure.data.table.EnvTable;
import roth.infrastructure.data.table.MemberTable;
import roth.infrastructure.data.type.EnvType;
import roth.infrastructure.service.IdRequest;
import roth.infrastructure.service.IdResponse;
import roth.infrastructure.service.InitResponse;
import roth.infrastructure.service.Service;
import roth.infrastructure.service.UpdateRequest;
import roth.infrastructure.service.UpdateResponse;
import roth.infrastructure.service.model.CheckboxColumn;
import roth.infrastructure.service.model.Group;
import roth.infrastructure.service.model.Option;
import roth.infrastructure.service.model.Select;
import roth.infrastructure.service.model.SelectColumn;
import roth.infrastructure.service.model.Table;
import roth.infrastructure.service.model.Text;
import roth.infrastructure.service.model.TextColumn;
import roth.lib.net.http.annotation.Ajax;
import roth.lib.net.http.annotation.Post;
import roth.lib.util.EnumUtil;

@Post
public class AppService extends Service
{
	protected App app;
	protected Org org;
	protected User user;
	protected Member member;
	
	@Override
	public boolean isAjaxAuthenticated(Ajax ajax)
	{
		return hasAdmin();
	}
	
	@Override
	public boolean isAuthorized(Object request)
	{
		boolean authorized = false;
		user = getUser();
		if(request instanceof IdRequest)
		{
			String id = ((IdRequest) request).getId();
			authorized = isAuthorized(id);
		}
		return authorized;
	}
	
	public boolean isAuthorized(String id)
	{
		boolean authorized = false;
		app = AppTable.get(db, user).findById(id);
		if(app != null && user != null)
		{
			org = app.getOrg();
			if(org != null && user != null)
			{
				member = MemberTable.get(db, user).findByOrgAndUser(org, user);
				if(member != null)
				{
					authorized = true;
				}
			}
		}
		return authorized;
	}
	
	@Ajax
	public InitResponse<Env> initEnvs(IdRequest request)
	{
		InitResponse<Env> response = new InitResponse<Env>();
		LinkedList<Option> options = new LinkedList<Option>();
		for(EnvType type : EnvType.values())
		{
			options.add(new Option(type.name(), type.name()));
		}
		Table<Env> table = new Table<Env>(app.getName() + " Envs");
		table.addColumn(new CheckboxColumn("", "30px", "active", "active", "app", "updateEnv"));
		table.addColumn(new SelectColumn("Type", "20%", "type", "type", "app", "updateEnv").setOptions(options));
		table.addColumn(new TextColumn("Name", "auto", "name", "name", "app", "updateEnv"));
		LinkedList<Env> envs = app.getEnvs();
		table.setRows(envs);
		response.addTable(table);
		Group group = new Group("create", "Create Env", "Create", "app", "createEnv");
		Select select = new Select("type", "Type", true);
		for(EnvType type : EnvType.values())
		{
			select.addOption(new Option(type.name(), type.name()));
		}
		group.addField(select);
		group.addField(new Text("name", "Name", "text", true));
		response.addGroup(group);
		return response;
	}
	
	@Ajax
	public IdResponse createEnv(CreateEnvRequest request)
	{
		IdResponse response = new IdResponse();
		Env env = new Env(db, user, app, request.getType(), request.getName()).insert();
		response.setId(env.getId());
		return response;
	}
	
	@Ajax
	public UpdateResponse updateEnv(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		Env env = EnvTable.get(db, user).findById(request.getKey());
		if(env != null)
		{
			switch(request.getName())
			{
				case "type":
				{
					env.setType(EnumUtil.fromString(request.getValue(), EnvType.class));
					break;
				}
				case "name":
				{
					env.setName(request.getValue());
					break;
				}
				case "active":
				{
					env.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			env.update();
		}
		return response;
	}
	
	@Ajax
	public InitResponse<Webserver> initWebservers(IdRequest request)
	{
		InitResponse<Webserver> response = new InitResponse<Webserver>();
		LinkedList<Env> envs = app.getEnvs();
		for(Env env : envs)
		{
			Table<Webserver> table = new Table<Webserver>(app.getName() + " Webservers - " + env.getName());
			table.addColumn(new CheckboxColumn("", "30px", "active", "active", "app", "updateWebserver"));
			
			//table.setRows(envs);
			response.addTable(table);
		}
		
		
		Group group = new Group("create", "Create Webserver", "Create", "app", "createWebserver");
		Select select = new Select("type", "Type", true);
		for(EnvType type : EnvType.values())
		{
			select.addOption(new Option(type.name(), type.name()));
		}
		group.addField(select);
		group.addField(new Text("name", "Name", "text", true));
		response.addGroup(group);
		return response;
	}
	
}
