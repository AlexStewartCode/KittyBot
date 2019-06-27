package core;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import utils.io.FileMonitor;

public class Config
{	
	private static final String filepath = Constants.AssetDirectory + Constants.ConfigFilename; 
	
	private ConfigCSV configCSV;
	private List<IConfigSection> sections;
	private FileMonitor monitoredConfigFile;
	
	public static Config instance;
	
	public Config()
	{
		if(instance == null)
		{
			sections = new Vector<IConfigSection>();
			
			// Add all sections
			sections.add(new LocCommands());
			sections.add(new LocStrings());
			sections.add(new CommandEnabler());
			
			// Build the config file, then start monitoring it.
			buildConfigFile(filepath);
			monitoredConfigFile = new FileMonitor(filepath);

			instance = this;
		}
		else
		{
			System.out.println("Only one config class is allowed, period. Aborting.");
			System.out.flush();
			System.exit(-1);
		}
	}
	
	public void buildConfigFile(String path)
	{
		configCSV = new ConfigCSV(sections, path);
		Map<String, List<ConfigItem>> groupedSections = ConfigCSV.groupByColumn(configCSV.getItems(), 0);
		
		if(groupedSections != null)
		{
			for(IConfigSection section : sections)
			{
				List<ConfigItem> items = groupedSections.getOrDefault(section.getSectionTitle(), new Vector<ConfigItem>());
				section.consume(items);
			}
		}
		
		configCSV.writeFile();
	}
	
	public void upkeep()
	{
		monitoredConfigFile.update((monitoredFile) -> {
			buildConfigFile(monitoredFile.path.toString());
		});
	}
}
