package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Host;
import roth.infrastructure.data.table.HostTable;

public interface HostReference extends Reference
{
	
	String getHostId();
	
	default Host getHost()
	{
		return HostTable.get(getRdb(), getUser()).findById(getHostId());
	}
	
}
