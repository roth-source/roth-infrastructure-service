package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdModel;
import roth.infrastructure.data.reference.BranchReference;
import roth.infrastructure.data.reference.ReleaseReference;
import roth.infrastructure.data.references.ContextReferences;
import roth.infrastructure.data.type.DeployType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "deploy")
@SuppressWarnings("serial")
public class Deploy extends IdModel implements BranchReference, ReleaseReference, ContextReferences
{
	@Property(name = "type")
	protected DeployType type;
	
	@Property(name = "branch_id")
	protected String branchId;
	
	@Property(name = "release_id")
	protected String releaseId;
	
	@Property(name = "name")
	protected String name;
	
	protected Deploy(Rdb rdb)
	{
		super(rdb);
	}
	
	public Deploy(Db db, User user, Branch branch, Release release, DeployType type)
	{
		super(db, user);
		this.branchId = branch.getId();
		this.releaseId = release.getId();
		this.type = type;
	}
	
	@Override
	public Deploy mask()
	{
		return this;
	}
	
	public DeployType getType()
	{
		return type;
	}
	
	@Override
	public String getBranchId()
	{
		return branchId;
	}
	
	@Override
	public String getReleaseId()
	{
		return releaseId;
	}
	
	public Deploy setType(DeployType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
	public Deploy setBranchId(String branchId)
	{
		this.branchId = setDirty("branchId", this.branchId, branchId);
		return this;
	}
	
	public Deploy setReleaseId(String releaseId)
	{
		this.releaseId = setDirty("releaseId", this.releaseId, releaseId);
		return this;
	}
	
}
