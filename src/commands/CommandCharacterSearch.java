package commands;

import java.util.ArrayList;

import core.CharacterManager;
import core.Command;
import core.LocStrings;
import dataStructures.*;

public class CommandCharacterSearch extends Command
{
	public CommandCharacterSearch(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return LocStrings.Stub("CharacterSearchInfo"); }
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		ArrayList <KittyCharacter> characters = CharacterManager.instance.searchCharacter(input.args);
		if(characters.size() < 1)
		{
			res.Call(LocStrings.Stub("CharacterSearchNoCharacterFound"));
			return;
		}
		if(characters.size() > 1)
		{
			res.Call(LocStrings.Stub("CharacterSearchMultipleCharacterHeader"));
			for(KittyCharacter character:characters)
			{
				res.Call(String.format(LocStrings.Stub("CharacterSearchMultipleCharacter"), character.getName(), character.getOwner().name, character.getUID()));
			}
			return;
		}
		else
		{
			res.Call(String.format(LocStrings.Stub("CharacterSearchOneCharacter"), characters.get(0).getOwner().name, characters.get(0).getName(), characters.get(0).getBio(), characters.get(0).getRefImage(), characters.get(0).getUID()));
		}
	}
}