
public class SIPModel
{
	String type;
	String invite;
	String via;
	String from;
	String fromIp;
	String to;
	String callId;
	String contact = "<sip:server@"+SIPSpeaker.getLocalIP()+":5070>";
	int cSeq;
	String ownIp = SIPSpeaker.getLocalIP();
	int port;
	
}
