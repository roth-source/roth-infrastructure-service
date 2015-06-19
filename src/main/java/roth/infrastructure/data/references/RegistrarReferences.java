package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Registrar;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.RegistrarTable;
import roth.lib.map.rdb.sql.Order;

public interface RegistrarReferences extends Reference
{
	
	default LinkedList<Registrar> getRegistrars()
	{
		return RegistrarTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Registrar> getRegistrars(Order order)
	{
		return RegistrarTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
