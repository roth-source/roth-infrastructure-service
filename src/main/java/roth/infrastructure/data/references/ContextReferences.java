package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Context;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.ContextTable;
import roth.lib.map.rdb.sql.Order;

public interface ContextReferences extends Reference
{
	
	default LinkedList<Context> getContexts()
	{
		return ContextTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Context> getContexts(Order order)
	{
		return ContextTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
