package commands;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

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
import network.NetworkTheColorAPI;
import network.NetworkTheColorAPI.ColorData;
import utils.ImageUtils;

public class CommandColor extends Command
{
	private NetworkTheColorAPI theColorAPI = new NetworkTheColorAPI();
	
	public CommandColor(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return LocStrings.Stub("ColorInfo"); }

	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		// First, parse out the color.
		ColorData colorData = theColorAPI.LookupHex(input.args.trim());
		
		// Config
		int sideLength = 50;
		int r = colorData.rgb.r;
		int g = colorData.rgb.g;
		int b = colorData.rgb.b;
		int a = 255; // Alpha not supported at this time
		Color parsed = new Color(r, g, b, a);
		
		// Construct the image example file that we intend to display locally
		BufferedImage img = new BufferedImage(sideLength, sideLength, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D graphics = img.createGraphics();
		graphics.setColor(parsed);
		graphics.fillRect(0, 0, sideLength, sideLength);
		graphics.dispose();
		
		String tempFileName = ImageUtils.writeTempImageData(img, ".png");
		File file = new File(tempFileName);

		// Build the response
		KittyEmbed response = new KittyEmbed();
		response.title = colorData.name.value + (colorData.name.exact_match_name ? "" : " (ish)");
		response.color = parsed;
		response.descriptionText = "```";
		response.descriptionText += "\nrgb:  " + String.format("%1$03d", r) + "  " + String.format("%1$03d", g) + "  " + String.format("%1$03d", b);
		response.descriptionText += "\nhsl:  " + String.format("%1$03d", colorData.hsl.h) + "  " + String.format("%1$02d", colorData.hsl.s) + "%  " + String.format("%1$02d", colorData.hsl.l) + "%";
		response.descriptionText += "\nhsv:  " + String.format("%1$03d", colorData.hsv.h) + "  " + String.format("%1$02d", colorData.hsv.s) + "%  " + String.format("%1$02d", colorData.hsv.v) + "%";
		response.descriptionText += "\nhex:  #" + colorData.hex.clean;
		response.descriptionText += "```";
		response.thumbnailURL = "attachment://" + tempFileName;
		response.footerText = "All percentages and values are rounded to the nearest whole number";
		
		// Send then delete the temp local files
		res.CallEmbed(response);
		ImageUtils.BlockingFileDelete(file);
	};
	
}
