package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Webserver;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.WebserverTable;
import roth.lib.map.rdb.sql.Order;

public interface WebserverReferences extends Reference
{
	
	default LinkedList<Webserver> getWebservers()
	{
		return WebserverTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Webserver> getWebservers(Order order)
	{
		return WebserverTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
