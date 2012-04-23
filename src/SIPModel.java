
public class SIPModel
{
	String type;
	String invite;
	String via;
	String from;
	String fromIp;
	String to;
	int callId;
	String contact = "<sip:"+ApplicationProperties.SIP_HOST+"@"+ApplicationProperties.SIP_HOST+":"+ApplicationProperties.SIP_PORT+">";
	int cSeq;
	int port;
	String stringPort;
	
}
