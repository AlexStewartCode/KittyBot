package network;

import com.google.gson.Gson;

import network.NetworkE621.E621ResponseObject;
import utils.HTTPUtils;

// This class is designed to easily query and ask for responses from https://www.thecolorapi.com/.
// Unlike some of the other networking classes, the query is much more wrapped.
@SuppressWarnings("unused")
public class NetworkTheColorAPI
{
	private static final Gson jsonParser = new Gson();
	
	private class tca_hsv
	{
		public int h; // 0-255
		public int s; // 0-100, %
		public int v; // 0-100, %
	}
	
	private class tca_hsl
	{
		public int h; // 0-255
		public int s; // 0-100, %
		public int l; // 0-100, %
	}
	
	private class tca_rgb
	{
		public int r; // 0-255
		public int g; // 0-255
		public int b; // 0-255
	}
	
	private class tca_hex
	{
		public String clean; // format xxxxxx, with no #
	}
	
	public class ColorData
	{
		public tca_hex hex;
		public tca_rgb rgb;
		public tca_hsl hsl;
		public tca_hsv hsv;
	}
	
	public ColorData LookupHex()
	{
		String response = HTTPUtils.SendPOSTRequest("https://www.thecolorapi.com/id?hex=24B1E0");
		
		// Use class evaluation on an array of the response imageObject to be able to hold multiple.
		ColorData imageObj = jsonParser.fromJson(response, ColorData.class);
	}
}
