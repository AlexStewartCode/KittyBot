package commands.images;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import core.Command;
import core.LocStrings;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;
import utils.ImageOverlayBuilder;
import utils.ImageUtils;

public class CommandDefaultDance  extends Command
{
	// Required constructor
	public CommandDefaultDance(KittyRole level, KittyRating rating) { super(level, rating); }
	
	private static Long num = 0l;
	
	@Override
	public String getHelpText() { return LocStrings.stub("DefaultDanceInfo"); }
	
	// Called when the command is run!
	@Override 
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		String name = null;
		File dancerFile = null;
		File danceeFile = null;
		
		synchronized(num)
		{
			name = "defaultDance_" + num + ".gif";
			++num;
		}
		
		try 
		{
			KittyUser person = null;
			
			if(input.mentions == null)
				person = user; 
			else
				person = input.mentions[0];
				
			String yeeteeFilename = ImageUtils.downloadFromURL(person.avatarID, ".png");
			if(yeeteeFilename == null)
				return;
			
			danceeFile = new File(yeeteeFilename);
			ImageOverlayBuilder builder = new ImageOverlayBuilder("assets/dance/default/", "defaultDance ", 118, 18);
			builder.overlay(ImageIO.read(danceeFile), name);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		dancerFile = new File (name);
		res.sendFile(dancerFile, "gif");

		// Thread cleanup...
		ImageUtils.blockingFileDelete(dancerFile);
		ImageUtils.blockingFileDelete(danceeFile);
	}
}
