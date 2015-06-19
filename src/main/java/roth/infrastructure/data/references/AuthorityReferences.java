package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Authority;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.AuthorityTable;
import roth.lib.map.rdb.sql.Order;

public interface AuthorityReferences extends Reference
{
	
	default LinkedList<Authority> getAuthorities()
	{
		return AuthorityTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Authority> getAuthorities(Order order)
	{
		return AuthorityTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
