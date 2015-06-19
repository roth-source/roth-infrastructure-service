package roth.infrastructure.service.model;


@SuppressWarnings("serial")
public class CheckboxColumn extends EditableColumn
{
	
	public CheckboxColumn(String label, String width, String field, String name, String service, String method)
	{
		super(label, width, new Renderer("boolean", field), name, service, method, "checkbox");
	}
	
}
