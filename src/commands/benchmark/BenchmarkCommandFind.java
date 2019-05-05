package commands.benchmark;

import java.util.List;

import core.benchmark.*;

public class BenchmarkCommandFind extends BenchmarkCommand 
{
	final char lineDelimiter = '\n';
	
	public String OnRun(BenchmarkManager manager, BenchmarkInput input)
	{
		String output = "";
		String searchString = input.value.trim();
		
		List<BenchmarkEntry> entries = manager.FindModel(searchString);
		
		if(entries.size() > 0)
		{
			output += "```" + lineDelimiter;
			int max = entries.size();
			final int listMax = 30;
			
			boolean fencepost = true;
			for(int i = 0; i < entries.size() && i < listMax; ++i)
			{
				BenchmarkEntry entry = entries.get(i);
				if(!fencepost)
					output += lineDelimiter;
				
				fencepost = false;
				output += entry.type + ": "+ entry.model;
			}
			
			if(max > listMax)
				output += "\n\nPlus " + (max - listMax) + " more items...";
			
			output += "```";
		}
		else
		{
			output = "Couldn't find any models matching the search `" + searchString + "`!";
		}
		
		return output;
	}
}
