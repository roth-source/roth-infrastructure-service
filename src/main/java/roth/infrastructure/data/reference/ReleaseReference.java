package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Release;
import roth.infrastructure.data.table.ReleaseTable;

public interface ReleaseReference extends Reference
{
	
	String getReleaseId();
	
	default Release getRelease()
	{
		return ReleaseTable.get(getRdb(), getUser()).findById(getReleaseId());
	}
	
}
