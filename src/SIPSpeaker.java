import java.io.UnsupportedEncodingException;


public class SIPSpeaker 
{
	public static void main(String [] args) throws UnsupportedEncodingException
	{
		WebServer web = new WebServer();
		web.startWebServer(80);
		
		SipServer sip = new SipServer();
		sip.startSipServer(5060);
	}

}
