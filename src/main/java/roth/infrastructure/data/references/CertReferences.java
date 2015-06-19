package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Cert;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.CertTable;
import roth.lib.map.rdb.sql.Order;

public interface CertReferences extends Reference
{
	
	default LinkedList<Cert> getCerts()
	{
		return CertTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Cert> getCerts(Order order)
	{
		return CertTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
