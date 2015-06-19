package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Release;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.ReleaseTable;
import roth.lib.map.rdb.sql.Order;

public interface ReleaseReferences extends Reference
{
	
	default LinkedList<Release> getReleases()
	{
		return ReleaseTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Release> getReleases(Order order)
	{
		return ReleaseTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
