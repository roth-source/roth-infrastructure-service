package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Env;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.EnvTable;
import roth.lib.map.rdb.sql.Order;

public interface EnvReferences extends Reference
{
	
	default LinkedList<Env> getEnvs()
	{
		return EnvTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Env> getEnvs(Order order)
	{
		return EnvTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
