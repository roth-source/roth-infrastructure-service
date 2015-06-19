package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdModel;
import roth.infrastructure.data.reference.SourceReference;
import roth.infrastructure.data.references.DeployReferences;
import roth.infrastructure.data.references.TargetReferences;
import roth.infrastructure.data.type.BranchType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "branch")
@SuppressWarnings("serial")
public class Branch extends IdModel implements SourceReference, TargetReferences, DeployReferences
{
	@Property(name = "type")
	protected BranchType type;
	
	@Property(name = "source_id")
	protected String sourceId;
	
	@Property(name = "name")
	protected String name;
	
	protected Branch(Rdb rdb)
	{
		super(rdb);
	}
	
	public Branch(Db db, User user, Source source, BranchType type, String name)
	{
		super(db, user);
		this.sourceId = source.getId();
		this.type = type;
		this.name = name;
	}
	
	@Override
	public Branch mask()
	{
		return this;
	}
	
	public BranchType getType()
	{
		return type;
	}
	
	@Override
	public String getSourceId()
	{
		return sourceId;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Branch setType(BranchType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
	public Branch setSourceId(String sourceId)
	{
		this.sourceId = setDirty("sourceId", this.sourceId, sourceId);
		return this;
	}
	
	public Branch setName(String name)
	{
		this.name = setDirty("name", this.name, name);
		return this;
	}
	
}
