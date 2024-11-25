package importexport;

import java.io.IOException;
import java.io.OutputStream;

public class ByteBufferReaderWriter 
{
	final public static int writeint(OutputStream output,int value) throws IOException
	{
		output.write((int)((value&0xFF000000)>>>24));
		output.write((int)((value&0x00FF0000)>>>16));
		output.write((int)((value&0x0000FF00)>>>8));
		output.write((int)((value&0x000000FF)>>>0));
		return 8;
	}
}
