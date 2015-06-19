package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Install;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.InstallTable;
import roth.lib.map.rdb.sql.Order;

public interface InstallReferences extends Reference
{
	
	default LinkedList<Install> getInstalls()
	{
		return InstallTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Install> getInstalls(Order order)
	{
		return InstallTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
