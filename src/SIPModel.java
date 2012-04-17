
public class SIPModel
{
	String type;
	String invite;
	String via;
	String from;
	String fromIp;
	String to;
	int callId;
	String contact = "<sip:server@"+SIPSpeaker.getLocalIP()+":5070>";
	int cSeq;
	//String ownIp = SIPSpeaker.getLocalIP();
	int port;
	
}
