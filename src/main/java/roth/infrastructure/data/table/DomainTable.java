package roth.infrastructure.data.table;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Domain;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.User;
import roth.lib.map.rdb.sql.Select;

public class DomainTable extends Table<Domain, String>
{
	
	protected DomainTable(Db db, User user)
	{
		super(db, user, Domain.class);
	}
	
	public static DomainTable get(Db db, User user)
	{
		return new DomainTable(db, user);
	}
	
	public LinkedList<Domain> findAllByOrg(Org org)
	{
		Select select = select();
		select.joinLeft("registrar", "id", "domain", "registrar_id");
		select.whereOpenParen();
		select.whereEquals("domain", "org_id", org.getId());
		select.orWhereEquals("registrar",  "org_id", org.getId());
		select.whereCloseParen();
		return findAllBy(select);
	}
	
}
