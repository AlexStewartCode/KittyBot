package utils;

public class StringUtils
{
	public static String unEscape(String str)
	{
		str = str.replaceAll("\\\\b", "\b");
		str = str.replaceAll("\\\\n", "\n");
		str = str.replaceAll("\\\\t", "\t");
		str = str.replaceAll("\\\\r", "\r");
		str = str.replaceAll("\\\\f", "\f");
		str = str.replaceAll("\\\\\"", "\"");
		str = str.replaceAll("\\\\\\\\\\\\", "\\\\");
		
		return str;
	}
	
	public static String reEscape(String str)
	{
		str = str.replaceAll("\\\b", "\\\\\\b");
		str = str.replaceAll("\\\n", "\\\\\\n");
		str = str.replaceAll("\\\t", "\\\\\\t");
		str = str.replaceAll("\\\r", "\\\\\\r");
		str = str.replaceAll("\\\f", "\\\\\\f");
		
		return str;
	}
	
	// Finds first whitespace in the string
	public static int findFirstWhitespace(String str)
	{
		for (int i = 0; i < str.length(); ++i) 
		{
			if (Character.isWhitespace(str.charAt(i)))
				return i;
		}
	
		return -1;
	}
}
