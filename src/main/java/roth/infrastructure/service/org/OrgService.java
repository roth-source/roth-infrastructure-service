package roth.infrastructure.service.org;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import roth.infrastructure.data.model.App;
import roth.infrastructure.data.model.Cert;
import roth.infrastructure.data.model.Domain;
import roth.infrastructure.data.model.Host;
import roth.infrastructure.data.model.Member;
import roth.infrastructure.data.model.Nameserver;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.Package;
import roth.infrastructure.data.model.Record;
import roth.infrastructure.data.model.Registrar;
import roth.infrastructure.data.model.Server;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.table.AppTable;
import roth.infrastructure.data.table.CertTable;
import roth.infrastructure.data.table.DomainTable;
import roth.infrastructure.data.table.MemberTable;
import roth.infrastructure.data.table.NameserverTable;
import roth.infrastructure.data.table.OrgTable;
import roth.infrastructure.data.table.RecordTable;
import roth.infrastructure.data.table.RegistrarTable;
import roth.infrastructure.data.table.ServerTable;
import roth.infrastructure.data.table.UserTable;
import roth.infrastructure.data.type.HostType;
import roth.infrastructure.data.type.PackageType;
import roth.infrastructure.data.type.RecordType;
import roth.infrastructure.data.type.RegistrarType;
import roth.infrastructure.data.type.ServerType;
import roth.infrastructure.service.IdRequest;
import roth.infrastructure.service.IdResponse;
import roth.infrastructure.service.InitResponse;
import roth.infrastructure.service.Service;
import roth.infrastructure.service.UpdateRequest;
import roth.infrastructure.service.UpdateResponse;
import roth.infrastructure.service.model.ActionMenuColumn;
import roth.infrastructure.service.model.Checkbox;
import roth.infrastructure.service.model.CheckboxColumn;
import roth.infrastructure.service.model.Column;
import roth.infrastructure.service.model.Date;
import roth.infrastructure.service.model.DateColumn;
import roth.infrastructure.service.model.Form;
import roth.infrastructure.service.model.Group;
import roth.infrastructure.service.model.Hidden;
import roth.infrastructure.service.model.OptGroup;
import roth.infrastructure.service.model.Option;
import roth.infrastructure.service.model.Renderer;
import roth.infrastructure.service.model.Select;
import roth.infrastructure.service.model.SelectColumn;
import roth.infrastructure.service.model.Table;
import roth.infrastructure.service.model.Text;
import roth.infrastructure.service.model.TextArea;
import roth.infrastructure.service.model.TextColumn;
import roth.infrastructure.task.CreateServerTask;
import roth.infrastructure.util.DigitalOceanUtil;
import roth.infrastructure.util.LinodeUtil;
import roth.lib.api.digitalocean.data.model.Image;
import roth.lib.api.digitalocean.data.model.Region;
import roth.lib.api.digitalocean.data.model.Size;
import roth.lib.api.linode.data.model.Datacenter;
import roth.lib.api.linode.data.model.Distribution;
import roth.lib.api.linode.data.model.Plan;
import roth.lib.net.http.annotation.Ajax;
import roth.lib.net.http.annotation.Post;
import roth.lib.net.http.task.HttpTaskResponse;
import roth.lib.net.http.util.TaskUtil;
import roth.lib.util.CalendarUtil;
import roth.lib.util.EnumUtil;
import roth.lib.util.NumberUtil;

@Post
public class OrgService extends Service
{
	protected User user;
	protected Org org;
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
	public InitResponse<Member> initMembers(IdRequest request)
	{
		InitResponse<Member> response = new InitResponse<Member>();
		Table<Member> table = new Table<Member>(org.getName() + " Users");
		table.addColumn(new CheckboxColumn("", "30px", "active", "active", "org", "updateMember"));
		table.addColumn(new Column("Type", "auto", "user.type"));
		table.addColumn(new Column("Email", "auto", "user.email"));
		//table.addColomn(new ActionMenuColomn());
		LinkedList<Member> members = MemberTable.get(db, user).findAllByOrg(org);
		for(Member member : members)
		{
			table.addRow(member.maskUser());
		}
		response.addTable(table);
		Group group = new Group("add", "Create Member", "Create", "org", "createMember");
		Select select = new Select("userId", "User", true);
		LinkedList<User> availableUsers = UserTable.get(db, user).findAllAvailable();
		if(!availableUsers.isEmpty())
		{
			for(User user : availableUsers)
			{
				select.addOption(new Option(user.getEmail() + " - " + user.getType(), user.getId()));
			}
			group.addField(select);
			response.addGroup(group);
		}
		return response;
	}
	
