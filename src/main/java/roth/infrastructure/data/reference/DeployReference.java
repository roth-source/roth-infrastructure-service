package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Deploy;
import roth.infrastructure.data.table.DeployTable;

public interface DeployReference extends Reference
{
	
	String getDeployId();
	
	default Deploy getDeploy()
	{
		return DeployTable.get(getRdb(), getUser()).findById(getDeployId());
	}
	
}
