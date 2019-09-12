package commands.dew.core.data;

import commands.dew.core.api.IDewLuaSerializable;

// Treat all data objects as structs for the purposes of this.
public class DewMovementInfo implements IDewLuaSerializable<DewMovementInfo>
{
	public String entityID;
	public int x;
	public int y;
}
