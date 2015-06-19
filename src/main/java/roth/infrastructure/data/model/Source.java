package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdAppModel;
import roth.infrastructure.data.reference.PackageReference;
import roth.infrastructure.data.reference.RepoReference;
import roth.infrastructure.data.type.SourceType;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "source")
@SuppressWarnings("serial")
public class Source extends IdAppModel implements RepoReference, PackageReference
{
	@Property(name = "type")
	protected SourceType type;
	
	@Property(name = "repo_id")
	protected String repoId;
	
	@Property(name = "package_id")
	protected String packageId;
	
	@Property(name = "url")
	protected String url;
	
	protected Source(Rdb rdb)
	{
		super(rdb);
	}
	
	public Source(Db db, User user, App app, Repo repo, Package _package, SourceType type, String url)
	{
		super(db, user, app);
		this.repoId = repo.getId();
		this.packageId = _package.getId();
		this.type = type;
		this.url = url;
	}
	
	@Override
	public Source mask()
	{
		return this;
	}
	
	public SourceType getType()
	{
		return type;
	}
	
	@Override
	public String getRepoId()
	{
		return repoId;
	}
	
	@Override
	public String getPackageId()
	{
		return packageId;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public Source setType(SourceType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
	public Source setRepoId(String repoId)
	{
		this.repoId = setDirty("repoId", this.repoId, repoId);
		return this;
	}
	
	public Source setPackageId(String packageId)
	{
		this.packageId = setDirty("packageId", this.packageId, packageId);
		return this;
	}
	
	public Source setUrl(String url)
	{
		this.url = setDirty("url", this.url, url);
		return this;
	}
	
}
