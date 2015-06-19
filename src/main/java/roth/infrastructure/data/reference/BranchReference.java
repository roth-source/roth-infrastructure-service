package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Branch;
import roth.infrastructure.data.table.BranchTable;

public interface BranchReference extends Reference
{
	
	String getBranchId();
	
	default Branch getBranch()
	{
		return BranchTable.get(getRdb(), getUser()).findById(getBranchId());
	}
	
}
