package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Record;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.RecordTable;
import roth.lib.map.rdb.sql.Order;

public interface RecordReferences extends Reference
{
	
	default LinkedList<Record> getRecords()
	{
		return RecordTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Record> getRecords(Order order)
	{
		return RecordTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
