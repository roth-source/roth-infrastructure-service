package roth.infrastructure.data;

import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.reference.OrgReference;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@SuppressWarnings("serial")
public abstract class IdOrgModel extends IdModel implements OrgReference
{
	@Property(name = "org_id")
	protected String orgId;
	
	protected IdOrgModel(Rdb rdb)
	{
		super(rdb);
	}

	public IdOrgModel(Db db, User user)
	{
		super(db, user);
	}
	
	public IdOrgModel(Db db, User user, Org org)
	{
		super(db, user);
		this.orgId = org.getId();
	}
	
	@Override
	public String getOrgId()
	{
		return orgId;
	}
	
	public IdOrgModel setOrgId(String orgId)
	{
		this.orgId = setDirty("orgId", this.orgId, orgId);
		return this;
	}
	
}
