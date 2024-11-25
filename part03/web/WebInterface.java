package web;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Enumeration;

public class WebInterface implements Runnable
{
	int port = 9090;
	//the web requests to be processed by the client
	ArrayList queue = new ArrayList();
	public String path = null;
	
	public WebInterface(int port)
	{
		this.port = port;

		try
		{			
            	boolean hasmac = false;
		       System.out.println("Host addr: " + InetAddress.getLocalHost().getHostAddress());  // often returns "127.0.0.1"
               Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
               for (; n.hasMoreElements();)
               {
                       NetworkInterface e = n.nextElement();
                       System.out.println("Interface: " + e.getName());
                       Enumeration<InetAddress> a = e.getInetAddresses();
                       for (; a.hasMoreElements();)
                       {
                               InetAddress addr = a.nextElement();
                               String haddress = addr.getHostAddress();
                        	   if(!haddress.contains("."))
                        	   {
                        		   hasmac = true;
                        	   }
                               if(path==null)
                               {
                            	   if(haddress.contains("."))
                            	   {
                            		   path = haddress+":"+port;
                            	   }
                               }
                               System.out.println("  " + haddress);
                       }
                       if(path!=null)
                       {
                    	   if(hasmac==false)
                    		   path = null;
                       }
               }
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}

		Thread t = new Thread( this );
		t.setDaemon( true );
		t.start();
	}
	
	public synchronized void getQueue(ArrayList q)
	{
		q.addAll(queue);
		queue.clear();
	}
	public synchronized void queue(Object e)
	{
		queue.add(e);
	}
	
	public void run()
	{
		try
		{
			final ServerSocket ss = new ServerSocket( port );
			while( true )
				new WebRequest( this, ss.accept() );
		}
		catch ( IOException ioe )
		{
		}
	}

	public static void launchBrowser(String url)
	{
		try
		{
			String os = System.getProperty("os.name").toLowerCase();
			if(os.indexOf("win")>=0)
			{
				Runtime rt = Runtime.getRuntime();
				rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
			}
			else
			if(os.indexOf("mac") >= 0)
			{
				Runtime rt = Runtime.getRuntime();
				rt.exec("open " + url);			
			}
			else
			if(os.indexOf("nix") >=0 || os.indexOf("nux") >=0)
			{
				Runtime rt = Runtime.getRuntime();
				String[] browsers = { "google-chrome", "firefox", "mozilla", "epiphany", "konqueror",
				                                 "netscape", "opera", "links", "lynx" };
				 
				StringBuffer cmd = new StringBuffer();
				for (int i = 0; i < browsers.length; i++)
				    if(i == 0)
				        cmd.append(String.format(    "%s \"%s\"", browsers[i], url));
				    else
				        cmd.append(String.format(" || %s \"%s\"", browsers[i], url)); 
				    // If the first didn't work, try the next browser and so on
	
				rt.exec(new String[] { "sh", "-c", cmd.toString() });
			}
			else
			{
				System.out.println("Can't launch browser on this unknown operating system");
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());			
		}
	}
}
