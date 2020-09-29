package network;

import java.util.List;

import com.google.gson.Gson;

import dataStructures.GenericImage;
import offline.Ref;
import utils.HTTPUtils;

public class NetworkSauceNAO 
{
	private static final Gson jsonParser_ = new Gson();
	private static final String API_ROOT = "https://saucenao.com/search.php?dbmask=268435456&output_type=2&numres=10&api_key=" + Ref.SauceNAOKey +"&url=";
	NetworkE6 e6Sauce = new NetworkE6();
	private class SauceNAOResponseObject
	{
		List<SauceNAOResult> results;
	}
	
	private class SauceNAOResult
	{
		SauceNAOData data;
		SauceNAOHeader header; 
	}
	
	private class SauceNAOHeader
	{
		double similarity;
	}
	
	private class SauceNAOData
	{
		String [] ext_urls;
	}
	
	public GenericImage getSauce(String input)
	{
		double similarity; 
		input = input.trim();
		input = input.replace("+", "%2B");
		input = input.replace(" ", "%20");
		
		String res = HTTPUtils.sendGETRequest(API_ROOT + input);
		SauceNAOResponseObject sauce = jsonParser_.fromJson(res, SauceNAOResponseObject.class);
		String sauceUrl = sauce.results.get(0).data.ext_urls[0];
		similarity = sauce.results.get(0).header.similarity;
		if(sauceUrl.startsWith("https://e621.net/post/show/"))
		{
			return e6Sauce.getE6("id:" + sauceUrl.substring(27), "Confidence: " + similarity + "%");
		}
		GenericImage unknown = new GenericImage("", "","");
		return unknown;
	}
	
}
