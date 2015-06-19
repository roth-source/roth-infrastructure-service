package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Host;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.HostTable;
import roth.lib.map.rdb.sql.Order;

public interface HostReferences extends Reference
{
	
	default LinkedList<Host> getHosts()
	{
		return HostTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Host> getHosts(Order order)
	{
		return HostTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
