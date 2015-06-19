package roth.infrastructure.data.table;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.Registrar;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.type.RegistrarType;
import roth.lib.map.rdb.sql.Select;

public class RegistrarTable extends Table<Registrar, String>
{
	
	protected RegistrarTable(Db db, User user)
	{
		super(db, user, Registrar.class);
	}
	
	public static RegistrarTable get(Db db, User user)
	{
		return new RegistrarTable(db, user);
	}
	
	public LinkedList<Registrar> findAllByOrgAndType(Org org, RegistrarType registrarType)
	{
		Select select = select();
		select.whereEquals("org_id", org.getId());
		select.whereEquals("type", registrarType.name());
		return findAllBy(select);
	}
	
}
