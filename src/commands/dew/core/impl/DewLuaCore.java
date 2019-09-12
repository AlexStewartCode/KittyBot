package commands.dew.core.impl;

import java.lang.reflect.Field;

import commands.dew.core.api.IDewLuaCore;
import commands.dew.core.api.IDewLuaSerializable;

public class DewLuaCore implements IDewLuaCore
{
	public static String toLua(IDewLuaSerializable<?> item)
	{
		return toLua(item, 1);
	}
	
	public static String toLua(IDewLuaSerializable<?> item, int tabs)
	{
		String out = "{";
		
		Field[] fields = item.getClass().getDeclaredFields();
		for(int i = 0; i < fields.length; ++i)
		{
			Field current = fields[i];
			try
			{
				String line = "\n";
				
				String fieldTypeRaw = current.getType().toString().toLowerCase();
				int fieldLastDotIndex = fieldTypeRaw.lastIndexOf('.');
				String fieldType = fieldLastDotIndex <= 0 ? fieldTypeRaw : fieldTypeRaw.substring(fieldLastDotIndex + 1);
				
				for(int j = 0; j < tabs; ++j)
					line += "\t";
				
				line += current.getName() + "=";
				
				switch(fieldType)
				{
					case "int": 
						line += "" + current.get(item); 
						break;
						
					case "string": 
						String content = (String)current.get(item);
						
						if(content == null)
							line += "nil";
						else if(content.length() == 0)
							line += "\"\"";
						else
							line += "\"" + content + "\"";
						
						break;
						
					case "dewmaptile ":
						line += "\"" + current.get(item).toString() + "\"";
						break;
						
					default:
						System.out.print("Found a type we couldn't parse: Raw - " + fieldTypeRaw + " | Parsed - " + fieldType + "\n");
						break;
				}
				
				// If we're not the last line, add a comma.
				if(i != fields.length - 1)
				{
					line += ",";
				}
				
				out += line;
			}
			catch (Exception e)
			{
				e.printStackTrace(System.out);
			}
		}
		
		out += "\n}\n";
		return out;
	}
	
	public static <T> IDewLuaSerializable<T> fromLua(String item)
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
		
		return null;
	}
}
