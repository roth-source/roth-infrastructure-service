package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Authority;
import roth.infrastructure.data.table.AuthorityTable;

public interface AuthorityReference extends Reference
{
	
	String getNameServerId();
	
	default Authority getNameServer()
	{
		return AuthorityTable.get(getRdb(), getUser()).findById(getNameServerId());
	}
	
}
