package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.App;
import roth.infrastructure.data.table.AppTable;
import roth.lib.map.rdb.sql.Order;

public interface AppReferences extends Reference
{
	
	default LinkedList<App> getApps()
	{
		return AppTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<App> getApps(Order order)
	{
		return AppTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
