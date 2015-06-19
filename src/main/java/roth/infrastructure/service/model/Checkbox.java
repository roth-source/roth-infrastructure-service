package roth.infrastructure.service.model;

import roth.lib.annotation.Property;

@SuppressWarnings("serial")
public class Checkbox extends Field
{
	@Property(name = "checked")
	protected boolean checked;
	
	public Checkbox(String name, String label, boolean checked)
	{
		super(name, label, "checkbox", false);
		this.checked = checked;
	}
	
	public Boolean isChecked()
	{
		return checked;
	}
	
	public Checkbox setChecked(boolean checked)
	{
		this.checked = checked;
		return this;
	}
	
}
