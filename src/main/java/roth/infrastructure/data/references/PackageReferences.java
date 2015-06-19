package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Package;
import roth.infrastructure.data.table.PackageTable;
import roth.lib.map.rdb.sql.Order;

public interface PackageReferences extends Reference
{
	
	default LinkedList<Package> getPackages()
	{
		return PackageTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Package> getPackages(Order order)
	{
		return PackageTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
