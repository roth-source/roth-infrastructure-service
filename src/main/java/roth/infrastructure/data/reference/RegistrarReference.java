package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Registrar;
import roth.infrastructure.data.table.RegistrarTable;

public interface RegistrarReference extends Reference
{
	
	String getRegistrarId();
	
	default Registrar getRegistrar()
	{
		return RegistrarTable.get(getRdb(), getUser()).findById(getRegistrarId());
	}
	
}