	@Ajax
	public IdResponse createMember(CreateMemberRequest request)
	{
		IdResponse response = new IdResponse();
		User user = UserTable.get(db, this.user).findById(request.getUserId());
		if(user != null)
		{
			Member orgUser = new Member(db, this.user, org, user).insert();
			response.setId(orgUser.getId());
		}
		return response;
	}
	
	@Ajax
	public UpdateResponse updateMember(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		Member orgUser = MemberTable.get(db, user).findById(request.getKey());
		if(orgUser != null)
		{
			switch(request.getName())
			{
				case "active":
				{
					orgUser.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			orgUser.update();
		}
		return response;
	}
	
	@Ajax
	public InitResponse<Domain> initDomains(IdRequest request)
	{
		InitResponse<Domain> response = new InitResponse<Domain>();
		LinkedList<Registrar> registrars = org.getRegistrars();
		for(Registrar registrar : registrars)
		{
			LinkedList<Domain> domains = registrar.getDomains();
			if(!domains.isEmpty())
			{
				Table<Domain> table = new Table<Domain>(org.getName() + " Domains - " + registrar.getDisplayName());
				table.addColumn(new CheckboxColumn("", "30px", "active", "active", "org", "updateDomain"));
				table.addColumn(new TextColumn("Name", "auto", "name", "name", "org", "updateDomain"));
				table.addColumn(new DateColumn("Expires On", "50%", "expiresOn", "expiresOn", "org", "updateDomain"));
				table.setRows(domains);
				response.addTable(table);
			}
		}
		LinkedList<Domain> domains = org.getDomains();
		if(!domains.isEmpty())
		{
			Table<Domain> table = new Table<Domain>(org.getName() + " Domains - " + RegistrarType.UNMANAGED.name());
			table.addColumn(new CheckboxColumn("", "30px", "active", "active", "org", "updateDomain"));
			table.addColumn(new TextColumn("Name", "auto", "name", "name", "org", "updateDomain"));
			table.addColumn(new DateColumn("Expires On", "50%", "expiresOn", "expiresOn", "org", "updateDomain"));
			table.setRows(domains);
			response.addTable(table);
		}
		Group group = new Group("create", "Create Domain", "Create", "org", "createDomain");
		Select select = new Select("registrarId", "Registrar", true);
		for(Registrar registrar : registrars)
		{
			select.addOption(new Option(registrar.getDisplayName(), registrar.getId()));
		}
		select.addOption(new Option(RegistrarType.UNMANAGED.name(), RegistrarType.UNMANAGED.name()));
		group.addField(select);
		group.addField(new Text("name", "Name", "text", true));
		group.addField(new Date("expiresOn", "Expires On", true));
		response.addGroup(group);
		return response;
	}
	
	@Ajax
	public IdResponse createDomain(CreateDomainRequest request)
	{
		IdResponse response = new IdResponse();
		Calendar expiresOn = CalendarUtil.parse(request.getExpiresOn(), "yyyy-MM-dd");
		if(!RegistrarType.UNMANAGED.name().equals(request.getRegistrarId()))
		{
			Registrar registrar = RegistrarTable.get(db, user).findById(request.getRegistrarId());
			if(registrar != null)
			{
				Domain domain = new Domain(db, registrar, request.getName(), expiresOn).insert();
				response.setId(domain.getId());
			}
		}
		else
		{
			Domain domain = new Domain(db, user, org, request.getName(), expiresOn).insert();
			response.setId(domain.getId());
		}
		return response;
	}
	
	@Ajax
	public UpdateResponse updateDomain(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		Domain domain = DomainTable.get(db, user).findById(request.getKey());
		if(domain != null)
		{
			switch(request.getName())
			{
				case "name":
				{
					domain.setName(request.getValue());
					break;
				}
				case "expiresOn":
				{
					Calendar expiresOn = CalendarUtil.parse(request.getValue(), "yyyy-MM-dd");
					domain.setExpiresOn(expiresOn);
					break;
				}
				case "active":
				{
					domain.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			domain.update();
		}
		return response;
	}
	
	@Ajax
	public InitResponse<Cert> initCerts(IdRequest request)
	{
		InitResponse<Cert> response = new InitResponse<Cert>();
		LinkedList<Cert> certs = CertTable.get(db, user).findAllByOrg(org);
		if(!certs.isEmpty())
		{
			LinkedList<Option> domainOptions = new LinkedList<Option>();
			LinkedList<Domain> domains = DomainTable.get(db, user).findAllByOrg(org);
			for(Domain domain : domains)
			{
				domainOptions.add(new Option(domain.getName(), domain.getId()));
			}
			Table<Cert> table = new Table<Cert>(org.getName() + " Certs");
			table.addColumn(new CheckboxColumn("", "30px", "active", "active", "org", "updateCert"));
			table.addColumn(new SelectColumn("Domain", "30%", "domain.name", "domainId", "org", "updateCert").setOptions(domainOptions));
			table.addColumn(new Column("Certificate", "auto", "certificate"));
			table.addColumn(new CheckboxColumn("Wildcard", "20%", "wildcard", "wildcard", "org", "updateCert"));
			for(Cert cert : certs)
			{
				table.addRow(cert.maskDomain());
			}
			response.addTable(table);
		}
		LinkedList<Domain> domains = DomainTable.get(db, user).findAllByOrg(org);
		if(!domains.isEmpty())
		{
			Group group = new Group("create", "Create Cert", "Create", "org", "createCert");
			Select select = new Select("domainId", "Domain", true);
			for(Domain domain : domains)
			{
				select.addOption(new Option(domain.getName(), domain.getId()));
			}
			group.addField(select);
			group.addField(new TextArea("certificate", "Certificate", true).setRows(10));
			group.addField(new TextArea("privateKey", "Private Key", true).setRows(10));
			group.addField(new Checkbox("wildcard", "Wildcard", true));
			response.addGroup(group);
		}
		return response;
	}
	
	@Ajax
	public IdResponse createCert(CreateCertRequest request)
	{
		IdResponse response = new IdResponse();
		Domain domain = DomainTable.get(db, user).findById(request.getDomainId());
		if(domain != null)
		{
			Cert cert = new Cert(db, user, domain, request.getCertificate(), request.getPrivateKey(), request.getWildcard()).insert();
			response.setId(cert.getId());
		}
		return response;
	}
	
	@Ajax
	public UpdateResponse updateCert(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		Cert cert = CertTable.get(db, user).findById(request.getKey());
		if(cert != null)
		{
			switch(request.getName())
			{
				case "domainId":
				{
					cert.setDomainId(request.getValue());
					break;
				}
				case "wildcard":
				{
					cert.setWildcard(request.getValue().equalsIgnoreCase("true"));
					break;
				}
				case "active":
				{
					cert.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			cert.update();
		}
		return response;
	}
	
	@Ajax
	public InitResponse<Server> initServers(IdRequest request)
	{
		InitResponse<Server> response = new InitResponse<Server>();
		LinkedHashMap<String, String> hostNameMap = new LinkedHashMap<String, String>();
		LinkedList<Host> hosts = org.getHosts();
		for(Host host : hosts)
		{
			String name = org.getName() + " Servers - " + host.getDisplayName();
			hostNameMap.put(host.getId(), name);
		}
		hostNameMap.put(HostType.UNMANAGED.name(), HostType.UNMANAGED.name());
		for(Entry<String, String> entry : hostNameMap.entrySet())
		{
			String id = entry.getKey();
			String name = entry.getValue();
			LinkedList<Server> servers = null;
			if(HostType.UNMANAGED.name().equals(id))
			{
				servers = org.getServers();
			}
			else
			{
				servers = ServerTable.get(db, user).findAllByHostId(id);
			}
			if(servers != null && !servers.isEmpty())
			{
				Table<Server> table = new Table<Server>(name);
				table.addColumn(new CheckboxColumn("", "30px", "active", "active", "org", "updateServer"));
				table.addColumn(new Column("Type", "15%", "type"));
				table.addColumn(new Column("Source", "15%", "source_type"));
				table.addColumn(new TextColumn("Name", "auto", "name", "name", "org", "updateServer"));
				table.addColumn(new TextColumn("Ip", "20%", "ip", "ip", "org", "updateServer"));
				table.addColumn(new Column("Created On", "20%", new Renderer("date", "createdOn")));
				table.addColumn(new ActionMenuColumn());
				table.setRows(servers);
				response.addTable(table);
			}
		}
		LinkedHashMap<HostType, LinkedList<Select>> hostSelects = new LinkedHashMap<HostType, LinkedList<Select>>();
		for(Host host : hosts)
		{
			HostType hostType = host.getType();
			LinkedList<Select> selects = hostSelects.get(hostType);
			if(selects == null)
			{
				switch(hostType)
				{
					case DIGITAL_OCEAN:
					{
						hostSelects.put(hostType, getDigitalOceanSelects(host.getApiKey()));
						break;
					}
					case LINODE:
					{
						hostSelects.put(hostType, getLinodeSelects(host.getApiKey()));
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
		Form form = new Form("Create Server", "Host");
		for(Host host : hosts)
		{
			String groupId = host.getId();
			Group group = new Group(host.getId(), host.getDisplayName(), "Create", "org", "createServer").setSuccess("client.page.initProgress(response);");
			group.addField(new Hidden("hostId", groupId));
			group.addField(new Text("name", "Server Name", "text", true));
			group.addField(new Text("password", "Root Password", "password", true).setValidate("password"));
			group.addField(new Text("confirm", "Confirm", "password", true).setValidate("password,confirm:" + groupId + "-password"));
			HostType hostType = host.getType();
			LinkedList<Select> selects = hostSelects.get(hostType);
			for(Select select : selects)
			{
				group.addField(select);
			}
			form.addGroup(group);
		}
		response.addForm(form);
		return response;
	}
	
	protected LinkedList<Select> getDigitalOceanSelects(String token)
	{
		LinkedList<Select> selects = new LinkedList<Select>();
		Select datacenterSelect = new Select("datacenter", "Datacenter", true);
		LinkedList<Region> regions = DigitalOceanUtil.getRegions(token);
		Collections.sort(regions, new Comparator<Region>()
		{
			@Override
			public int compare(Region region1, Region region2)
			{
				return region1.getName().compareTo(region2.getName());
			}
		});
		for(Region region : regions)
		{
			String name = region.getName().toLowerCase();
			String value = region.getSlug();
			datacenterSelect.addOption(new Option(name, value));
		}
		selects.add(datacenterSelect);
		
		Select planSelect = new Select("plan", "Plan", true);
		LinkedList<Size> sizes = DigitalOceanUtil.getSizes(token);
		for(Size size : sizes)
		{
			String name = size.getSlug() + " - " + NumberUtil.displayAmount((int) (size.getPriceMonthly() * 100));
			String value = size.getSlug();
			planSelect.addOption(new Option(name, value));
		}
		selects.add(planSelect);
		
		Select distributionSelect = new Select("distribution", "Distribution", true);
		OptGroup ubuntuOptGroup = new OptGroup("Ubuntu");
		LinkedList<Image> images = DigitalOceanUtil.getImages(token);
		Collections.sort(images, new Comparator<Image>()
		{
			@Override
			public int compare(Image image1, Image image2)
			{
				String name1 = image1.getDistribution().toLowerCase() + " " + image1.getName();
				String name2 = image2.getDistribution().toLowerCase() + " " + image2.getName();
				return name2.compareTo(name1);
			}
		});
		for(Image image : images)
		{
			String distributionName = image.getDistribution().toLowerCase();
			if(distributionName.contains(ServerType.UBUNTU.name().toLowerCase()) && image.getName().contains("x64"))
			{
				String name = distributionName + " " + image.getName().replaceAll(" x64$", "");
				String value = ServerType.UBUNTU.name() + ":" + image.getSlug();
				ubuntuOptGroup.addOption(new Option(name, value));
			}
		}
		if(!ubuntuOptGroup.getOptions().isEmpty())
		{
			distributionSelect.addOption(ubuntuOptGroup);
		}
		selects.add(distributionSelect);
		return selects;
	}
	
	protected LinkedList<Select> getLinodeSelects(String apiKey)
	{
		LinkedList<Select> selects = new LinkedList<Select>();
		Select datacenterSelect = new Select("datacenter", "Datacenter", true);
		LinkedList<Datacenter> datacenters = LinodeUtil.getDatacenters(apiKey);
		for(Datacenter datacenter : datacenters)
		{
			String name = datacenter.getAbbreviation();
			String value = String.valueOf(datacenter.getDatacenterId());
			datacenterSelect.addOption(new Option(name, value));
		}
		selects.add(datacenterSelect);
		
		Select planSelect = new Select("plan", "Plan", true);
		LinkedList<Plan> plans = LinodeUtil.getPlans(apiKey);
		for(Plan plan : plans)
		{
			String name = String.valueOf(Math.round(plan.getRam() / 1024f)) + "gb" + " - " + NumberUtil.displayAmount((int) (plan.getPrice().doubleValue() * 100));
			String value = String.valueOf(plan.getPlanId());
			planSelect.addOption(new Option(name, value));
		}
		selects.add(planSelect);
		
		Select distributionSelect = new Select("distribution", "Distribution", true);
		OptGroup ubuntuOptGroup = new OptGroup("Ubuntu");
		LinkedList<Distribution> distributions = LinodeUtil.getDistributions(apiKey);
		for(Distribution distribution : distributions)
		{
			String distributionName = distribution.getLabel().toLowerCase();
			if(distributionName.contains(ServerType.UBUNTU.name().toLowerCase()) && distribution.is64bit())
			{
				String name = distributionName.replaceAll(" lts$", "");
				String value = ServerType.UBUNTU.name() + ":" + String.valueOf(distribution.getDistributionId());
				distributionSelect.addOption(new Option(name, value));
			}
		}
		if(!ubuntuOptGroup.getOptions().isEmpty())
		{
			distributionSelect.addOption(ubuntuOptGroup);
		}
		selects.add(distributionSelect);
		return selects;
	}
	
	@Ajax
	public HttpTaskResponse createServer(CreateServerRequest request)
	{
		HttpTaskResponse response = new HttpTaskResponse();
		String taskId = TaskUtil.initProgress(httpServletRequest.getSession());
		response.setTaskId(taskId);
		CreateServerTask task = new CreateServerTask(db, user, httpServletRequest.getSession(), taskId, request);
		executor.execute(task);
		return response;
	}
	
	@Ajax
	public UpdateResponse updateServer(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		Server server = ServerTable.get(db, user).findById(request.getKey());
		if(server != null)
		{
			switch(request.getName())
			{
				case "name":
				{
					server.setName(request.getValue());
					break;
				}
				case "ip":
				{
					server.setIp(request.getValue());
					break;
				}
				case "active":
				{
					server.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			server.update();
		}
		return response;
	}
	
	@Ajax
	public InitResponse<Record> initRecords(IdRequest request)
	{
		InitResponse<Record> response = new InitResponse<Record>();
		LinkedList<Option> typeOptions = new LinkedList<Option>();
		for(RecordType recordType : RecordType.values())
		{
			typeOptions.add(new Option(recordType.name(), recordType.name()));
		}
		LinkedList<Option> serverOptions = new LinkedList<Option>();
		serverOptions.add(new Option("none", "null"));
		LinkedList<Server> servers = ServerTable.get(db, user).findAllByOrg(org);
		for(Server server : servers)
		{
			serverOptions.add(new Option(server.getDisplayName(), server.getId()));
		}
		LinkedList<Nameserver> nameservers = org.getNameservers();
		LinkedList<Domain> domains = DomainTable.get(db, user).findAllByOrg(org);
		for(Nameserver nameserver : nameservers)
		{
			for(Domain domain : domains)
			{
				LinkedList<Record> records = RecordTable.get(db, user).findAllByNameserverAndDomain(nameserver, domain);
				if(!records.isEmpty())
				{
					Table<Record> table = new Table<Record>(org.getName() + " Records - " + nameserver.getDisplayName() + " - " + domain.getName());
					table.addColumn(new CheckboxColumn("", "30px", "active", "active", "org", "updateRecord"));
					table.addColumn(new SelectColumn("Type", "10%", "type", "type", "org", "updateRecord").setOptions(typeOptions));
					table.addColumn(new TextColumn("Name", "25%", "name", "name", "org", "updateRecord"));
					table.addColumn(new SelectColumn("Server", "auto", "server.name", "serverId", "org", "updateRecord").setOptions(serverOptions));
					table.addColumn(new TextColumn("Value", "20%", "value", "value", "org", "updateRecord"));
					table.addColumn(new CheckboxColumn("Routed", "10%", "routed", "routed", "org", "updateRecord"));
					for(Record record : records)
					{
						table.addRow(record.maskServer());
					}
					response.addTable(table);
				}
			}
		}
		Group group = new Group("create", "Create Record", "Create", "org", "createRecord");
		Select typeSelect = new Select("type", "Type", true);
		typeSelect.setOptions(typeOptions);
		group.addField(typeSelect);
		Select nameserverSelect = new Select("nameserverId", "Nameserver", true);
		for(Nameserver nameserver : nameservers)
		{
			nameserverSelect.addOption(new Option(nameserver.getDisplayName(), nameserver.getId()));
		}
		group.addField(nameserverSelect);
		Select domainSelect = new Select("domainId", "Domain", true);
		for(Domain domain : domains)
		{
			domainSelect.addOption(new Option(domain.getName(), domain.getId()));
		}
		group.addField(domainSelect);
		group.addField(new Text("name", "Name", "text", true));
		Select serverSelect = new Select("serverId", "Server", true);
		serverSelect.setOptions(serverOptions);
		group.addField(serverSelect);
		group.addField(new Text("value", "Value", "text", false));
		group.addField(new Checkbox("routed", "Routed", true));
		response.addGroup(group);
		return response;
	}
	
	@Ajax
	public IdResponse createRecord(CreateRecordRequest request)
	{
		IdResponse response = new IdResponse();
		Nameserver nameserver = NameserverTable.get(db, user).findById(request.getNameserverId());
		Domain domain = DomainTable.get(db, user).findById(request.getDomainId());
		Server server = ServerTable.get(db, user).findById(request.getServerId());
		Record record = null;
		if(server != null)
		{
			record = new Record(db, user, nameserver, domain, request.getType(), request.getName(), server, request.isRouted()).insert();
		}
		else
		{
			record = new Record(db, user, nameserver, domain, request.getType(), request.getName(), request.getValue(), request.isRouted()).insert();
		}
		response.setId(record.getId());
		return response;
	}
	
	@Ajax
	public UpdateResponse updateRecord(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		Record record = RecordTable.get(db, user).findById(request.getKey());
		if(record != null)
		{
			switch(request.getName())
			{
				case "type":
				{
					record.setType(EnumUtil.fromString(request.getValue(), RecordType.class));
					break;
				}
				case "name":
				{
					record.setName(request.getValue());
					break;
				}
				case "serverId":
				{
					record.setServerId(!request.getValue().equalsIgnoreCase("null") ? request.getValue() : null);
					break;
				}
				case "value":
				{
					record.setValue(request.getValue());
					break;
				}
				case "routed":
				{
					record.setRouted(request.getValue().equalsIgnoreCase("true"));
					break;
				}
				case "active":
				{
					record.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			record.update();
		}
		return response;
	}
	
	@Ajax
	public InitResponse<Package> initPackages(IdRequest request)
	{
		InitResponse<Package> response = new InitResponse<Package>();
		LinkedList<Option> typeOptions = new LinkedList<Option>();
		for(PackageType type : PackageType.values())
		{
			typeOptions.add(new Option(type.name(), type.name()));
		}
		Table<Package> table = new Table<Package>(org.getName() + " Packages");
		table.addColumn(new CheckboxColumn("", "30px", "active", "active", "org", "updatePackage"));
		table.addColumn(new SelectColumn("Type", "20%", "type", "type", "org", "updatePackage").setOptions(typeOptions));
		table.addColumn(new TextColumn("Name", "30%", "name", "name", "org", "updatePackage"));
		table.addColumn(new TextColumn("Excludes", "", "excludes", "excludes", "org", "updatePackage"));
		LinkedList<Package> packages = org.getPackages();
		table.setRows(packages);
		response.addTable(table);
		return response;
	}
	
	@Ajax
	public InitResponse<App> initApps(IdRequest request)
	{
		InitResponse<App> response = new InitResponse<App>();
		Table<App> table = new Table<App>(org.getName() + " Apps");
		table.addColumn(new CheckboxColumn("", "30px", "active", "active", "org", "updateApp"));
		table.addColumn(new TextColumn("Name", "auto", "name", "name", "org", "updateApp"));
		LinkedList<App> apps = org.getApps();
		table.setRows(apps);
		response.addTable(table);
		Group group = new Group("create", "Create App", "Create", "org", "createApp").setSuccess("client.reloadComponents();");
		group.addField(new Text("name", "Name", "text", true));
		response.addGroup(group);
		return response;
	}
	
	@Ajax
	public IdResponse createApp(CreateAppRequest request)
	{
		IdResponse response = new IdResponse();
		App app = new App(db, user, org, request.getName()).insert();
		response.setId(app.getId());
		return response;
	}
	
	@Ajax
	public UpdateResponse updateApp(UpdateRequest request)
	{
		UpdateResponse response = new UpdateResponse();
		App app = AppTable.get(db, user).findById(request.getKey());
		if(app != null)
		{
			switch(request.getName())
			{
				case "name":
				{
					app.setName(request.getValue());
					break;
				}
				case "active":
				{
					app.setActive(request.getValue().equalsIgnoreCase("true"));
					break;
				}
			}
			app.update();
		}
		return response;
	}
	
}
