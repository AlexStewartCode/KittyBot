package commands.dew.core.api;

// Commands are standalone pieces of data that contain only structures.
// Each command is simplistic and represents an action in the game world.
// Format:
/*
return
{
	name="SomeCommand",
	data={
		-- some object here --
	}
}
*/
public interface IDewMessage<T>
{
	public String getMessageName();
	public T getData();
}
