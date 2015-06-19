package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Virtualhost;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.VirtualhostTable;
import roth.lib.map.rdb.sql.Order;

public interface VirtualhostReferences extends Reference
{
	
	default LinkedList<Virtualhost> getVirtualhosts()
	{
		return VirtualhostTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Virtualhost> getVirtualhosts(Order order)
	{
		return VirtualhostTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
