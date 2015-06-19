package roth.infrastructure.service.admin;

import java.io.File;
import java.util.LinkedList;

import roth.infrastructure.data.model.Dist;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.table.DistTable;
import roth.infrastructure.data.table.OrgTable;
import roth.infrastructure.data.table.UserTable;
import roth.infrastructure.data.type.DistType;
import roth.infrastructure.data.type.UserType;
import roth.infrastructure.service.IdResponse;
import roth.infrastructure.service.InitResponse;
import roth.infrastructure.service.Service;
import roth.infrastructure.service.ServiceResponse;
import roth.infrastructure.service.UpdateRequest;
import roth.infrastructure.service.UpdateResponse;
import roth.infrastructure.service.model.CheckboxColumn;
import roth.infrastructure.service.model.Column;
import roth.infrastructure.service.model.Group;
import roth.infrastructure.service.model.Option;
import roth.infrastructure.service.model.Select;
import roth.infrastructure.service.model.SelectColumn;
import roth.infrastructure.service.model.Table;
import roth.infrastructure.service.model.Text;
import roth.infrastructure.service.model.TextColumn;
import roth.infrastructure.util.DataUtil;
import roth.lib.net.http.annotation.Ajax;
import roth.lib.net.http.annotation.Post;
import roth.lib.map.rdb.sql.Order;
import roth.lib.map.rdb.sql.Wheres;
import roth.lib.util.EnumUtil;

@Post
public class AdminService extends Service
{
	protected User user;
	
	@Override
	public boolean isAjaxAuthenticated(Ajax ajax)
	{
		return hasGlobalAdmin();
	}
	
	@Override
	public boolean isAuthorized(Object request)
	{
		boolean authorized = true;
		user = getUser();
		return authorized;
	}
	
	@Ajax
	public InitResponse<Org> initOrgs()
	{
		InitResponse<Org> response = new InitResponse<Org>();
		Table<Org> table = new Table<Org>("Orgs");
		table.addColumn(new CheckboxColumn("", "30px", "active", "active", "admin", "updateOrg"));
		table.addColumn(new TextColumn("Name", "auto", "name", "name", "admin", "updateOrg"));
		LinkedList<Org> orgs = OrgTable.get(db, user).findAllBy(new Wheres(), Order.byAsc("name"));
		table.setRows(orgs);
		response.addTable(table);
		Group group = new Group("create", "Create Org", "Create", "admin", "createOrg");
		group.addField(new Text("name", "Name", "text", true));
		response.addGroup(group);
		return response;
	}
	
	@Ajax
	public IdResponse createOrg(CreateOrgRequest request)
	{
		IdResponse response = new IdResponse();
		Org org = new Org(db, user, request.getName()).insert();
		response.setId(org.getId());
		return response;
	}
	
	@Ajax
	public UpdateResponse updateOrg(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		Org org = OrgTable.get(db, user).findById(request.getKey());
		if(org != null)
		{
			switch(request.getName())
			{
				case "name":
				{
					org.setName(request.getValue());
					break;
				}
				case "active":
				{
					org.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			org.update();
		}
		return response;
	}
	
	@Ajax
	public InitResponse<User> initUsers()
	{
		InitResponse<User> response = new InitResponse<User>();
		LinkedList<Option> typeOptions = new LinkedList<Option>();
		for(UserType userType : UserType.values())
		{
			typeOptions.add(new Option(userType.name(), userType.name()));
		}
		Table<User> table = new Table<User>("Users");
		table.addColumn(new CheckboxColumn("", "30px", "active", "active", "admin", "updateUser"));
		table.addColumn(new SelectColumn("Type", "20%", "type", "type", "admin", "updateUser").setOptions(typeOptions));
		table.addColumn(new TextColumn("Email", "auto", "email", "email", "admin", "updateUser"));
		table.addColumn(new TextColumn("Public Key", "50%", "publicKey", "publicKey", "admin", "updateUser"));
		LinkedList<User> users = UserTable.get(db).findAllBy(new Wheres(), Order.bySql("`type` ASC, `email` ASC"));
		table.setRows(users);
		response.addTable(table);
		Group group = new Group("create", "Create User", "Create", "admin", "createUser");
		group.addField(new Select("type", "Type", true).setOptions(typeOptions));
		group.addField(new Text("email", "Email", "text", true).setValidate("email"));
		group.addField(new Text("password", "Password", "password", true).setValidate("password"));
		group.addField(new Text("confirm", "Confirm", "password", true).setValidate("password,confirm:create-password"));
		response.addGroup(group);
		return response;
	}
	
	@Ajax
	public IdResponse createUser(CreateUserRequest request)
	{
		IdResponse response = new IdResponse();
		User user = new User(db, this.user, EnumUtil.fromString(request.getType(), UserType.class), request.getEmail(), DataUtil.digestPassword(request.getPassword())).insert();
		response.setId(user.getId());
		return response;
	}
	
	@Ajax
	public UpdateResponse updateUser(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		User user = UserTable.get(db).findById(request.getKey());
		if(user != null)
		{
			switch(request.getName())
			{
				case "type":
				{
					user.setType(EnumUtil.fromString(request.getValue(), UserType.class));
					break;
				}
				case "email":
				{
					user.setEmail(request.getValue());
					break;
				}
				case "publicKey":
				{
					user.setPublicKey(request.getValue());
					break;
				}
				case "active":
				{
					user.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			user.update();
		}
		return response;
	}
	
	@Ajax
	public InitResponse<Dist> initDists()
	{
		InitResponse<Dist> response = new InitResponse<Dist>();
		for(DistType distType : DistType.values())
		{
			LinkedList<Dist> dists = DistTable.get(db, user).findAllByType(distType);
			if(!dists.isEmpty())
			{
				Table<Dist> table = new Table<Dist>(distType.name());
				table.addColumn(new CheckboxColumn("", "30px", "active", "active", "admin", "updateDist"));
				table.addColumn(new Column("Name", "auto", "name"));
				table.setRows(dists);
				response.addTable(table);
			}
		}
		Group group = new Group("sync", "Sync Dists", "Sync", "admin", "syncDists");
		response.addGroup(group);
		return response;
	}
	
	@Ajax
	public ServiceResponse syncDists()
	{
		ServiceResponse response = new ServiceResponse();
		for(DistType distType : DistType.values())
		{
			syncDist(distType);
		}
		return response;
	}
	
	public void syncDist(DistType distType)
	{
		String type = distType.name().toLowerCase();
		File dataDir = config.getDataDir();
		File distDir = new File(dataDir, "dist/" + type);
		if(distDir.exists())
		{
			for(File file : distDir.listFiles())
			{
				if(file.isFile() && !file.isHidden() && file.getName().endsWith(".tar.gz"))
				{
					String name = file.getName().replaceAll("\\.tar\\.gz$", "");
					Dist dist = DistTable.get(db, user).findByName(distType, name);
					if(dist == null)
					{
						new Dist(db, user, distType, name).insert();
					}
				}
			}
		}
	}
	
}
