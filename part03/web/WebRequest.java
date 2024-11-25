package web;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import importexport.ByteBufferReaderWriter;
import importexport.OutputStreamCallback;
import importexport.TextImportExport;

public class WebRequest implements Runnable
{
	WebInterface winterface;
	private Socket socket;
	InputStream in;
	public Response r = null;
	public String path;
	public HashMap<String, String> parms = new HashMap<String, String>();
	
	/**
	 * Some HTTP response status codes
	 */
	public static final String
	    HTTP_OK = "200 OK",
	    HTTP_PARTIAL = "206 Partial Content",
		//HTTP_REDIRECT = "301 Moved Permanently",
	    HTTP_REDIRECT = "307 Temporary Redirect",
		HTTP_FORBIDDEN = "403 Forbidden",
		HTTP_NOTFOUND = "404 Not Found",
		HTTP_BADREQUEST = "400 Bad Request",
		HTTP_INTERNALERROR = "500 Internal Server Error",
		HTTP_NOTIMPLEMENTED = "501 Not Implemented";

	/**
	 * Common mime types for dynamic content
	 */
	public static final String
		MIME_PLAINTEXT = "text/plain",
		MIME_HTML = "text/html",
		MIME_DEFAULT_BINARY = "application/octet-stream",
		MIME_MP4 = "video/mp4";
	
