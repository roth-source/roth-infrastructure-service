package roth.infrastructure.service.model;

@SuppressWarnings("serial")
public class TextColumn extends EditableColumn
{
	
	public TextColumn(String label, String width, String field, String name, String service, String method)
	{
		super(label, width, field, name, service, method, "text");
	}
	
}
