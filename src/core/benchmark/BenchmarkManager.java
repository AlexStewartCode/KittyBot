package core.benchmark;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import utils.FileUtils;
import utils.directoryMonitor.DirectoryMonitor;
import utils.directoryMonitor.MonitoredFile;

// All things considered, this doesn't need to be particularly efficient since anything
// less than 10ms of search time won't be noticable to an end user really, not for
// a networked application that's not performance bound. 
public class BenchmarkManager
{
	// Variables
	public final String directory = "assets/userbench/";
	public final String extension = ".csv";
	public final String lineDelimiter = "\n";
	
	private List<BenchmarkEntry> raw;
	private DirectoryMonitor directoryMonitor;
	private boolean needsUpdate;
	
	// Read in all extensions
	public BenchmarkManager()
	{
		long start = Instant.now().toEpochMilli();
		
		raw = new ArrayList<BenchmarkEntry>();
		directoryMonitor = new DirectoryMonitor(directory);
		needsUpdate = false;
		
		RebuildLookup();
		
		BenchmarkLog.Log("Took " + (Instant.now().toEpochMilli() - start) + " ms to load " + raw.size() + " entries from " + directoryMonitor.GetCurrentFiles().size() + " file" + (raw.size() > 1 ? "s" : ""));
	}
	
	// Rebuilds the files being monitored
	public void RebuildLookup()
	{
		synchronized(raw)
		{
			raw.clear();
			List<MonitoredFile> files;
			
			synchronized(directoryMonitor)
			{
				files = directoryMonitor.GetCurrentFiles();
			}
			
			if(files != null)
			{
				for(MonitoredFile mf : files)
				{
					String contents = FileUtils.ReadContent(mf.path);
					String[] lines = contents.split(lineDelimiter);
					
					for(int i = 1; i < lines.length; ++i)
						raw.add(new BenchmarkEntry(lines[i]));
				}
			}
			else
			{
				BenchmarkLog.Warn("No " + extension + " files where found in " + directory);
			}
		}
	}
	
	// Search for a substring in the model name
	public List<BenchmarkEntry> FindModel(String modelSubstr)
	{
		long start = Instant.now().toEpochMilli();
		List<BenchmarkEntry> matching = new ArrayList<BenchmarkEntry>();
		String searchSubstr = modelSubstr.toLowerCase();
		
		for(BenchmarkEntry e : raw)
		{
			String model = e.model.toLowerCase().trim();
			
			if(model.contains(searchSubstr))
				matching.add(e);
		}
		
		BenchmarkLog.Log("Searched for '" + searchSubstr + "' for "+ (Instant.now().toEpochMilli() - start) + "ms and found " + matching.size() + " entries.");
		return matching;
	}
	
	// Keeps tabs on any changes of the files.
	public void Update()
	{
		needsUpdate = false;
		directoryMonitor.Update(this::OnRescan, this::OnRescan, this::OnRescan);
		
		if(needsUpdate)
			RebuildLookup();
	}
	
	// When a file is changed, handle it.
	private void OnRescan(MonitoredFile file)
	{
		// For now, all we need is to note that something was adjusted. 
		if(file.path.endsWith(extension))
			needsUpdate = true;
	}
}
