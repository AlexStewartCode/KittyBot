package core;

public abstract interface IConfigSection
{
	public abstract String getHeader();
	public abstract void preUpdate();
	public abstract void init();
	public abstract String getContent();
}
