package dataStructures;

import core.DatabaseTrackedObject;
import utils.GlobalLog;
import utils.LogFilter;

// NOTE(wisp): A user is a specific instance of a discord user on a given
// discord server. This means that if a user is on two servers, they have
// two unique user objects associated with them.
public class KittyUser extends DatabaseTrackedObject
{
	public KittyGuild guild;
	public String name;
	public String uniqueID;
	public String discordID;
	public String avatarID; 	
	private KittyRole role;
	private long beans;
	
	// Explicit Constructor
	public KittyUser(String name, KittyGuild guild, KittyRole role, String uniqueID, String avatarID, String discordID)
	{
		super(uniqueID);
		this.name = name;
		this.role = role;
		this.guild = guild;
		this.uniqueID = uniqueID;
		this.beans = 0;
		this.avatarID = avatarID;
		this.discordID = discordID;
	}

	// Can be positive or negative
	public void ChangeBeans(int amount)
	{
		beans += amount;
		
		if(amount != 0)
			MarkDirty();
	}
	
	public void ChangeRole(KittyRole newRole)
	{
		if(newRole != role)
		{
			role = newRole;
			MarkDirty();
		}
	}
	
	public long GetBeans()
	{
		return beans;
	}
	
	public KittyRole GetRole()
	{
		return role;
	}
	
	@Override
	public String Serialize() 
	{
		return beans + "," + role.getValue();
	}

	@Override
	public void DeSerialzie(String string) 
	{
		try
		{
			String[] strings = string.split(",");
			beans = Integer.parseInt(strings[0]);
			if(strings.length > 1)
			{
				role = KittyRole.valueOf(Integer.parseInt(strings[1])).get();
			}
			else
			{
				GlobalLog.Log(LogFilter.Database, "Upgrading user " + name + " to include 'role' in DB");
				// Mark ourselves dirty to re-write the role information stored in the user.
				// Just uses defaults from earlier again.
				MarkDirty();
			}
		}
		catch (NumberFormatException e)
		{
			GlobalLog.Warn(LogFilter.Database, "Invalid user data for user " + name + "! "
					+ "Starting over at 0 beans with a general role!");
			// We don't need to specify the role at this point because it is set at this point.
			// We use what the user was created with whatever defaults were in the factory. 
			// Beans are maintained too, just in case there's some in cache.
			MarkDirty();
		}
	}
}
