package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.Member;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.MemberTable;
import roth.lib.map.rdb.sql.Order;

public interface MemberReferences extends Reference
{
	
	default LinkedList<Member> getMembers()
	{
		return MemberTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<Member> getMembers(Order order)
	{
		return MemberTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
