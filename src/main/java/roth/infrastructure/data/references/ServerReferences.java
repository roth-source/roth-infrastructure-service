package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Server;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.ServerTable;
import roth.lib.map.rdb.sql.Order;

public interface ServerReferences extends Reference
{
	
	default LinkedList<Server> getServers()
	{
		return ServerTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Server> getServers(Order order)
	{
		return ServerTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
