package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.App;
import roth.infrastructure.data.table.AppTable;

public interface AppReference extends Reference
{
	
	String getAppId();
	
	default App getApp()
	{
		return AppTable.get(getRdb(), getUser()).findById(getAppId());
	}
	
}
