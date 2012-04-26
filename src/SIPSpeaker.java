import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class SIPSpeaker 
{
	public static void main(String [] args) throws SocketException, InterruptedException, Exception
	{
		// java SIPSpeaker [-c config_file_name] [-user sip_uri] [-http http_bind_address]
		for(int i=0; i<args.length; i++)
		{
			if(args[i].contains("-c"))
			{
				ApplicationProperties.filePath = args[i].split(" ")[1];
			}
		}
		
		ApplicationProperties.loadProperties();
		
		for(int i=0; i<args.length; i++)
		{
			if(args[i].contains("-user"))
				ApplicationProperties.updateUser(args[i].split(" ")[1]);
			if(args[i].contains("-http"))
				ApplicationProperties.updateHttp(args[i].split(" ")[1]);	
		}
		
		
		VoiceHandler vh = new VoiceHandler();
		vh.setMessage(ApplicationProperties.DEFAULT_MESSAGE);
		
  		WebServer web = new WebServer();
		web.startWebServer(ApplicationProperties.HTTP_PORT);
		SipServer sip = new SipServer();
		sip.startSipServer();
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


