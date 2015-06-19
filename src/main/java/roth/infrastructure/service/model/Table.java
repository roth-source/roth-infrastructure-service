package roth.infrastructure.service.model;

import java.io.Serializable;
import java.util.LinkedList;

import roth.infrastructure.data.Model;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class Table<T> implements Serializable
{
	@Property(name = "title")
	protected String title;
	
	@Property(name = "columns")
	protected LinkedList<Column> columns = new LinkedList<Column>();
	
	@Property(name = "rows")
	protected LinkedList<T> rows = new LinkedList<T>();
	
	public Table(String title)
	{
		this.title = title;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public LinkedList<Column> getColumns()
	{
		return columns;
	}
	
	public LinkedList<T> getRows()
	{
		return rows;
	}
	
	public Table<T> setTitle(String title)
	{
		this.title = title;
		return this;
	}
	
	public Table<T> setColumns(LinkedList<Column> columns)
	{
		
		this.columns = columns;
		return this;
	}
	
	public Table<T> addColumn(Column column)
	{
		this.columns.add(column);
		return this;
	}
	
	public Table<T> setRows(LinkedList<T> rows)
	{
		for(T row : rows)
		{
			addRow(row);
		}
		return this;
	}
	
	public Table<T> addRow(T row)
	{
		if(row instanceof Model)
		{
			((Model) row).mask();
		}
		this.rows.add(row);
		return this;
	}
	
}
