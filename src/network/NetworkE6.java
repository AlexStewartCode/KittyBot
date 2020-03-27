package network;

import com.google.gson.Gson;

import dataStructures.GenericImage;
import offline.Ref;
import utils.HTTPUtils;

/**
 * This is the e621 request class, designed for form and parse requests that
 * use the e621 API, and is entirely static. 
 * 
 * If you ever find this class randomly not working,
 * it may be a good idea to make sure the user agent string is set in 
 * HTTPUtils to something other than a browser emulating string, or the default
 * java one.
 * 
 * @author Wisp
 * Edited by Rin
 */
public class NetworkE6
{   
	  ///////////////////////////////////////
	 // Internal JSON class and variables //
	//////////////////////////////////.////
	private static final Gson jsonParser_ = new Gson();
	private static final String API_ROOT = "https://e621.net/posts.json?";
	private static int maxSearchResults_ = 10;
	private static String[] blacklist = Ref.e621Blacklist;
	
	private class E6ResponseObject
	{
		// public variables matching the case and the type we want for JSON.
		// There are many more fields, but if we don't provide some it just
		// doesn't bother parsing them.
		public E6ResponseObjectPost [] posts; 
		
	}
	
	private class E6ResponseObjectPost
	{
		public String id;
		public String [] sources;
		public String[] pools;
		public String rating;
		public E6ResponseObjectFile file;
		public E6ResponseObjectScore score;
		public E6ResponseObjectTags tags;
		public E6ResponseObjectRelationships relationships;
	}
	
	private class E6ResponseObjectFile
	{
		public String url;
	}
	
	private class E6ResponseObjectScore
	{
		public String total; 
	}
	
	private class E6ResponseObjectTags
	{
		public String[] general;
		public String[] artist;
	}
	
	private class E6ResponseObjectRelationships
	{
		public String has_children;
		public String[] children; 
	}
	
	

	
	
	  ////////////////////
	 // Static methods //
	////////////////////
	// Requests a specific image, then returns a few.
	public GenericImage getE6(String input)
	{
		GenericImage image = new GenericImage("","","");
		boolean blacklisted;
		// Clean up request and replace problematic characters for the query string.
		input = input.trim();
		input = input.replace("+", "%2B");
		input = input.replace(" ", "%20");
		
		// Configure and send request. Note: Random ordering added as first
		// tag by default. User-provided tags, therefore, will override it. 
		// If order:score is provided, that will be honored over order:random.
		String res = HTTPUtils.sendGETRequest(API_ROOT + "tags=order:random%20" + input + "&limit=" + maxSearchResults_);
		
		if(res != null)
		{
			
			// Use class evaluation on an array of the response imageObject to be able to hold multiple.
			System.out.println("Still");
			E6ResponseObject imageObj = jsonParser_.fromJson(res, E6ResponseObject.class);
			System.out.println("Broken");
			// For now, we really just wanna display images and their source. 
			// Append them all separately to a response string w/ some flavor text.
			if(imageObj.posts.length < 1)
			{
				
			}
			else
			{
				for(int i = 0; i < imageObj.posts.length; ++i)
				{
					String [] TagsList = imageObj.posts[i].tags.general;
					blacklisted = false;
					for(int j = 0; j < blacklist.length; j++)
					{
						for(int k = 0; k < TagsList.length; k ++)
						{
							if(TagsList[k].equals(blacklist[j]))
							{
								System.out.println("BLACKLISTED " + blacklist[j]);
								blacklisted = true;
							}
						}
					}
					
					if(blacklisted)
					{
						return null;
					}
					// We will always have a file URL. That's a given.
					image.editImageURL(imageObj.posts[i].file.url);
					image.editPostURL("https://e621.net/posts/" + imageObj.posts[i].id);
					if(imageObj.posts[i].tags.artist.length > 0)
						{
							String artists = "";
							for(int j = 0; j < imageObj.posts[i].tags.artist.length; j++)
								artists += imageObj.posts[i].tags.artist[j];
							image.editArtist(artists);
						}
					else
						image.editArtist("Artist Not Found!");
					
					image.editAuthorImage("https://e621.net/favicon.ico");
					
					String sources = "";
					
					if(imageObj.posts[i].sources != null)
					{
						for(int k = 0; k < imageObj.posts[i].sources.length; k ++)
						{
							if(!imageObj.posts[i].sources[k].endsWith(imageObj.posts[i].file.url.substring(imageObj.posts[i].file.url.lastIndexOf('.'))))
							{
								String source = imageObj.posts[i].sources[k];
								if(source.contains("//www."))
								{
									source = source.substring(source.indexOf(".") + 1);
									source = source.substring(0, source.indexOf('.'));
								}
								else 
								{
									source = source.substring(source.indexOf("/") + 2);
									source = source.substring(0, source.indexOf('.'));
								}
								sources += " [" + source + "](" + imageObj.posts[i].sources[k] + ") "; 
							}
						}
						image.editDescriptionText("Score: " + imageObj.posts[i].score.total + "\n**Source:**" + sources);
					}
					else
					{
						image.editDescriptionText("Score: " + imageObj.posts[i].score.total);
					}
					
					
					String descTags = "";
					if(imageObj.posts[i].tags.general.length > 15)
					{
						for(int o = 0; o < 15; o++)
						{
							descTags += imageObj.posts[i].tags.general[o] + ", ";
						}
						image.editFooterText("[Tags] " + descTags + " etc.");
					}
					else
					{
						descTags = imageObj.posts[i].tags.general.toString();
						image.editFooterText("[Tags] " + descTags);
					}
					
					if(image.output().authorText != null)
					{
						return image; 
					}
				}
			}
			
		}
		return image;
	}
}