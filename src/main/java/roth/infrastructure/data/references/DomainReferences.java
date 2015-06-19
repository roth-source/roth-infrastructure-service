package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Domain;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.DomainTable;
import roth.lib.map.rdb.sql.Order;

public interface DomainReferences extends Reference
{
	
	default LinkedList<Domain> getDomains()
	{
		return DomainTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Domain> getDomains(Order order)
	{
		return DomainTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
