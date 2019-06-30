package commands.characters;

import core.Command;
import core.LocStrings;
import core.SubCommandFramework;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

public class CommandCharacterMain extends Command
{
	SubCommandFramework framework = new SubCommandFramework();
	public CommandCharacterMain(KittyRole level, KittyRating rating) 
	{ 
		super(level, rating); 
	}
	
	@Override
	public String getHelpText() { return LocStrings.stub("CharacterInfo"); }
	
	
	
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		framework.run(guild, channel, user, input.args).Call(res);
	}
}