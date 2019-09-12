package commands.dew.core.data;

import commands.dew.core.api.IDewLuaSerializable;

public final class DewMap implements IDewLuaSerializable<DewMap>
{
	public int mapWidth = 20;
	public int mapHeight = 20;
	public int[][] map;
}
