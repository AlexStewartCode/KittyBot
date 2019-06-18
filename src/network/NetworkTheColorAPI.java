package network;

import com.google.gson.Gson;

import utils.HTTPUtils;

// This class is designed to easily query and ask for responses from https://www.thecolorapi.com/.
// Unlike some of the other networking classes, the query is much more wrapped.
public class NetworkTheColorAPI
{
	private static final Gson jsonParser = new Gson();
	
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
	}
	
	public ColorData LookupHex(String hex)
	{
		try
		{
			hex = hex.replace("#", "");
			String response = HTTPUtils.SendGETRequest("https://www.thecolorapi.com/id?hex=" + hex);
			
			ColorData data = jsonParser.fromJson(response, ColorData.class);
			return data;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
