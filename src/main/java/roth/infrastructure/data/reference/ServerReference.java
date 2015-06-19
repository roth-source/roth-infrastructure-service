package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Server;
import roth.infrastructure.data.table.ServerTable;

public interface ServerReference extends Reference
{
	
	String getServerId();
	
	default Server getServer()
	{
		return ServerTable.get(getRdb(), getUser()).findById(getServerId());
	}
	
}
