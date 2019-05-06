package dataStructures;

import java.util.ArrayList;
import core.DatabaseTrackedObject;
import utils.AdminControl;

// Context for a given guild for kittybot. Primarily designed to hold guild-specific settings.
public class KittyGuild extends DatabaseTrackedObject
{
	// Variables
	public final String uniqueID;
	public KittyRating contentRating;
	public KittyUser guildOwner;
	public boolean polling;
	public String poll; 
	public ArrayList<String> hasVoted = new ArrayList<String>();
	public ArrayList<String> emoji = new ArrayList<String>();
	public ArrayList <KittyPoll> choices = new ArrayList<KittyPoll>();
	public AdminControl control;
	
	// Database synced info
	public final KittyGuildRoleList roleList;
	private String commandIndicator;
	
	// Default content for a guild
	public KittyGuild(String uniqueID, AdminControl adminControl, ArrayList <String> emoji)
	{
		super(uniqueID);
		this.uniqueID = uniqueID;
		roleList = new KittyGuildRoleList(uniqueID);
		
		control = adminControl;
		this.contentRating = KittyRating.Safe; 
		this.polling = false;
		this.emoji = emoji;
		SetCommandIndicator("!");
	}
	
	// Explicit constructor
	public KittyGuild(String commandIndicator, KittyRating contentRating, KittyUser guildOwner, String uniqueID)
	{
		super(uniqueID);
		this.uniqueID = uniqueID;
		roleList = new KittyGuildRoleList(uniqueID);
		
		SetCommandIndicator(commandIndicator);
		this.contentRating = contentRating;
		this.polling = false;
		this.guildOwner = guildOwner;
	}
	
	public String startPoll(String poll)
	{
		polling = true; 
		this.poll = poll;
		return "Poll `" + poll + "` started! Don't forget to add choices with `!poll choice`~";
	}
	
	public String addChoiceToPoll(String choice)
	{
		choices.add(new KittyPoll(choice));
		return "Added `" + choice + "` to poll!";
	}
	
	public String endPoll()
	{
		choices.clear();
		hasVoted.clear();
		this.polling = false;
		poll = null;
		return "Poll ended!";
	}
	
	@Override
	public String Serialize() 
	{
		return commandIndicator;
	}

	@Override
	public void DeSerialzie(String string) 
	{
		if(string == null || string.length() == 0)
			SetCommandIndicator("!");
		else
			SetCommandIndicator(string);
	}
	
	public String GetCommandIndicator()
	{
		return commandIndicator;
	}
	
	public void SetCommandIndicator(String newIndicator)
	{
		if(newIndicator != commandIndicator)
		{
			commandIndicator = newIndicator;
			MarkDirty();
		}
	}
}
