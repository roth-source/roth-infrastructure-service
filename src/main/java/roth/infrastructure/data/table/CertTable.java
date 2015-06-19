package roth.infrastructure.data.table;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Cert;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.User;
import roth.lib.map.rdb.sql.Select;

public class CertTable extends Table<Cert, String>
{
	
	protected CertTable(Db db, User user)
	{
		super(db, user, Cert.class);
	}
	
	public static CertTable get(Db db, User user)
	{
		return new CertTable(db, user);
	}
	
	public LinkedList<Cert> findAllByOrg(Org org)
	{
		Select select = select();
		select.join("domain", "id", "cert", "domain_id");
		select.joinLeft("registrar", "id", "domain", "registrar_id");
		select.whereOpenParen();
		select.whereEquals("domain", "org_id", org.getId());
		select.orWhereEquals("registrar",  "org_id", org.getId());
		select.whereCloseParen();
		return findAllBy(select);
	}
	
}
