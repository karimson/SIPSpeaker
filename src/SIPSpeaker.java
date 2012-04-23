import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class SIPSpeaker 
{
	private static int sipPort;
	private static int httpPort;
	static int port;
	
	public static void main(String [] args) throws SocketException, InterruptedException, Exception
	{
		// java SIPSpeaker [-c config_file_name] [-user sip_uri] [-http http_bind_address]
		if (args[0].contains("-c"))
		{
			if (args[1].contains("-user"))
			{
				if (args[2].contains("-http"))
				{
					ApplicationProperties.updateProperties(args[0], args[1], args[2]);
				}
			}
		}
		
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


