package commands;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import core.Command;
import core.LocStrings;
import dataStructures.KittyChannel;
import dataStructures.KittyEmbed;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

public class CommandColor extends Command
{
	public CommandColor(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return LocStrings.Stub("ColorInfo"); }

	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		int sideLength = 50;
		
		float r = 0;
		float g = 0;
		float b = 0;
		float a = 1;
		Color parsed = new Color(r, g, b, a);
		
		BufferedImage img = new BufferedImage(sideLength, sideLength, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D graphics = img.createGraphics();
		graphics.setColor(parsed);
		graphics.fillRect(0, 0, sideLength, sideLength);
		graphics.dispose();
		
		KittyEmbed response = new KittyEmbed();
		response.color = parsed;
		response.thumbnailURL = "attachment://test.png";
		
		res.CallEmbed(response);
		//ImageIO.write(bufferedImage, "png", file);
	};
	
}
