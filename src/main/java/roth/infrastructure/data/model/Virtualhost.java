package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdModel;
import roth.infrastructure.data.Model;
import roth.infrastructure.data.reference.WebserverReference;
import roth.infrastructure.data.reference.RecordReference;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "virtualhost")
@SuppressWarnings("serial")
public class Virtualhost extends IdModel implements RecordReference, WebserverReference
{
	@Property(name = "record_id")
	protected String recordId;
	
	@Property(name = "webserver_id")
	protected String webserverId;
	
	protected Virtualhost(Rdb rdb)
	{
		super(rdb);
	}
	
	protected Virtualhost(Db db, User user, Record record, Webserver webserver)
	{
		super(db, user);
		this.recordId = record.getId();
		this.webserverId = webserver.getId();
	}
	
	@Override
	public Model mask()
	{
		return this;
	}
	
	@Override
	public String getRecordId()
	{
		return recordId;
	}
	
	@Override
	public String getWebserverId()
	{
		return webserverId;
	}
	
	public Virtualhost setRecordId(String recordId)
	{
		this.recordId = setDirty("recordId", this.recordId, recordId);
		return this;
	}
	
	public Virtualhost setWebserverId(String webserverId)
	{
		this.webserverId = setDirty("webserverId", this.webserverId, webserverId);
		return this;
	}
	
}