	private static java.text.SimpleDateFormat gmtFrmt;
	static
	{
		gmtFrmt = new java.text.SimpleDateFormat( "E, d MMM yyyy HH:mm:ss 'GMT'", Locale.UK);
		gmtFrmt.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	/**
     * Returns the MIME type based on the file path's extension.
     *
     * @param filePath The path to the file.
     * @return The corresponding MIME type as a String.
     */
    public static String getMimeType(String filePath) {
        if (filePath == null) {
            return "application/octet-stream"; // Default MIME type
        }

        String lowerCasePath = filePath.toLowerCase(Locale.ENGLISH);

        if (lowerCasePath.endsWith(".html") || lowerCasePath.endsWith(".htm")) {
            return "text/html";
        } else if (lowerCasePath.endsWith(".js")) {
            return "application/javascript";
        } else if (lowerCasePath.endsWith(".css")) {
            return "text/css";
        } else if (lowerCasePath.endsWith(".json")) {
            return "application/json";
        } else if (lowerCasePath.endsWith(".xml")) {
            return "application/xml";
        } else if (lowerCasePath.endsWith(".jpg") || lowerCasePath.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lowerCasePath.endsWith(".png")) {
            return "image/png";
        } else if (lowerCasePath.endsWith(".gif")) {
            return "image/gif";
        } else if (lowerCasePath.endsWith(".bmp")) {
            return "image/bmp";
        } else if (lowerCasePath.endsWith(".webp")) {
            return "image/webp";
        } else if (lowerCasePath.endsWith(".svg")) {
            return "image/svg+xml";
        } else if (lowerCasePath.endsWith(".mp4")) {
            return "video/mp4";
        } else if (lowerCasePath.endsWith(".webm")) {
            return "video/webm";
        } else if (lowerCasePath.endsWith(".ogg")) {
            return "video/ogg";
        } else if (lowerCasePath.endsWith(".mp3")) {
            return "audio/mpeg";
        } else if (lowerCasePath.endsWith(".wav")) {
            return "audio/wav";
        } else if (lowerCasePath.endsWith(".ogg")) {
            return "audio/ogg";
        } else if (lowerCasePath.endsWith(".pdf")) {
            return "application/pdf";
        } else if (lowerCasePath.endsWith(".zip")) {
            return "application/zip";
        } else if (lowerCasePath.endsWith(".rar")) {
            return "application/vnd.rar";
        } else if (lowerCasePath.endsWith(".7z")) {
            return "application/x-7z-compressed";
        } else if (lowerCasePath.endsWith(".doc")) {
            return "application/msword";
        } else if (lowerCasePath.endsWith(".docx")) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if (lowerCasePath.endsWith(".xls")) {
            return "application/vnd.ms-excel";
        } else if (lowerCasePath.endsWith(".xlsx")) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if (lowerCasePath.endsWith(".ppt")) {
            return "application/vnd.ms-powerpoint";
        } else if (lowerCasePath.endsWith(".pptx")) {
            return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        } else if (lowerCasePath.endsWith(".txt")) {
            return "text/plain";
        } else if (lowerCasePath.endsWith(".csv")) {
            return "text/csv";
        } else if (lowerCasePath.endsWith(".tar")) {
            return "application/x-tar";
        } else if (lowerCasePath.endsWith(".gz")) {
            return "application/gzip";
        } else if (lowerCasePath.endsWith(".mpg") || lowerCasePath.endsWith(".mpeg")) {
            return "video/mpeg";
        } else if (lowerCasePath.endsWith(".m4a")) {
            return "audio/mp4";
        } else if (lowerCasePath.endsWith(".aac")) {
            return "audio/aac";
        } else if (lowerCasePath.endsWith(".ico")) {
            return "image/x-icon";
        } else {
            return "application/octet-stream"; // Default MIME type
        }
    }
    
	public WebRequest(WebInterface i, Socket s)
	{
		winterface = i;
		socket = s;

		Thread t = new Thread( this );
		t.setDaemon( true );
		t.start();
	}
	
	public void run()
	{
		try
		{
			if(r==null)
			{
				InputStream is = socket.getInputStream();
				if ( is == null) return;
				in = is;
				
				// Read the request line
				String inLine = TextImportExport.readline(in);
				if (inLine == null) return;
				StringTokenizer st = new StringTokenizer( inLine );
				if ( !st.hasMoreTokens())
					sendError( HTTP_BADREQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html" );
	
				String method = st.nextToken();
	
				if ( !st.hasMoreTokens())
					sendError( HTTP_BADREQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html" );
	
				String uri = st.nextToken();
	
				// Decode parameters from the URI
				int qmi = uri.indexOf( '?' );
				if ( qmi >= 0 )
				{
					decodeParms( uri.substring( qmi+1 ), parms );
					uri = URLSafe.decodePercent( uri.substring( 0, qmi ));
				}
				else uri = URLSafe.decodePercent(uri);
	
				path = uri.substring(1);
				//can have hidden params
	
				// If there's another token, it's protocol version,
				// followed by HTTP headers. Ignore version but parse headers.
				// NOTE: this now forces header names uppercase since they are
				// case insensitive and vary by client.
				HashMap<String, String> header = new HashMap<String, String>();
				if ( st.hasMoreTokens())
				{
					String line = TextImportExport.readline(in);
					while ( line.trim().length() > 0 )
					{
						int p = line.indexOf( ':' );
						header.put( line.substring(0,p).trim().toLowerCase(), line.substring(p+1).trim());
						line = TextImportExport.readline(in);
					}
				}
	
				// If the method is POST, there may be parameters
				// in data section, too, read it:
				if ( method.equalsIgnoreCase( "POST" ))
				{
					long size = 0x7FFFFFFFFFFFFFFFl;
					String contentLength = header.get("content-length");
					if (contentLength != null)
					{
						try { size = Integer.parseInt(contentLength); }
						catch (NumberFormatException ex) {}
					}
					int maxSize = 512*1024;
					if(size>maxSize)
					{
						System.out.println("Max upload file size exceeded");
						size = maxSize;
					}
	
					String contentType = header.get("content-type");
					
					if(contentType.startsWith("multipart/form-data;"))
					{						
						System.out.println("multipart/form-data;");
						parms.put("multipart", null);
					}
					else
					if(contentType.startsWith("application/octet-stream;"))
					{
						System.out.println("application/octet-stream;");
						//Don't parse as parameters
					}
					else
					{
						String postLine = "";
						byte buf[] = new byte[(int)size];
						int read = in.read(buf);
						while ( read >= 0 && size > 0 && !postLine.endsWith("\r\n") )
						{
							size -= read;
							postLine += new String(buf, "UTF-8");//String.valueOf(buf, 0, read);
							if ( size > 0 )
								read = in.read(buf);
						}
						postLine = postLine.trim();
						decodeParms( postLine, parms );
					}
				}
				winterface.queue(this);
			}
			else
			{
				// Ok, now do the serve()
				//Response r = serve( uri, method, header, parms, in, socket );
				if ( r == null )
				{
					//sendError( HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: Serve() returned a null response." );
				}
				else
					sendResponse( r.status, r.location, r.mimeType, r.header, r.data, r.datacallback, r.range, r.appendTerminationInt );
				in.close();
			}
		}
		catch ( IOException ioe )
		{
			try
			{
				sendError( HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
			}
			catch ( Throwable t ) {}
		}
		catch ( InterruptedException e )
		{
			// Thrown by sendError, ignore and exit the thread.
			System.out.println(e.toString());
		}
		catch ( Exception e )
		{
			System.out.println(e.toString());
		}
	}


	/**
	 * Decodes parameters in percent-encoded URI-format
	 * ( e.g. "name=Jack%20Daniels&pass=Single%20Malt" ) and
	 * adds them to given Parameters. NOTE: this doesn't support multiple
	 * identical keys due to the simplicity of Parameters -- if you need multiples,
	 * you might want to replace the Parameters with a Hastable of Vectors or such.
	 */
	private void decodeParms( String parms, HashMap<String, String> p )
		throws InterruptedException
	{
		if ( parms == null )
			return;

		StringTokenizer st = new StringTokenizer( parms, "&" );
		while ( st.hasMoreTokens())
		{
			String e = st.nextToken();
			int sep = e.indexOf( '=' );
			if ( sep >= 0 )
				p.put( URLSafe.decodePercent( e.substring( 0, sep )).trim(),
					   URLSafe.decodePercent( e.substring( sep+1 )));
		}
	}

	public void saveMultipart(String to)
	{
		//This code is commented out as it won't handle all cases properly
		//Some browsers also send multipart data in different ways
		/*
		String boundary = TextImportExport.readline(in);
		if((boundary!=null)&&(boundary.length()>3))
		{
			boundary = boundary.substring(2);
			
			try
			{
				MultipartStream multipartStream = new MultipartStream(in, boundary.getBytes());
				boolean nextPart = true;//multipartStream.skipPreamble();
				 while(nextPart) 
				 {
					 String header = multipartStream.readHeaders();
					 int ind = header.indexOf("filename=");
					 if(ind!=-1)
					 {
						 String rest = header.substring(ind+"filename=\"".length());
						 ind = rest.indexOf("\"");
						 String filename = rest.substring(0,ind);
						 // process headers
						 OutputStream output = new FileOutputStream(to+filename);
						 // create some output stream
						 multipartStream.readBodyData(output);
					 }
					 else
					 {
						 multipartStream.discardBodyData();						 
					 }
					 nextPart = multipartStream.readBoundary();
				 }
			 } 
			catch(Exception e) 
			 {
				System.out.println(e.toString());
			 // the stream failed to follow required syntax
			 }
		}
		// */
	}

	
	/**
	 * Returns an error message as a HTTP response and
	 * throws InterruptedException to stop further request processing.
	 */
	private void sendError( String status, String msg ) throws InterruptedException
	{
		sendResponse( status, null, MIME_PLAINTEXT, null, new ByteArrayInputStream( msg.getBytes() ), null, -1, false);
		throw new InterruptedException();
	}

	/**
	 * Sends given response to the socket.
	 */
	private void sendResponse( String status, String location, String mime, HashMap<String, String> header, InputStream data, OutputStreamCallback datacallback, long range, boolean appendBufferInt )
	{
		try
		{
			if ( status == null )
				throw new Error( "sendResponse(): Status can't be null." );

			//System.out.println(status+" "+mime);
			OutputStream out = socket.getOutputStream();
			PrintWriter pw = new PrintWriter( out );
			pw.print("HTTP/1.0 " + status + " \r\n");

			if ( location != null )
				pw.print("Location: " + location + "\r\n");

			if ( mime != null )
				pw.print("Content-Type: " + mime + "\r\n");

			if ( header == null || header.get( "Date" ) == null )
				pw.print( "Date: " + gmtFrmt.format( new Date()) + "\r\n");

			if ( header != null )
			{
				for (Map.Entry<String, String> entry : header.entrySet()) {
		            String key = entry.getKey();
		            String value = entry.getValue();
		            System.out.println("Key=" + key + ", Value=" + value);
					pw.print( key + ": " + value + "\r\n");
		        }
			}

			pw.print("\r\n");
			pw.flush();

			if( datacallback != null)
			{
				datacallback.out(out);
			}
			else
			if ( data != null )
			{
				byte[] buff = new byte[2048];
				if(range==-1)
				{
					while (true)
					{
						int read = data.read( buff, 0, 2048 );
						if (read <= 0)
							break;
						out.write( buff, 0, read );
					}
				}
				else
				{
					long rem = range;
					while (true)
					{
						int read = data.read( buff, 0, 2048 );
						if (read <= 0)
							break;
						if(rem>=read)
						{
							out.write( buff, 0, read );
							rem -= read;
						}
						else
						{
							out.write( buff, 0, (int)rem );
							break;
						}
					}
					
				}
			}

			if(appendBufferInt)
			{
				//Buffer element so doesn't terminate weirdly
		    	ByteBufferReaderWriter.writeint(out, 0);
			}
							
			out.flush();
			out.close();
			if ( data != null )
				data.close();
			
			socket.close();
			//System.out.println(path + " chunk sent");
		}
		catch( IOException ioe )
		{
			// Couldn't write? No can do.
			try { socket.close(); } catch( Throwable t ) {}
		}
	}
}
