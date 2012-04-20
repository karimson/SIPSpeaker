import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;


public class SIPSpeaker 
{
	private static int sipPort;
	private static int httpPort;
	static int port;
	
	public static void main(String [] args) throws SocketException, InterruptedException, Exception
	{
		/*Properties prop = new Properties();
		InputStream is = new FileInputStream("sipspeaker.cfg");

	    prop.load(is);
	    
	    prop.getProperty("sip_port", "5070");
	    
		for(int i=0; i<args.length; i++)
		{
			if(args[i].contains("-c"))
			{
				is = new FileInputStream(args[i]);
				prop.load(is);
			}
			if(args[i].contains("-user"))
			{
				
			}
			if(args[i].contains("-http"))
			{

			}
		}*/
		
		VoiceHandler vh = new VoiceHandler();
		vh.setMessage("Hej Niklas wanna do business");
		
  		WebServer web = new WebServer();
		//web.startWebServer(httpPort);
		web.startWebServer(8080);
		SipServer sip = new SipServer();
		sip.startSipServer(5070);
	//	sip.startSipServer(sipPort);
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

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		SIPSpeaker.port = port;
	}
}


