package roth.infrastructure.service.provider;

import java.util.LinkedList;

import roth.infrastructure.data.model.Authority;
import roth.infrastructure.data.model.Host;
import roth.infrastructure.data.model.Member;
import roth.infrastructure.data.model.Nameserver;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.Registrar;
import roth.infrastructure.data.model.Repo;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.table.AuthorityTable;
import roth.infrastructure.data.table.HostTable;
import roth.infrastructure.data.table.MemberTable;
import roth.infrastructure.data.table.NameserverTable;
import roth.infrastructure.data.table.OrgTable;
import roth.infrastructure.data.table.RegistrarTable;
import roth.infrastructure.data.table.RepoTable;
import roth.infrastructure.data.type.AuthorityType;
import roth.infrastructure.data.type.HostType;
import roth.infrastructure.data.type.NameserverType;
import roth.infrastructure.data.type.RegistrarType;
import roth.infrastructure.data.type.RepoType;
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
import roth.infrastructure.service.model.Table;
import roth.infrastructure.service.model.Text;
import roth.infrastructure.service.model.TextColumn;
import roth.infrastructure.util.DigitalOceanUtil;
import roth.lib.api.digitalocean.data.model.Key;
import roth.lib.net.http.annotation.Ajax;
import roth.lib.net.http.annotation.Post;
import roth.lib.util.FileUtil;

