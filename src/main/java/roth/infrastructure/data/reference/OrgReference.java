package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.table.OrgTable;

public interface OrgReference extends Reference
{
	
	String getOrgId();
	
	default Org getOrg()
	{
		return OrgTable.get(getRdb(), getUser()).findById(getOrgId());
	}
	
}
