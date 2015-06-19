package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Nameserver;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.NameserverTable;
import roth.lib.map.rdb.sql.Order;

public interface NameserverReferences extends Reference
{
	
	default LinkedList<Nameserver> getNameservers()
	{
		return NameserverTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Nameserver> getNameservers(Order order)
	{
		return NameserverTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
