import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class SIPSpeaker 
{
	public static void main(String [] args) throws SocketException, InterruptedException, Exception
	{
/* GÖR EN TRÅDAD WEBSERVER!
 * 		WebServer web = new WebServer();
		web.startWebServer(80);
*/ 		
		SipServer sip = new SipServer();
		sip.startSipServer(5070);
	}
	
	public static String getLocalIP()
	{
		InetAddress addr = null;
		
		try 
		{
		    addr = InetAddress.getLocalHost();
		} 
		catch (UnknownHostException e) 
		{
		}
		return addr.getHostAddress();
	}
}


