package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Env;
import roth.infrastructure.data.table.EnvTable;

public interface EnvReference extends Reference
{
	
	String getEnvId();
	
	default Env getEnv()
	{
		return EnvTable.get(getRdb(), getUser()).findById(getEnvId());
	}
	
}
