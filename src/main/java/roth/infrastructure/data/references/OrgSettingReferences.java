package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.model.OrgSetting;
import roth.infrastructure.data.Reference;
import roth.infrastructure.data.table.OrgSettingTable;
import roth.lib.map.rdb.sql.Order;

public interface OrgSettingReferences extends Reference
{
	
	default LinkedList<OrgSetting> getOrgSettings()
	{
		return OrgSettingTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<OrgSetting> getOrgSettings(Order order)
	{
		return OrgSettingTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
