import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class SIPSpeaker 
{
	private static int port;
	public static void main(String [] args) throws SocketException, InterruptedException, Exception
	{

  		WebServer web = new WebServer();
		web.startWebServer(8000);
		
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
	
	public static void setPort(int port)
	{
		SIPSpeaker.port = port;
	}
	public static int getPort()
	{
		return port;
	}
}


