package commands.dew.core.impl;

import java.lang.reflect.Field;

import commands.dew.core.api.IDewLuaCore;
import commands.dew.core.api.IDewLuaSerializable;

public class DewLuaCore implements IDewLuaCore
{
	public static void toLua(IDewLuaSerializable item)
	{
		for(Field field : item.getClass().getDeclaredFields())
		{
			try
			{
				System.out.println(field.getName() + " " + field.getType() + " " + field.get(item));
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}
}
