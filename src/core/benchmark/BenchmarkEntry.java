package core.benchmark;

public class BenchmarkEntry
{
	public final BenchmarkType type;
	public final String partNumber;
	public final String brand;
	public final String model;
	public final int rank;
	public final float benchmark;
	public final int samples;
	public final String url;
	
	BenchmarkEntry(String input)
	{
		String[] row = input.split(",");
		
		type = BenchmarkType.valueOf(row[0]);
		partNumber = row[1];
		brand = row[2];
		model = row[3];
		rank = Integer.parseInt(row[4]);
		benchmark = Float.parseFloat(row[5]);
		samples = Integer.parseInt(row[6]);
		url = row[7];
	}
}
