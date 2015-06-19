package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Repo;
import roth.infrastructure.data.table.RepoTable;

public interface RepoReference extends Reference
{
	
	String getRepoId();
	
	default Repo getRepo()
	{
		return RepoTable.get(getRdb(), getUser()).findById(getRepoId());
	}
	
}
