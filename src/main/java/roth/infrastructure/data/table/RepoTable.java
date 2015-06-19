package roth.infrastructure.data.table;

import java.util.LinkedList;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.Table;
import roth.infrastructure.data.model.Org;
import roth.infrastructure.data.model.Repo;
import roth.infrastructure.data.model.User;
import roth.infrastructure.data.type.RepoType;
import roth.lib.map.rdb.sql.Select;

public class RepoTable extends Table<Repo, String>
{
	
	protected RepoTable(Db db, User user)
	{
		super(db, user, Repo.class);
	}
	
	public static RepoTable get(Db db, User user)
	{
		return new RepoTable(db, user);
	}
	
	public LinkedList<Repo> findAllByOrgAndType(Org org, RepoType type)
	{
		Select select = select();
		select.whereEquals("org_id", org.getId());
		select.whereEquals("type", type.name());
		return findAllBy(select);
	}
	
}
