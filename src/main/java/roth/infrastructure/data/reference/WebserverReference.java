package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Webserver;
import roth.infrastructure.data.table.WebserverTable;

public interface WebserverReference extends Reference
{
	
	String getWebserverId();
	
	default Webserver getWebserver()
	{
		return WebserverTable.get(getRdb(), getUser()).findById(getWebserverId());
	}
	
}
