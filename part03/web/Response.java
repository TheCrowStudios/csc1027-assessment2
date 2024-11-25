package web;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import importexport.OutputStreamCallback;

/**
 * HTTP response.
 * Return one of these from serve().
 */
public class Response 
{
	//To deal with nastiness with a file being terminated
	public boolean appendTerminationInt = false;
	
	/**
	 * HTTP status code after processing, e.g. "200 OK", HTTP_OK
	 */
	public String status;

	/**
	 * MIME type of content, e.g. "text/html"
	 */
	public String mimeType;

	public String location = null;
	
	/**
	 * Data of the response, may be null.
	 */
	public InputStream data;
	public long range = -1;
	public OutputStreamCallback datacallback = null;
	
	/**
	 * Headers for the HTTP response. Use addHeader()
	 * to add lines.
	 */
	public HashMap<String, String> header = new HashMap<String, String>();
	
	/**
	 * Default constructor: response = HTTP_OK, data = mime = 'null'
	 */
	public Response()
	{
		this.status = WebRequest.HTTP_OK;
	}

	/**
	 * Basic constructor.
	 */
	public Response( String status, String mimeType, long rng, InputStream data )
	{
		this.status = status;
		this.mimeType = mimeType;
		this.data = data;
		this.range = rng;
	}

	public Response( String status, String mimeType, OutputStreamCallback data )
	{
		this.status = status;
		this.mimeType = mimeType;
		this.datacallback = data;
	}
	
	/**
	 * Convenience method that makes an InputStream out of
	 * given text.
	 */
	public Response( String status, String location, String mimeType, String txt )
	{
		this.status = status;
		this.location = location;
		this.mimeType = mimeType;
		this.data = new ByteArrayInputStream(txt.getBytes());
	}

	/**
	 * Convenience method that makes an InputStream out of
	 * given text.
	 */
	public Response( String status, String mimeType, String txt )
	{
		this.status = status;
		this.mimeType = mimeType;
		this.data = new ByteArrayInputStream(txt.getBytes());
	}

	/**
	 * Adds given line to the header.
	 */
	public void addHeader( String name, String value )
	{
		header.put( name, value );
	}



}
