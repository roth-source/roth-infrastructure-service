package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Target;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.TargetTable;
import roth.lib.map.rdb.sql.Order;

public interface TargetReferences extends Reference
{
	
	default LinkedList<Target> getTargets()
	{
		return TargetTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Target> getTargets(Order order)
	{
		return TargetTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
