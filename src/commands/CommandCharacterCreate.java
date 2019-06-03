package commands;

import core.CharacterManager;
import core.Command;
import core.LocStrings;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

public class CommandCharacterCreate extends Command
{
public CommandCharacterCreate(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return LocStrings.Stub("CharacterCreateInfo"); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		String [] info = input.args.split(",");
		if(info.length < 3)
		{
			res.Call(LocStrings.Stub("CharacterCreateNullInfo"));
			return;
		}
		if(CharacterManager.instance.addCharacter(user, info[0], info[1], info[2]))
		{
			res.Call(LocStrings.Stub("CharacterCreateSuccess"));
			return;
		}
		res.Call(LocStrings.Stub("CharacterCreateDuplicate"));
		
	}
}