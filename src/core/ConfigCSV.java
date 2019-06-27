package core;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

public class ConfigCSV
{
	// Variables
	private final String path;
	private List<ConfigItem> fileContents;
	
	// Constructor
	public ConfigCSV(List<IConfigSection> sections, String path)
	{
		this.path = path;
		fileContents = new Vector<ConfigItem>();
		
		read(path);
	}
	
	// Creates the writer in the format we desire.
	public static CSVWriter makeWriter(Writer writer)
	{
		return new CSVWriter(writer,
				CSVWriter.DEFAULT_SEPARATOR,
				CSVWriter.DEFAULT_QUOTE_CHARACTER,
				'\\',
				CSVWriter.DEFAULT_LINE_END);
	}
	
	// Read the file. This is internal only.
	private void read(String filepath)
	{
		try
		{
			File configFile = new File(filepath);
			
			// If we had to create a new file, write the default header into it.
			if(configFile.createNewFile())
			{
				Writer writer = Files.newBufferedWriter(Paths.get(path));
				CSVWriter csvWriter = makeWriter(writer);
				csvWriter.writeNext(ConfigItem.header);
				csvWriter.close();
			}
			
			Reader reader = Files.newBufferedReader(Paths.get(filepath));
			CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
			fileContents.clear();
			
			// Read contents. As for the line number, we not only start counting at 1, we also skip the header.
			int line = 2; 
			String[] record = null;
			while ((record = csvReader.readNext()) != null)
			{
				if(record.length < ConfigItem.items)
				{
					System.out.println("Incorrect number of items on line " + line + " in " + path);
					continue;
				}
				
				ConfigItem parsedLine = new ConfigItem(record[0], record[1], record[2]);				
				System.out.println(parsedLine);
				fileContents.add(parsedLine);
				++line;
			}
			
			// Close down
			csvReader.close();
			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// Get a copy of the internal items
	public List<ConfigItem> getItems()
	{
		return Arrays.asList((ConfigItem[])fileContents.toArray());
	}
	
	// Set the internal items
	public void setItems(List<ConfigItem> newList)
	{
		fileContents = newList;
	}
	
	// Write out the file, header then body.
	public void writeFile()
	{
		try
		{
			Writer writer = Files.newBufferedWriter(Paths.get(path));
			CSVWriter csvWriter = makeWriter(writer);
			
			csvWriter.writeNext(ConfigItem.header);
			
			for(ConfigItem item : fileContents)
			{
				csvWriter.writeNext(item.getAll());
			}
			
			csvWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// Static utility function
	public static Map<String, List<ConfigItem>> groupByColumn(List<ConfigItem> items, int columnNumber)
	{
		if(items == null || items.size() <= 0 || columnNumber < 0)
		{
			return null;
		}
		
		Map<String, List<ConfigItem>> sortedItems = new HashMap<String, List<ConfigItem>>();
		
		for(ConfigItem item : items)
		{
			String key = item.getColumn(columnNumber);
			List<ConfigItem> list = sortedItems.getOrDefault(key, new Vector<ConfigItem>());
			list.add(item);
			sortedItems.put(key, list);
		}
		
		return sortedItems;
	}
}
