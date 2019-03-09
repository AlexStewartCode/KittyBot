package core.rpg;

public class RPGInput
{
	public String raw;
	public String key;
	public String value;
	
	public RPGInput(String raw)
	{
		this.raw = raw;
		this.key = "";
		this.value = "";
		
		if(raw == null || raw.length() == 0)
			return;
		
		raw = raw.trim();
		int whitespacePos = FindFirstWhitespace(raw);
		
		if(whitespacePos == -1)
		{
			key = raw;
			return;
		}
		
		key = raw.substring(0, whitespacePos).trim();
		value = raw.substring(whitespacePos).trim();
	}
	
	// Finds first whitespace in the string
	private int FindFirstWhitespace(String str)
	{
		for (int i = 0; i < str.length(); ++i) 
		{
			if (Character.isWhitespace(str.charAt(i)))
				return i;
		}
	
		return -1;
	}
}
