package roth.infrastructure.data.table;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Domain;
import roth.infrastructure.data.model.Nameserver;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.Record;
import roth.infrastructure.data.model.User;
import roth.lib.map.rdb.sql.Select;

public class RecordTable extends Table<Record, String>
{
	
	protected RecordTable(Db db, User user)
	{
		super(db, user, Record.class);
	}
	
	public static RecordTable get(Db db, User user)
	{
		return new RecordTable(db, user);
	}
	
	public LinkedList<Record> findAllUnmanaged(Org org)
	{
		Select select = select();
		select.whereEquals("org_id", org.getId());
		select.whereIsNull("server_id");
		return findAllBy(select);
	}
	
	public LinkedList<Record> findAllByNameserverAndDomain(Nameserver nameserver, Domain domain)
	{
		Select select = select();
		select.whereEquals("nameserver_id", nameserver.getId());
		select.whereEquals("domain_id", domain.getId());
		return findAllBy(select);
	}
	
}
