package network;

import com.google.gson.Gson;

import utils.HTTPUtils;

// This class is designed to easily query and ask for responses from https://www.thecolorapi.com/.
// Unlike some of the other networking classes, the query is much more wrapped.
public class NetworkTheColorAPI
{
	private static final Gson jsonParser = new Gson();
	
	public class tca_XYZ
	{
		// This is also known as the CIE 1931 colorspace.
		// Value meanings: https://en.wikipedia.org/wiki/CIE_1931_color_space
		public int X;
		public int Y;
		public int Z;
	}
	
	public class tca_cymk
	{
		public int c; // 0-100
		public int m; // 0-100
		public int y; // 0-100
		public int k; // 0-100
	}
	
	public class tca_name
	{
		public String value; // The name of the color in english
		public boolean exact_match_name; // Is this exactly the name of the color searched for?
		public String closest_named_hex; // The hex value of the actual value
	}
	
	public class tca_hsv
	{
		public int h; // 0-255
		public int s; // 0-100, %
		public int v; // 0-100, %
	}
	
	public class tca_hsl
	{
		public int h; // 0-255
		public int s; // 0-100, %
		public int l; // 0-100, %
	}
	
	public class tca_rgb
	{
		public int r; // 0-255
		public int g; // 0-255
		public int b; // 0-255
	}
	
	public class tca_hex
	{
		public String clean; // format xxxxxx, with no #
	}
	
	public class ColorData
	{
		public tca_name name;
		public tca_hex hex;
		public tca_rgb rgb;
		public tca_hsl hsl;
		public tca_hsv hsv;
		public tca_cymk cmyk;
		public tca_XYZ XYZ;
	}
	
	public ColorData lookupHex(String hex)
	{
		if(hex == null)
			return null;
		
		hex = hex.replace("#", "").trim();
		int len = hex.length();
		
		if(len == 0)
			return null;
		
		// Verify we're either a full or half length color. Intermediate colors not supported.
		// Alpha is also not supported.
		if(len != 6 && len != 3)
			return null;
		
		try
		{
			String hexUpper = hex.toUpperCase();
			
			// Verify the number is hex.
			for(int i = 0; i < hexUpper.length(); ++i)
			{
				char current = hexUpper.charAt(i);
				Boolean isValidNum = current >= '0' && current <= '9';
				Boolean isValidChar = current >= 'A' && current <= 'F';
				
				// If it's not a valid character or a valid number, return null/
				if(!isValidNum && !isValidChar)
					return null;
			}
			
			String response = HTTPUtils.sendGETRequest("https://www.thecolorapi.com/id?hex=" + hex);
			
			ColorData data = jsonParser.fromJson(response, ColorData.class);
			return data;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
