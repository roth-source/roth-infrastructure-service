package roth.infrastructure.service;

import java.util.LinkedList;

import roth.infrastructure.service.model.Form;
import roth.infrastructure.service.model.Group;
import roth.infrastructure.service.model.Table;
import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class InitResponse<T> extends ServiceResponse
{
	@Property(name = "tables")
	protected LinkedList<Table<T>> tables = new LinkedList<Table<T>>();
	
	@Property(name = "forms")
	protected LinkedList<Form> forms = new LinkedList<Form>();
	
	public InitResponse()
	{
		
	}
	
	public LinkedList<Table<T>> getTables()
	{
		return tables;
	}
	
	public LinkedList<Form> getForms()
	{
		return forms;
	}
	
	public InitResponse<T> setTables(LinkedList<Table<T>> tables)
	{
		this.tables = tables;
		return this;
	}
	
	public InitResponse<T> addTable(Table<T> table)
	{
		this.tables.add(table);
		return this;
	}
	
	public InitResponse<T> setForms(LinkedList<Form> forms)
	{
		this.forms = forms;
		return this;
	}
	
	public InitResponse<T> addForm(Form form)
	{
		this.forms.add(form);
		return this;
	}
	
	public InitResponse<T> addGroup(Group group)
	{
		this.forms.add(new Form().setTitle(group.getName()).addGroup(group));
		return this;
	}
	
}
