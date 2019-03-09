package dataStructures;

import java.io.File;
import java.io.InputStream;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.*;
import utils.GlobalLog;
import utils.LogFilter;

// NOTE(wisp): This isn't constructed with the factory at this time, mostly
// because we need it to handle all the response queue behavior and everything
// internally. So long as commands aren't exposed to the GuildMessageReceivedEvent
// then we should be fine.
public class Response 
{
	private GuildMessageReceivedEvent event;
	private JDA kitty;
	private final int discordMessageMax = 2000;
	private final int kittyMessageMax = 1950;
	
	public Response(GuildMessageReceivedEvent event, JDA kitty)
	{
		this.event = event; 
		this.kitty = kitty; 
	}
	
	public void Call(String toRespondWith)
	{
		GlobalLog.Log(LogFilter.Response, "Sending response: " + toRespondWith);
		if(toRespondWith.length() > discordMessageMax)
		{
			event.getChannel().sendMessage(toRespondWith.substring(0, kittyMessageMax) + "\n\nI think that's enough!").queue();
		}
		else
		{
			event.getChannel().sendMessage(toRespondWith).queue();
		}
	}
	
	public void CallToChannel(String toRespondWith, String channelID)
	{
		TextChannel channel;
		channel = kitty.getTextChannelById(Long.parseLong(channelID));
		if(toRespondWith.length() > discordMessageMax)
		{
			channel.sendMessage(toRespondWith.substring(0, kittyMessageMax) + "\n\nI think that's enough!").queue();
		}
		else
		{
			channel.sendMessage(toRespondWith).queue();
		}
	}
	
	public void CallImmediate(String toRespondWith)
	{
		GlobalLog.Log(LogFilter.Response, "Sending immediate response: " + toRespondWith);
		if(toRespondWith.length() > discordMessageMax)
		{
			event.getChannel().sendMessage(toRespondWith.substring(0, kittyMessageMax) + "\n\nI think that's enough!");
		}
		else
		{
			event.getChannel().sendMessage(toRespondWith);
		}
	}
	
	// This is for responding with a file rather than a String
	public void CallFile(File toRespondWith, String extension)
	{
		GlobalLog.Log(LogFilter.Response, "Sending file response");
		event.getChannel().sendFile(toRespondWith, "return." + extension).queue();
	}

	public void CallInput(InputStream in, String extension) 
	{
		GlobalLog.Log(LogFilter.Response, "Sending input stream response");
		event.getChannel().sendFile(in, "return." + extension).queue();
	}
}
