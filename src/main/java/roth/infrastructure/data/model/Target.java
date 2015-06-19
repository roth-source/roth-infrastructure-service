package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdModel;
import roth.infrastructure.data.reference.BranchReference;
import roth.infrastructure.data.reference.EnvReference;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "target")
@SuppressWarnings("serial")
public class Target extends IdModel implements BranchReference, EnvReference
{
	@Property(name = "branch_id")
	protected String branchId;
	
	@Property(name = "env_id")
	protected String envId;
	
	protected Target(Rdb rdb)
	{
		super(rdb);
	}
	
	public Target(Db db, User user, Branch branch, Env env)
	{
		super(db, user);
		this.branchId = branch.getId();
		this.envId = env.getId();
	}
	
	@Override
	public Target mask()
	{
		return this;
	}
	
	@Override
	public String getBranchId()
	{
		return branchId;
	}
	
	@Override
	public String getEnvId()
	{
		return envId;
	}
	
	public Target setBranchId(String branchId)
	{
		this.branchId = setDirty("branchId", this.branchId, branchId);
		return this;
	}
	
	public Target setEnvId(String envId)
	{
		this.envId = setDirty("envId", this.envId, envId);
		return this;
	}
	
}
