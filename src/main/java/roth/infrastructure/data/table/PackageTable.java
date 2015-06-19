package roth.infrastructure.data.table;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Package;
import roth.infrastructure.data.model.User;

public class PackageTable extends Table<Package, String>
{
	
	protected PackageTable(Db db, User user)
	{
		super(db, user, Package.class);
	}
	
	public static PackageTable get(Db db, User user)
	{
		return new PackageTable(db, user);
	}
	
}
