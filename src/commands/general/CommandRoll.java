package commands.general;

import java.awt.Color;
import java.util.Random;
import java.util.Stack;

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

public class CommandRoll extends Command
{
	public CommandRoll(KittyRole level, KittyRating rating) { super(level, rating); }
	
	@Override
	public String getHelpText() { return LocStrings.stub("RollInfo"); } 
	
	@Override
	public void onRun(KittyGuild guild, KittyChannel channel, KittyUser user, UserInput input, Response res)
	{
		try 
		{
			KittyEmbed embed = new KittyEmbed();
			embed.title = user.name +"'s roll!";
			embed.descriptionText = rollDice(input.args);
			Random numGen = new Random(user.name.hashCode());
			float hue = numGen.nextFloat();
			float sat = .7f;
			float bright = .7f;
			embed.color = Color.getHSBColor(hue, sat, bright);
			res.send(embed);
		}
		catch(Exception e)
		{
			res.send(LocStrings.stub("RollError"));
		}
	}
	
	private String rollDice(String thing)
	{
		Stack<Integer> values = new Stack<Integer>();
		Stack<Character> operators = new Stack<Character>();
		String steps = ""; 
		for(int i = 0; i < thing.length(); i ++)
		{
			char current = thing.charAt(i);
			
			switch(current)
			{
				case '+':
				case '-':
				case '*':
				case '/':
					if(operators.isEmpty() || !hasPrecendence(operators.peek(), current) || operators.peek() == '(')
						operators.add(current);
					else
					{
						steps += "\n" + calculate(values, operators);
						operators.add(current); 
					}
						
					continue;
					
				case '(':
					operators.add(current);
					continue;
					
				case ')':
					if(operators.peek() == '(')
						operators.pop(); 
					else
					{
						i --;
						steps += "\n" + calculate(values, operators);
					}
					continue;
					
				case 'd':
					if(operators.isEmpty() || operators.peek() == '(' || !hasPrecendence(operators.peek(), current))
						operators.add(current);
					else
						steps += "\n" + calculate(values, operators);
					continue;
					
				case ' ':
					continue; 
					
				default:
					try
					{
					    // Accumulate valid characters
					    String accumulated = "";
					    for(int j = i; j < thing.length(); ++j)
					    {
					        char parse = thing.charAt(j);
					        if(Character.isDigit(parse))
					        {
					            accumulated += parse; // Keep tabs on our character
					            ++i;                  // Offset overall loop
					            continue;
					        }
					        
					        // Default leave
					        break;
					    }
					    
					    if(accumulated.length() <= 0)
					        throw new Exception("Error! Invalid character!");
					        
					    values.add(Integer.parseInt(accumulated));
					    i--;
					}
					catch (Exception e)
					{
					    System.out.print("Something went wrong");
					    i = thing.length();
					}
			}
		}
		
		while(operators.size() > 0)
		{
			steps += "\n" + calculate(values, operators);
		}
		
		return "Roll```" + thing + "```" + "Steps```" + steps + "```" + "Final value ```" + values.pop() +"```";
	}
	
	private String calculate(Stack<Integer> nums, Stack<Character> operators)
	{
		int second = nums.pop();
		int first = nums.pop();
		int value = 0;
		char op = operators.pop();
		
		switch(op)
		{
			case '+':
				value = first + second;
				break;
			case '-':
				value = first - second;
				break;
			case '*':
				value = first * second;
				break;
			case '/':
				value = first / second;
				break;
			case 'd': 
				value = roll(first, second);
		}
		nums.add(value);
		return (first + "" + op + "" + second + "=" + value);
	}

	private int roll(int first, int second) 
	{
		int total = 0;
		int roll = 0;
		int dice = first;
		int sides = second;
		
		if(dice < 1)
			return 0; 
		for(int i = 0; dice > i; i ++)
		{
			roll = (int)(Math.random() * sides) + 1;
			total += roll;
		}
		return total;
	}

	private boolean hasPrecendence(char op1, char op2)
	{
		 if ((op2 == '*' || op2 == '/') && (op1 == '+' || op1 == '-')) 
	         return false;
	     else
	    	 if ((op2 == 'd') && (op1 == '+' || op1 == '-' || op1 == '*' || op1 == '/')) 
	    		 return false;
	    	 else
	    		 return true; 
	}
}
