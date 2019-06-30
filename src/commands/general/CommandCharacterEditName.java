package commands.general;

import core.CharacterManager;
import core.Command;
import core.LocStrings;
import dataStructures.*;

public class CommandCharacterEditName extends Command
{
	public CommandCharacterEditName(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.stub("CharacterEditNameInfo"); }
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		try
		{
			Long.parseLong(input.args.split(" ")[0]);
		}
		catch(Exception e)
		{
			res.send(LocStrings.stub("CharacterEditNameNotValid"));
			return;
		}
		KittyCharacter character = CharacterManager.instance.searchCharacter(input.args.split(" ")[0]).get(0);
		if(character.getOwner().equals(user))
		{
			CharacterManager.instance.editName(character, input.args.substring(input.args.indexOf(' ')));
			res.send(LocStrings.stub("CharacterEditNameSuccess"));
		}
		else
		{
			res.send(LocStrings.stub("CharacterEditNameNotAuth"));
		}
	}
}