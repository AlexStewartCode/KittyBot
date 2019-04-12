package dataStructures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// This is a class designed to parse ini inspired key-value pairs that are sectioned off.
// The difference here is that this is more permissive than an ini file, and only accepts
// a single split character, not the traditional set an ini does. All of the following are valid:
//
// [ExampleSection]
// Key=Value
//  Valid Ridiculous Key&\n\t_.:;foo   =Some Value
// Key=
//
public class SectionedKeyValue
{
	// Variables
	public final char SectionStart = '[';
	public final char SectionEnd = ']';
	public final char KeyValueLineSeparator = '\n';
	public final String KeyValueSplit = "=";
	private HashMap<String, HashMap<String, String>> sectionKeyValue;
	
	// String constructor that parses the input string into the object
	public SectionedKeyValue(String input)
	{
		sectionKeyValue = new HashMap<String, HashMap<String, String>>();
		
		Parse(input);
	}
	
	// Adds a KeyValue pair to the specified section if it's not already there.
	// Also creates the section if it's not already present.
	public void AddKeyValue(String sectionName, String key, String value)
	{
		AddSection(sectionName);
		sectionKeyValue.get(sectionName).putIfAbsent(key, value);
	}
	
	// Returns a HashMap of Keys to Values for a given section
	public HashMap<String, String> GetSection(String sectionName)
	{
		return sectionKeyValue.get(sectionName);
	}
	
	// Stores the internal hashmap as a string then reutrns it. 
	// Places 1 newline between the sections.
	@SuppressWarnings({"rawtypes", "unchecked"})
	public String ToString()
	{
		String out = "";
		
		Iterator it = sectionKeyValue.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry pair = (Map.Entry)it.next();
			out += ("" + SectionStart + pair.getKey() + SectionEnd);
			
			Iterator internal = ((HashMap<String, String>)pair.getValue()).entrySet().iterator();
			
			while(internal.hasNext())
			{
				Map.Entry internalPair = (Map.Entry)internal.next();
				out += (internalPair.getKey() + KeyValueSplit + internalPair.getValue()) + KeyValueLineSeparator;
			}
			
			out += "\n";
		}
		
		return out;
	}
	
	// Adds a given section to the hashmap if it's not already present
	private void AddSection(String sectionName)
	{
		sectionKeyValue.putIfAbsent(sectionName, new HashMap<String, String>());
	}
	
	// Parses out the string passed in into the sectionkeyValue HashMap. 
	// Any character used in a split call is escaped just in case on 
	// account of some characters having specific regex meanings.
	private void Parse(String input)
	{
		String[] sections = input.split("\\" + SectionStart);

		for(int sec = 0; sec < sections.length; ++sec)
		{
			// Gather information about the contents of the section, and the header.
			String section = sections[sec];
			if(section.length() < 2)
				continue;
			
			int pos = section.indexOf(SectionEnd);
			if(pos == -1)
				continue;
			
			// Parse section name. If it already exists, don't bother making it.
			String sectionName = section.substring(0, pos);
			AddSection(sectionName);
				
			// Parse out the pairs within the section, split them all out. 
			String unparsedPairs = section.substring(pos + 1);
			String[] pairs = unparsedPairs.split("\\" + KeyValueLineSeparator);
			
			// Parse out valid key-value pairs, and store them in the specified section.
			// At this point, we can be guarenteed that sectionName is in the Hashmap.
			for(int pair = 0; pair < pairs.length; ++pair)
			{
				String line = pairs[pair];
				int splitPos = line.indexOf(KeyValueSplit);
				if(splitPos < 0)
					continue;
				
				String key = line.substring(0, splitPos);
				String value = line.substring(splitPos + KeyValueSplit.length());
				sectionKeyValue.get(sectionName).putIfAbsent(key, value);
			}
		}
	}
	
	// Dumps out a string array
	@SuppressWarnings("unused")
	private void Dump(String[] toPrint)
	{
		System.out.println("Length: " + toPrint.length);
		
		for(int i = 0; i < toPrint.length; ++i)
			System.out.println(toPrint[i]);
	}
}
