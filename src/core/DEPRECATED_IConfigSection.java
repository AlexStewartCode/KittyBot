package core;

public abstract interface DEPRECATED_IConfigSection
{
	public abstract String getHeader();
	public abstract void read(String contents);
	public abstract String write();
}
