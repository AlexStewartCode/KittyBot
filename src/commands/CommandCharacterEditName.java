package commands;

import core.CharacterManager;
import core.Command;
import core.LocStrings;
import dataStructures.*;

public class CommandCharacterEditName extends Command
{
	public CommandCharacterEditName(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return LocStrings.Stub("CharacterEditNameInfo"); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		try
		{
			Long.parseLong(input.args.split(" ")[0]);
		}
		catch(Exception e)
		{
			res.Call(LocStrings.Stub("CharacterEditNameNotValid"));
			return;
		}
		KittyCharacter character = CharacterManager.instance.searchCharacter(input.args.split(" ")[0]).get(0);
		if(character.getOwner().equals(user))
		{
			CharacterManager.instance.editName(character, input.args.substring(input.args.indexOf(' ')));
			res.Call(LocStrings.Stub("CharacterEditNameSuccess"));
		}
		else
		{
			res.Call(LocStrings.Stub("CharacterEditNameNotAuth"));
		}
	}
}