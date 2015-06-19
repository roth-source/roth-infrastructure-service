package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Deploy;
import roth.infrastructure.data.table.DeployTable;
import roth.lib.map.rdb.sql.Order;

public interface DeployReferences extends Reference
{
	
	default LinkedList<Deploy> getDeploys()
	{
		return DeployTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Deploy> getDeploys(Order order)
	{
		return DeployTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
