package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Dist;
import roth.infrastructure.data.table.DistTable;

public interface DistReference extends Reference
{
	
	String getDistId();
	
	default Dist getDist()
	{
		return DistTable.get(getRdb(), getUser()).findById(getDistId());
	}
	
}
