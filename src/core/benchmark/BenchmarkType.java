package core.benchmark;

public enum BenchmarkType
{
	CPU(0), GPU(1), SSD(2), HDD(4), RAM(8), USB(16);  
	
	private final int value;
	private BenchmarkType(int value) 
	{
		this.value = value;
	}
	
	public int getValue() 
	{
		return value;
	}
}
