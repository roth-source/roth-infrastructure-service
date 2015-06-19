package roth.infrastructure.data.references;

import java.util.LinkedList;

import roth.infrastructure.data.Reference;
import roth.infrastructure.data.model.AppSetting;
import roth.infrastructure.data.table.AppSettingTable;
import roth.lib.map.rdb.sql.Order;

public interface AppSettingReferences extends Reference
{
	
	default LinkedList<AppSetting> getAppSettings()
	{
		return AppSettingTable.get(getRdb(), getUser()).findAllBy(reference());
	}
	
	default LinkedList<AppSetting> getAppSettings(Order order)
	{
		return AppSettingTable.get(getRdb(), getUser()).findAllBy(reference(), order);
	}
	
}
