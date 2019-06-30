package commands.general;

import java.util.ArrayList;

import core.CharacterManager;
import core.Command;
import core.LocStrings;
import dataStructures.*;

public class CommandCharacterSearch extends Command
{
	public CommandCharacterSearch(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.stub("CharacterSearchInfo"); }
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		ArrayList <KittyCharacter> characters = CharacterManager.instance.searchCharacter(input.args);
		if(characters.size() < 1)
		{
			res.send(LocStrings.stub("CharacterSearchNoCharacterFound"));
			return;
		}
		if(characters.size() > 1)
		{
			res.send(LocStrings.stub("CharacterSearchMultipleCharacterHeader"));
			for(KittyCharacter character:characters)
			{
				res.send(String.format(LocStrings.stub("CharacterSearchMultipleCharacter"), character.getName(), character.getOwner().name, character.getUID()));
			}
			return;
		}
		else
		{
			res.send(String.format(LocStrings.stub("CharacterSearchOneCharacter"), characters.get(0).getOwner().name, characters.get(0).getName(), characters.get(0).getBio(), characters.get(0).getRefImage(), characters.get(0).getUID()));
		}
	}
}