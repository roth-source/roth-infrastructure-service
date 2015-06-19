package roth.infrastructure.data.model;

import roth.infrastructure.data.Db;
import roth.infrastructure.data.IdModel;
import roth.infrastructure.data.references.MemberReferences;
import roth.infrastructure.data.type.UserType;
import roth.infrastructure.util.DataUtil;
import roth.lib.annotation.Entity;
import roth.lib.annotation.Property;
import roth.lib.map.rdb.Rdb;

@Entity(name = "user")
@SuppressWarnings("serial")
public class User extends IdModel implements MemberReferences
{
	@Property(name = "type")
	protected UserType type;
	
	@Property(name = "email")
	protected String email;
	
	@Property(name = "password", json = false)
	protected String password;
	
	@Property(name = "publicKey")
	protected String publicKey;
	
	protected User(Rdb rdb)
	{
		super(rdb);
	}
	
	public User(Db db, User user, UserType type, String email, String password)
	{
		super(db, user);
		this.type = type;
		this.email = email;
		this.password = password;
	}
	
	@Override
	public User mask()
	{
		this.publicKey = DataUtil.maskEnd(publicKey, 30);
		return this;
	}
	
	public UserType getType()
	{
		return type;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getPublicKey()
	{
		return publicKey;
	}
	
	public User setType(UserType type)
	{
		this.type = setDirty("type", this.type, type);
		return this;
	}
	
	public User setEmail(String email)
	{
		this.email = setDirty("email", this.email, email);
		return this;
	}
	
	public User setPassword(String password)
	{
		this.password = setDirty("password", this.password, password);
		return this;
	}
	
	public User setPublicKey(String publicKey)
	{
		this.publicKey = publicKey;
		return this;
	}
	
}
