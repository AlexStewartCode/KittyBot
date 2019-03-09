package network;

import java.io.*;
import java.net.*;

import offline.Ref;

public class NetworkWolfram 
{
	private static Integer num = 1;
	
	public String getWolfram(String input) throws IOException
	{
		String name = null;
        synchronized(num)
        {
            name = "wolfram_" + num;
            ++num;
        }
        
        URL url = new URL("http://api.wolframalpha.com/v1/simple?appid=" + Ref.wolfRamID + "&i=" 
        + URLEncoder.encode(input, "UTF-8"));
        
        InputStream in = new BufferedInputStream(url.openStream());
        OutputStream out = new BufferedOutputStream(new FileOutputStream(name + ".png"));
        
        for ( int i; (i = in.read()) != -1; ) 
        {
            out.write(i);
        }
        
        in.close();
        out.close();
        return name + ".png";
	}
}