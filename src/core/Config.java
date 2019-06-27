package core;

import java.util.List;
import java.util.Vector;

public class Config
{	
	ConfigCSV configCSV;
	List<IConfigSection> sections;
	
	public static Config instance;
	
	public Config()
	{
		if(instance == null)
		{
			sections = new Vector<IConfigSection>();

			configCSV = new ConfigCSV(sections, "./config.csv");
			configCSV.writeFile();
			
			instance = this;
		}
		else
		{
			System.out.println("Only one config class is allowed, period. Aborting.");
			System.out.flush();
			System.exit(-1);
		}
	}
}
