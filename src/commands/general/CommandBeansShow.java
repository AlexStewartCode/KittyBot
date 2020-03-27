package commands.general;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.Command;
import core.Constants;
import core.LocStrings;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

public class CommandBeansShow extends Command
{
	public CommandBeansShow(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.stub("BeansShowInfo"); };
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		if(input.mentions == null)
		{
			res.send("" + user.getBeans());
			try {
				res.sendFile(combineImages(user.getBeans()), "png");
			} catch (IOException e) {
				e.printStackTrace();
			}
//			res.send(String.format(LocStrings.stub("BeansShowDisplay"), user.getBeans()));
		}
		else
		{
			String mentionedBeans = ""; 
			for(KittyUser mentioned:input.mentions)
			{
				mentionedBeans += mentioned.name + " has " + mentioned.getBeans() + " ";
			}
			res.send(String.format(LocStrings.stub("BeansShowMentioned"), mentionedBeans));
		}
	}
	
	private File combineImages(long beans) throws IOException
	{
		// Acquire images
		BufferedImage imageBase = ImageIO.read(new File(Constants.AssetDirectory + "beans/BeansBG.png"));
		BufferedImage thouBean = ImageIO.read(new File(Constants.AssetDirectory + "beans/thouBean.png"));
		BufferedImage hunBean = ImageIO.read(new File(Constants.AssetDirectory + "beans/hunBean.png"));
		BufferedImage tenBean = ImageIO.read(new File(Constants.AssetDirectory + "beans/tenBean.png"));
		BufferedImage oneBean = ImageIO.read(new File(Constants.AssetDirectory + "beans/oneBean.png"));
		
		final int baseWidth = imageBase.getWidth();
		final int baseHeight = imageBase.getHeight();
		
		long calcBean = beans;
		
		System.out.println("BEANS: " + beans);
		
		long thousands = beans / 1000;
		calcBean = beans % 1000;
		long hundreds = (calcBean / 100);
		calcBean = calcBean % 100;
		long tens = (calcBean / 10);
		calcBean = calcBean % 10;
		long ones = calcBean;
		
		BufferedImage beanImage = new BufferedImage(baseWidth, baseHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics beanGraphics = beanImage.getGraphics();
		
		for(int i = 0; i < ones; i++)
		{
			int x = (int)(Math.random() * (baseWidth - oneBean.getWidth())) + 1;
			int y = (int)(Math.random() * (baseHeight - oneBean.getHeight())) + 1;
			beanGraphics.drawImage(oneBean, x, y, null);
		}
		
		for(int i = 0; i < tens; i++)
		{
			int x = (int)(Math.random() * (baseWidth - tenBean.getWidth())) + 1;
			int y = (int)(Math.random() * (baseHeight - tenBean.getHeight())) + 1;
			beanGraphics.drawImage(tenBean, x, y, null);
		}
		
		for(int i = 0; i < hundreds; i++)
		{
			int x = (int)(Math.random() * (baseWidth - hunBean.getWidth())) + 1;
			int y = (int)(Math.random() * (baseHeight - hunBean.getHeight())) + 1;
			beanGraphics.drawImage(hunBean, x, y, null);
		}
		
		for(int i = 0; i < thousands; i++)
		{
			int x = (int)(Math.random() * (baseWidth - thouBean.getWidth())) + 1;
			int y = (int)(Math.random() * (baseHeight - thouBean.getHeight())) + 1;
			beanGraphics.drawImage(thouBean, x, y, null);
		}
		
		
		try {
			@SuppressWarnings("unused")
			Font font = null;
			font = Font.createFont(Font.TRUETYPE_FONT, new File(Constants.AssetDirectory +"fonts/B612Mono-Regular.ttf"));
		} catch (Exception e)
		{
		}
		Font beanFont = new Font("font", Font.BOLD, 45);
		beanGraphics.setFont(beanFont);
		beanGraphics.drawString("YOU GOT " + beans + " BEANS", 0, baseHeight / 2);
		
		ImageIO.write(beanImage, "PNG", new File("beanImage.png"));
		return new File("beanImage.png");
	}
}
