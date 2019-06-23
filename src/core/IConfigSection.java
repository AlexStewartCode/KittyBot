package core;

public abstract interface IConfigSection
{
	public abstract String getHeader();
	public abstract void read(String contents);
	public abstract String write();
}
