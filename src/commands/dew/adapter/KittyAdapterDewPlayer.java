package commands.dew.adapter;

import commands.dew.core.api.IDewPlayer;
import commands.dew.core.data.DewRealm;
import dataStructures.KittyUser;

public class KittyAdapterDewPlayer implements IDewPlayer 
{
	private KittyUser user;
	private String realmID;
	private int x;
	private int y;
	
	public KittyAdapterDewPlayer(DewRealm realm, KittyUser user, int x, int y)
	{
		this.user = user;
		this.realmID = realm.id;
		this.x = x;
		this.y = y;
	}
	
	public String getVisual()
	{
		return user.name.length() >= 1 ? user.name.substring(0, 1) : "@";
	}
	
	public boolean isUser(KittyUser user)
	{
		return user.uniqueID == user.uniqueID;
	}

	public void setRealmPos(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	  ////////////////////////////
	 // Interface requirements //
	////////////////////////////
	
	@Override
	public String getName()
	{
		return user.name;
	}

	@Override
	public String getUniqueID()
	{
		return user.uniqueID;
	}

	@Override
	public String getCurrentRealmID()
	{
		return realmID;
	}

	@Override
	public int getRealmX()
	{
		return x;
	}

	@Override
	public int getRealmY()
	{
		return y;
	}
}
