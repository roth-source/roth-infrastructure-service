package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Nameserver;
import roth.infrastructure.data.table.NameserverTable;

public interface NameserverReference extends Reference
{
	
	String getNameserverId();
	
	default Nameserver getNameserver()
	{
		return NameserverTable.get(getRdb(), getUser()).findById(getNameserverId());
	}
	
}
