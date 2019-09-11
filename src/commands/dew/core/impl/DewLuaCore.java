package commands.dew.core.impl;

import java.lang.reflect.Field;

import commands.dew.core.api.IDewLuaCore;
import commands.dew.core.api.IDewLuaSerializable;

public class DewLuaCore implements IDewLuaCore
{
	public void Parse(IDewLuaSerializable item)
	{
		for(Field field : item.getClass().getDeclaredFields())
		{
			
		}
	}
}
