package core;

public abstract class DatabaseTrackedObject
{
	private boolean isDirty;
	public final String identifier;
	
	public DatabaseTrackedObject(String identifier)
	{
		this.isDirty = false;
		this.identifier = identifier;
	}
	
	public final boolean IsDirty()
	{
		return isDirty;
	}
	
	public final void MarkDirty()
	{
		isDirty = true;
	}
	
	public final void Resolve()
	{
		isDirty = false;
	}
	
	// TODO(wisp): Not the best way to handle this, potentially consider
	// using an object factory that looks up how to serialize and
	// deserialize based on the type of the thing being tracked.
	// For now, this is fine. 
	public abstract String Serialize();
	public abstract void DeSerialzie(String string);
}
