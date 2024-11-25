package importexport;

import java.io.InputStream;

public class TextImportExport 
{
    public static String readline(InputStream bis)
    {
    	boolean empty = true;
    	StringBuilder result = new StringBuilder();
    	try
    	{
	    	while(true)
	    	{
	    		int c = bis.read();
	    		if(c=='\r')
	    		{
	    			//read a line feed
	    			bis.read();
	    			return result.toString();
	    		}
	    		else
	    		if(c=='\n')
	    		{	    			
		    		return result.toString();
	    		}
	    		else
	    		if(c==-1)
	    		{
	    			
	    		}
	    		else
	    		{
	    			empty = false;
	    			result.append((char)c);
	    		}
	    		if(c==-1)
	    		{
	    			if(empty)
	    				return null;
	    			else
	    				return result.toString();
	    		}
	    	}
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.toString());
    	}
    	return null;
    }
}
