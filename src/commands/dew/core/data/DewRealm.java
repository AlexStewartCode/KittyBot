package commands.dew.core.data;

import commands.dew.core.api.IDewLuaSerializable;

public final class DewRealm implements IDewLuaSerializable<DewRealm>
{
	public String id = "0";
	public int[][] map = new int[12][24]; // [Rows][Columns]
}
