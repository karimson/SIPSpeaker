import java.util.Enumeration;
import java.util.Vector;


public class SIPModel
{
	String type;
	String invite;
	Vector<String> via = new Vector<String>();
	String from;
	String fromIp;
	String to;
	int callId;
	String contact;
	int cSeq;
	int port;
	String stringPort;
	String requestedUser;

	public String getVia()
	{
		String message = "";
		Enumeration<String> vEnum = via.elements();
		while(vEnum.hasMoreElements())
		{
			message += "Via: SIP/2.0/UDP "+vEnum.nextElement()+"\r\n";
		}
		return message;
	}
}

