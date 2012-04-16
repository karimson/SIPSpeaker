
public class SIPModel
{
	String type;
	String invite;
	String via;
	String from;
	String to;
	String callId;
	String contact = "<sip:server@"+SIPSpeaker.getLocalIP()+":5070>";
	int cSeq;
	String IP = SIPSpeaker.getLocalIP();
	int port;
	
}