@Post
public class ProviderService extends Service
{
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
		org = OrgTable.get(db, user).findById(id);
		if(org != null && user != null)
		{
			member = MemberTable.get(db, user).findByOrgAndUser(org, user);
			if(member != null)
			{
				authorized = true;
			}
		}
		return authorized;
	}
	
	@Ajax
	public InitResponse<Registrar> initRegistrars(IdRequest request)
	{
		InitResponse<Registrar> response = new InitResponse<Registrar>();
		for(RegistrarType registrarType : RegistrarType.values())
		{
			if(!RegistrarType.UNMANAGED.equals(registrarType))
			{
				LinkedList<Registrar> registrars = RegistrarTable.get(db, user).findAllByOrgAndType(org, registrarType);
				if(!registrars.isEmpty())
				{
					Table<Registrar> table = new Table<Registrar>(org.getName() + " Registrars - " + registrarType.name());
					table.addColumn(new CheckboxColumn("", "30px", "active", "active", "provider", "updateRegistrar"));
					table.addColumn(new TextColumn("Name", "auto", "name", "name", "provider", "updateRegistrar"));
					table.addColumn(new TextColumn("Api Key", "30%", "apiKey", "apiKey", "provider", "updateRegistrar"));
					table.addColumn(new TextColumn("Username", "30%", "username", "username", "provider", "updateRegistrar"));
					table.setRows(registrars);
					response.addTable(table);
				}
			}
		}
		Group group = new Group("create", "Create Registrar", "Create", "provider", "createRegistrar");
		Select select = new Select("type", "Type", true);
		for(RegistrarType registrarType : RegistrarType.values())
		{
			if(!RegistrarType.UNMANAGED.equals(registrarType))
			{
				select.addOption(new Option(registrarType.name(), registrarType.name()));
			}
		}
		group.addField(select);
		group.addField(new Text("name", "Name", "text", true).setValue("default"));
		group.addField(new Text("apiKey", "Api Key", "text", true));
		group.addField(new Text("username", "Username", "text", false));
		response.addGroup(group);
		return response;
	}
	
	@Ajax
	public IdResponse createRegistrar(CreateRegistrarRequest request)
	{
		IdResponse response = new IdResponse();
		Registrar registrar = new Registrar(db, user, org, request.getType(), request.getName(), request.getApiKey());
		String username = request.getUsername();
		if(username != null && !username.isEmpty())
		{
			registrar.setUsername(username);
		}
		registrar.insert();
		response.setId(registrar.getId());
		return response;
	}
	
	@Ajax
	public UpdateResponse updateRegistrar(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		Registrar registrar = RegistrarTable.get(db, user).findById(request.getKey());
		if(registrar != null)
		{
			switch(request.getName())
			{
				case "name":
				{
					registrar.setName(request.getValue());
					break;
				}
				case "apiKey":
				{
					registrar.setApiKey(request.getValue());
					break;
				}
				case "username":
				{
					registrar.setUsername(request.getValue());
					break;
				}
				case "active":
				{
					registrar.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			registrar.update();
		}
		return response;
	}
	
	@Ajax
	public InitResponse<Authority> initAuthorities(IdRequest request)
	{
		InitResponse<Authority> response = new InitResponse<Authority>();
		for(AuthorityType authorityType : AuthorityType.values())
		{
			LinkedList<Authority> authorities = AuthorityTable.get(db, user).findAllByOrgAndType(org, authorityType);
			if(!authorities.isEmpty())
			{
				Table<Authority> table = new Table<Authority>(org.getName() + " Authorities - " + authorityType.name());
				table.addColumn(new CheckboxColumn("", "30px", "active", "active", "provider", "updateAuthority"));
				table.addColumn(new TextColumn("Name", "auto", "name", "name", "provider", "updateAuthority"));
				table.addColumn(new TextColumn("Api Key", "30%", "apiKey", "apiKey", "provider", "updateAuthority"));
				table.addColumn(new TextColumn("Username", "30%", "username", "username", "provider", "updateAuthority"));
				table.setRows(authorities);
				response.addTable(table);
			}
		}
		Group group = new Group("create", "Create Authority", "Create", "provider", "createAuthority");
		Select select = new Select("type", "Type", true);
		for(AuthorityType authorityType : AuthorityType.values())
		{
			select.addOption(new Option(authorityType.name(), authorityType.name()));
		}
		group.addField(select);
		group.addField(new Text("name", "Name", "text", true).setValue("default"));
		group.addField(new Text("apiKey", "Api Key", "text", true));
		group.addField(new Text("username", "Username", "text", false));
		response.addGroup(group);
		return response;
	}
	
	@Ajax
	public IdResponse createAuthority(CreateAuthorityRequest request)
	{
		IdResponse response = new IdResponse();
		Authority authority = new Authority(db, user, org, request.getType(), request.getName(), request.getApiKey());
		String username = request.getUsername();
		if(username != null && !username.isEmpty())
		{
			authority.setUsername(username);
		}
		authority.insert();
		response.setId(authority.getId());
		return response;
	}
	
	@Ajax
	public UpdateResponse updateAuthority(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		Authority authority = AuthorityTable.get(db, user).findById(request.getKey());
		if(authority != null)
		{
			switch(request.getName())
			{
				case "name":
				{
					authority.setName(request.getValue());
					break;
				}
				case "apiKey":
				{
					authority.setApiKey(request.getValue());
					break;
				}
				case "username":
				{
					authority.setUsername(request.getValue());
					break;
				}
				case "active":
				{
					authority.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			authority.update();
		}
		return response;
	}
	
	@Ajax
	public InitResponse<Host> initHosts(IdRequest request)
	{
		InitResponse<Host> response = new InitResponse<Host>();
		for(HostType hostType : HostType.values())
		{
			if(!HostType.UNMANAGED.equals(hostType))
			{
				LinkedList<Host> hosts = HostTable.get(db, user).findAllByOrgAndType(org, hostType);
				if(!hosts.isEmpty())
				{
					Table<Host> table = new Table<Host>(org.getName() + " Hosts - " + hostType.name());
					table.addColumn(new CheckboxColumn("", "30px", "active", "active", "provider", "updateHost"));
					table.addColumn(new TextColumn("Name", "auto", "name", "name", "provider", "updateHost"));
					table.addColumn(new TextColumn("Api Key", "30%", "apiKey", "apiKey", "provider", "updateHost"));
					table.addColumn(new TextColumn("Username", "30%", "username", "username", "provider", "updateHost"));
					for(Host host : hosts)
					{
						if(host.getSshKeyId() == null)
						{
							setupSshKey(host);
						}
						table.addRow(host);
					}
					response.addTable(table);
				}
			}
		}
		Group group = new Group("create", "Create Host", "Create", "provider", "createHost");
		Select select = new Select("type", "Type", true);
		for(HostType hostType : HostType.values())
		{
			if(!HostType.UNMANAGED.equals(hostType))
			{
				select.addOption(new Option(hostType.name(), hostType.name()));
			}
		}
		group.addField(select);
		group.addField(new Text("name", "Name", "text", true).setValue("default"));
		group.addField(new Text("apiKey", "Api Key", "text", true));
		group.addField(new Text("username", "Username", "text", false));
		response.addGroup(group);
		return response;
	}
	
	@Ajax
	public IdResponse createHost(CreateHostRequest request)
	{
		IdResponse response = new IdResponse();
		Host host = new Host(db, user, org, request.getType(), request.getName(), request.getApiKey());
		String username = request.getUsername();
		if(username != null && !username.isEmpty())
		{
			host.setUsername(username);
		}
		host.insert();
		response.setId(host.getId());
		return response;
	}
	
	protected void setupSshKey(Host host)
	{
		String publicKey = FileUtil.toString(config.getPublicKeyFile());
		if(publicKey != null)
		{
			HostType hostType = host.getType();
			switch(hostType)
			{
				case DIGITAL_OCEAN:
				{
					Key key = DigitalOceanUtil.setupKey(host.getApiKey(), "infrastructure", publicKey);
					host.setSshKeyId(String.valueOf(key.getId()));
					host.update();
					break;
				}
				case LINODE:
				{
					
					break;
				}
				case VULTR:
				{
					break;
				}
				case RACKSPACE:
				{
					break;
				}
				default:
				{
					
				}
			}
		}
	}
	
	@Ajax
	public UpdateResponse updateHost(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		Host host = HostTable.get(db, user).findById(request.getKey());
		if(host != null)
		{
			switch(request.getName())
			{
				case "name":
				{
					host.setName(request.getValue());
					break;
				}
				case "apiKey":
				{
					host.setApiKey(request.getValue());
					break;
				}
				case "username":
				{
					host.setUsername(request.getValue());
					break;
				}
				case "active":
				{
					host.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			host.update();
		}
		return response;
	}
	
	@Ajax
	public InitResponse<Nameserver> initNameservers(IdRequest request)
	{
		InitResponse<Nameserver> response = new InitResponse<Nameserver>();
		for(NameserverType nameserverType : NameserverType.values())
		{
			LinkedList<Nameserver> nameservers = NameserverTable.get(db, user).findAllByOrgAndType(org, nameserverType);
			if(!nameservers.isEmpty())
			{
				Table<Nameserver> table = new Table<Nameserver>(org.getName() + " Nameservers - " + nameserverType.name());
				table.addColumn(new CheckboxColumn("", "30px", "active", "active", "provider", "updateNameserver"));
				table.addColumn(new TextColumn("Name", "auto", "name", "name", "provider", "updateNameserver"));
				table.addColumn(new TextColumn("Api Key", "30%", "apiKey", "apiKey", "provider", "updateNameserver"));
				table.addColumn(new TextColumn("Username", "30%", "username", "username", "provider", "updateNameserver"));
				table.setRows(nameservers);
				response.addTable(table);
			}
		}
		Group group = new Group("create", "Create Nameserver", "Create", "provider", "createNameserver");
		Select select = new Select("type", "Type", true);
		for(NameserverType nameserverType : NameserverType.values())
		{
			select.addOption(new Option(nameserverType.name(), nameserverType.name()));
		}
		group.addField(select);
		group.addField(new Text("name", "Name", "text", true).setValue("default"));
		group.addField(new Text("apiKey", "Api Key", "text", true));
		group.addField(new Text("username", "Username", "text", false));
		response.addGroup(group);
		return response;
	}
	
	@Ajax
	public IdResponse createNameserver(CreateNameserverRequest request)
	{
		IdResponse response = new IdResponse();
		Nameserver nameserver = new Nameserver(db, user, org, request.getType(), request.getName(), request.getApiKey());
		String username = request.getUsername();
		if(username != null && !username.isEmpty())
		{
			nameserver.setUsername(username);
		}
		nameserver.insert();
		response.setId(nameserver.getId());
		return response;
	}
	
	@Ajax
	public UpdateResponse updateNameserver(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		Nameserver nameserver = NameserverTable.get(db, user).findById(request.getKey());
		if(nameserver != null)
		{
			switch(request.getName())
			{
				case "name":
				{
					nameserver.setName(request.getValue());
					break;
				}
				case "apiKey":
				{
					nameserver.setApiKey(request.getValue());
					break;
				}
				case "username":
				{
					nameserver.setUsername(request.getValue());
					break;
				}
				case "active":
				{
					nameserver.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			nameserver.update();
		}
		return response;
	}
	
	@Ajax
	public InitResponse<Repo> initRepos(IdRequest request)
	{
		InitResponse<Repo> response = new InitResponse<Repo>();
		for(RepoType repoType : RepoType.values())
		{
			LinkedList<Repo> nameservers = RepoTable.get(db, user).findAllByOrgAndType(org, repoType);
			if(!nameservers.isEmpty())
			{
				Table<Repo> table = new Table<Repo>(org.getName() + " Repos - " + repoType.name());
				table.addColumn(new CheckboxColumn("", "30px", "active", "active", "provider", "updateRepo"));
				table.addColumn(new TextColumn("Name", "auto", "name", "name", "provider", "updateRepo"));
				table.addColumn(new TextColumn("Api Key", "30%", "apiKey", "apiKey", "provider", "updateRepo"));
				table.addColumn(new TextColumn("Username", "30%", "username", "username", "provider", "updateRepo"));
				table.setRows(nameservers);
				response.addTable(table);
			}
		}
		Group group = new Group("create", "Create Repo", "Create", "provider", "createRepo");
		Select select = new Select("type", "Type", true);
		for(RepoType repoType : RepoType.values())
		{
			select.addOption(new Option(repoType.name(), repoType.name()));
		}
		group.addField(select);
		group.addField(new Text("name", "Name", "text", true).setValue("default"));
		group.addField(new Text("apiKey", "Api Key", "text", false));
		group.addField(new Text("username", "Username", "text", false));
		group.addField(new Text("password", "Password", "password", false));
		response.addGroup(group);
		return response;
	}
	
	@Ajax
	public IdResponse createRepo(CreateRepoRequest request)
	{
		IdResponse response = new IdResponse();
		Repo repo = new Repo(db, user, org, request.getType(), request.getName());
		String apiKey = request.getApiKey();
		if(apiKey != null && !apiKey.isEmpty())
		{
			repo.setApiKey(apiKey);
		}
		String username = request.getUsername();
		if(username != null && !username.isEmpty())
		{
			repo.setUsername(username);
		}
		String password = request.getPassword();
		if(password != null && !password.isEmpty())
		{
			repo.setPassword(password);
		}
		repo.insert();
		response.setId(repo.getId());
		return response;
	}
	
	@Ajax
	public UpdateResponse updateRepo(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		Repo repo = RepoTable.get(db, user).findById(request.getKey());
		if(repo != null)
		{
			switch(request.getName())
			{
				case "name":
				{
					repo.setName(request.getValue());
					break;
				}
				case "apiKey":
				{
					repo.setApiKey(request.getValue());
					break;
				}
				case "username":
				{
					repo.setUsername(request.getValue());
					break;
				}
				case "active":
				{
					repo.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			repo.update();
		}
		return response;
	}
	
}
