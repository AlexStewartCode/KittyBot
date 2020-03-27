package network;

import utils.HTTPUtils;

public class NetworkColiru 
{
	public String compileCPlusPlus(String query)
	{
		// Escape characters that need escaping - mostly whitespace.
		query = query.replace("\\n", "\\\\n");
		query = query.replace("\\t", "\\\\t");
		query = query.replace("\n", "\\n");
		query = query.replace("\"", "\\\"");
		query = query.replace("\t", "\\t");
		query = query.replace("`", "");

//		// replace backticks on only the edges of the string.
//		StringBuilder s = new StringBuilder(query);
//		
//		for(int i = 0; i < s.length(); ++i)
//		{
//			if(s.charAt(i) != '`')
//				break;
//			
//			s.setCharAt(i, ' ');
//		}
//		
//		for(int i = s.length() - 1; i >= 0; --i)
//		{
//			if(s.charAt(i) != '`')
//				break;
//			
//			s.setCharAt(i, ' ');
//		}
//		
//		query = s.toString().trim();
		
		// Determine if the query is effectively empty or not. If it is, don't attempt to compile it.
		if(query.trim().length() == 0)
		{
			return "I can't compile nothing!";
		}

		// Send the compilation request!
		String result = HTTPUtils.sendPOSTRequest("http://coliru.stacked-crooked.com/compile"
				, "{ \"cmd\": \"g++ main.cpp && ./a.out\", \"src\": \"" + query + "\" }");
		
		// If we got a valid response...
		if(!result.isEmpty())
		{
			result = "Here's what happened when I went to compiled that! \n```" + result + "```";
		}
		
		return result;
	}
	
}
