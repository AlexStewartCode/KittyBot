package commands;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

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
import utils.GlobalLog;
import utils.ImageUtils;

public class CommandColor extends Command
{
	public CommandColor(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return LocStrings.Stub("ColorInfo"); }

	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		// Config
		int sideLength = 50;
		float r = 209.0f/255;
		float g = 74.0f/255;
		float b = 153.0f/255;
		float a = 1;
		Color parsed = new Color(r, g, b, a);
		
		GlobalLog.Log("1");
		
		// Construct the image example file that we intend to display locally
		BufferedImage img = new BufferedImage(sideLength, sideLength, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D graphics = img.createGraphics();
		graphics.setColor(parsed);
		graphics.fillRect(0, 0, sideLength, sideLength);
		graphics.dispose();
		
		GlobalLog.Log("2");
		
		String tempFileName = ImageUtils.writeTempImageData(img, ".png");
		File file = new File(tempFileName);
		
		GlobalLog.Log("3");
		
		KittyEmbed response = new KittyEmbed();
		response.title = "Color Name";
		response.color = parsed;
		response.descriptionText = "`rgba` test\n`hex` test\n`hsv` test";
		response.thumbnailURL = "attachment://" + tempFileName;
		
		GlobalLog.Log("4");
		
		res.CallEmbed(response);
		ImageUtils.BlockingFileDelete(file);
		
		GlobalLog.Log("5");
	};
	
}
