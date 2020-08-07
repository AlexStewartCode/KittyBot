package commands.guildrole;

import java.util.ArrayList;

import core.Command;
import core.ObjectBuilderFactory;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;
import net.dv8tion.jda.core.JDA;

public class CommandWipeRoles extends Command
{

	public CommandWipeRoles(KittyRole roleLevel, KittyRating contentRating) {super(roleLevel, contentRating);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res) 
	{
		JDA kitty = res.getKitty();
		ArrayList guilds = (ArrayList) kitty.getGuilds();
		KittyGuild guildx;
		
		for(int i = 0; i < guilds.size(); i ++)
		{
			guildx = ObjectBuilderFactory.extractGuild
		}
	}
}