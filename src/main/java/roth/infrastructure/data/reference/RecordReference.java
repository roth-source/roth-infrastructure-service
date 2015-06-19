package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Record;
import roth.infrastructure.data.table.RecordTable;

public interface RecordReference extends Reference
{
	
	String getRecordId();
	
	default Record getRecord()
	{
		return RecordTable.get(getRdb(), getUser()).findById(getRecordId());
	}
	
}
