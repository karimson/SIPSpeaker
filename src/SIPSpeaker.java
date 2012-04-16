import java.net.SocketException;


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

}
