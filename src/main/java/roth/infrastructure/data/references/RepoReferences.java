package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Repo;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.RepoTable;
import roth.lib.map.rdb.sql.Order;

public interface RepoReferences extends Reference
{
	
	default LinkedList<Repo> getRepos()
	{
		return RepoTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Repo> getRepos(Order order)
	{
		return RepoTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
