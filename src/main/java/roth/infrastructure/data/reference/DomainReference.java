package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Domain;
import roth.infrastructure.data.table.DomainTable;

public interface DomainReference extends Reference
{
	
	String getDomainId();
	
	default Domain getDomain()
	{
		return DomainTable.get(getRdb(), getUser()).findById(getDomainId());
	}
	
}
