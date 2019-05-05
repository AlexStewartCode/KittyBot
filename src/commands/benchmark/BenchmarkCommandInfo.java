package commands.benchmark;

import java.util.List;

import core.benchmark.BenchmarkCommand;
import core.benchmark.BenchmarkEntry;
import core.benchmark.BenchmarkInput;
import core.benchmark.BenchmarkManager;

public class BenchmarkCommandInfo extends BenchmarkCommand 
{
	static final String lineDelimiter = "\n";
	
	public static String FormatInfo(BenchmarkEntry entry)
	{
		String output = "";
		
		output += "`" + entry.brand + " " + entry.model + "`" + lineDelimiter;
		output += "<" + entry.url + ">";
		
		return output;
	}
		
	@Override
	public String OnRun(BenchmarkManager manager, BenchmarkInput input) 
	{
		String searchString = input.value.trim();
		
		String output = "";
		output += lineDelimiter;
		
		List<BenchmarkEntry> entries = manager.FindModel(searchString);
		
		if(entries.size() < 1)
			output += "Couldn't find any models containing `" + searchString + "`!";
		
		output += FormatInfo(entries.get(0));
		output += lineDelimiter;
		
		if(entries.size() > 1)
			output += "_chose " + entries.get(0).model + " from " + entries.size() + " potential components. To see others, try the command `benchmark find " + searchString +"`_)";
		
		return output;
	}
	
}
