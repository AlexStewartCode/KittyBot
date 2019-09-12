package commands.dew.core.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.JsePlatform;
import commands.dew.core.api.IDewLuaCore;
import commands.dew.core.api.IDewLuaSerializable;

public class DewLuaCore implements IDewLuaCore
{
	private static Globals globalLuaCore = null;
	
	public static String toLua(IDewLuaSerializable<?> item)
	{
		return toLua(item, 0);
	}
	
	private static String tab(int num)
	{
		String line = "";
		
		for(int i = 0; i < num; ++i)
			line += "\t";
		
		return line;
	}
	
	public static String toLua(IDewLuaSerializable<?> item, int tabs)
	{
		String out = tab(tabs) + "return {\n";
		
		// Set up type information
		out += tab(tabs + 1) + "type=\"" + item.getClass().getTypeName() + "\",\n";
		
		
		// Set up data information
		out += tab(tabs + 1) + "data={\n";
		
		// Write data
		Field[] fields = item.getClass().getDeclaredFields();
		for(int i = 0; i < fields.length; ++i)
		{
			Field current = fields[i];
			try
			{
				String line = "";
				String fieldType = current.getType().toString().toLowerCase();
				
				line += tab(tabs + 2);
				line += current.getName() + "=";
				
				switch(fieldType)
				{
					case "int": 
						line += "" + current.get(item); 
						break;
						
					case "class java.lang.string": 
						String content = (String)current.get(item);
						
						if(content == null || content.length() == 0)
							line += "\"\"";
						else
							line += "\"" + content + "\"";
						
						break;
						
					case "dewmaptile ":
						line += "\"" + current.get(item).toString() + "\"";
						break;
						
					default:
						line += "nil";
						System.err.print("Type could not be foramtted as lua: " + fieldType + "\n");
						break;
				}
				
				// If we're not the last line, add a comma.
				if(i != fields.length - 1)
					line += ",";
				
				out += line + "\n";
			}
			catch (Exception e)
			{
				e.printStackTrace(System.err);
			}
		}
		
		out += tab(tabs + 1) + "}\n";
		out += tab(tabs)+ "}\n";
		return out;
	}
	
	@SuppressWarnings({ "null", "unchecked" })
	public static <T> T fromLua(T t, String lua)
	{
		if(globalLuaCore == null)
		{
			globalLuaCore = JsePlatform.standardGlobals();
		}
		
		LuaValue val = globalLuaCore.load(lua).call();
		String type = val.get("type").toString();
		
		try
		{
			Class<?> concreteClass = Class.forName(type);
			return (T)concreteClass;
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace(System.err);
		}
		
		return null;
	}
}
