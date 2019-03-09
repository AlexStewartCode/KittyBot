package commands;

import core.*;
import dataStructures.KittyChannel;
import dataStructures.KittyGuild;
import dataStructures.KittyRating;
import dataStructures.KittyRole;
import dataStructures.KittyUser;
import dataStructures.Response;
import dataStructures.UserInput;

public class CommandRoll extends Command
{
	public CommandRoll(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String HelpText() { return "Based on input of xdy where x is number of dice and y is faces kitty will roll that amount of dice, display the individual rolls and total"; } 
	
	@Override
	public void OnRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		int dice;
		int sides;
		String dicenum [] = input.args.split("d");
		
		try 
		{
			dice = Integer.parseInt(dicenum[0]);
			sides = Integer.parseInt(dicenum[1]);
		}
		catch (NumberFormatException e)
		{
			res.Call("Rude.");
			return;
		}
		
		res.Call(rollDice(dice,sides));
	}
	
	private String rollDice(int dice, int sides)
	{
		int total = 0;
		int roll = 0;
		String nums = "";
		
		//checks for lower than 1 dice or dice size 
		if(dice > 100 || sides > 100)
		{
			return ("*drowns in dice*");
		}
		
		if(dice < 1 || sides < 1)
		{
			return("I can't do that!");
		}
		
		for(int i = 0; dice > i; i ++)
		{
			roll = (int)(Math.random() * sides) + 1;
			nums += roll + " ";
			total += roll;
		}
		
		//makes output look nicer
		String prettyNums = "";
		String[] splitNums = nums.split(" ");
		if(splitNums.length > 10)
		{
			final int len = 5;
			for(int i = 0; i < splitNums.length; i += len)
			{
				prettyNums += "\n";
				for(int j = 0; j < len; ++j)
				{
					if(j != 0)
						prettyNums += " ";
					
					prettyNums += splitNums[i + j];
				}
			}
		}
		else
		{
			prettyNums = nums;
		}
		
		return ("Rolls are\n" + prettyNums + "\n" + "Total is: " + total);
	}
}
