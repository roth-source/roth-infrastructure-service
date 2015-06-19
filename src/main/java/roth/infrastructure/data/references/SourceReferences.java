package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Source;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.SourceTable;
import roth.lib.map.rdb.sql.Order;

public interface SourceReferences extends Reference
{
	
	default LinkedList<Source> getSources()
	{
		return SourceTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Source> getSources(Order order)
	{
		return SourceTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
