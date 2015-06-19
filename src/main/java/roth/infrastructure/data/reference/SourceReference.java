package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Source;
import roth.infrastructure.data.table.SourceTable;

public interface SourceReference extends Reference
{
	
	String getSourceId();
	
	default Source getSource()
	{
		return SourceTable.get(getRdb(), getUser()).findById(getSourceId());
	}
	
}
