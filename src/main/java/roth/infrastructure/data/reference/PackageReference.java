package roth.infrastructure.data.reference;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.Package;
import roth.infrastructure.data.table.PackageTable;

public interface PackageReference extends Reference
{
	
	String getPackageId();
	
	default Package getPackage()
	{
		return PackageTable.get(getRdb(), getUser()).findById(getPackageId());
	}
	
}
