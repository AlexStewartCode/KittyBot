package core;

import java.util.List;
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
			
			// Being monitoring configs and mark this as the instance now that it's made
			monitoredConfigFile = new FileMonitor(filepath);
			build(filepath);

			instance = this;
		}
		else
		{
			System.out.println("Only one config class is allowed, period. Aborting.");
			System.out.flush();
			System.exit(-1);
		}
	}
	
	public void build(String path)
	{
		configCSV = new ConfigCSV(sections, path);
		configCSV.writeFile();
	}
	
	public void upkeep()
	{
		monitoredConfigFile.update((monitoredFile) -> {
			build(monitoredFile.path.toString());
		});
	}
}
