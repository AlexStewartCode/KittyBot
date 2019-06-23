package core;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

import dataStructures.Pair;

public class BaseKeyValueFile
{
	// Variables
	public static final String headerStart = "[";
	public static final String headerEnd = "]";
	public static final String pairSplit = "=";
	public static final char pairSeparator = '\n';
	
	protected final String header;
	
	
	// Constructor
	public BaseKeyValueFile()
	{
		this.header = headerStart + this.getClass().getSimpleName() + headerEnd;
	}
	
	// Reads in and calls the specifid function for each keyvalue pair we find
	protected void parse(String content, Consumer<? super Pair<String, String>> keyValueCallback)
	{
		content = content.trim();
		String[] lines = content.split("" + pairSeparator);
		
		for(int i = 0; i < lines.length; ++i)
		{
			if(lines[i].contains(header))
				continue;
			
			String[] pair = lines[i].split(pairSplit);
			
			if(pair.length < 2)
				continue;
			
			String key = pair[0].trim().toLowerCase();
			String value = pair[1].trim().toLowerCase();
			
			keyValueCallback.accept(new Pair<String, String>(key, value));
		}
	}

	// Writes the set of keyvalue pairs to a string 
	protected String write(List<Pair<String, String>> toWrite)
	{
		ListIterator<Pair<String, String>> iter = toWrite.listIterator();
		
		String outString = "";
		outString += header + pairSeparator;
		
		while(iter.hasNext())
		{
			Pair<String, String> pair = iter.next();
			String key = pair.First.toLowerCase();
			String value = pair.Second.toLowerCase();
			
			outString += key + pairSplit + value + pairSeparator;
		}
		
		return outString;
	}
}
