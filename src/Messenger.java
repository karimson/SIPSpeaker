
public class Messenger
{	
	public String ringMessage(SIPModel model)
	{
        return String.format(RING_FORMAT, model.via, model.from, model.to, model.callId, model.cSeq, model.contact);
	}
	
	private String RING_FORMAT = "SIP/2.0 180 Ringing\r\n"
            + "Via: SIP/2.0/UDP %s\r\n"
            + "From: %s\r\n"
            + "To: %s\r\n"
            + "Call-ID: %s\r\n"
            + "CSeq: %d INVITE\r\n"
            + "Contact: %s\r\n"
            + "Content-Length: 0\r\n\r\n";
	
	public String okMessage(SIPModel model)
	{
        String sdp = String.format(SDP, model.port); // mediaport
        return String.format(OK_FORMAT, model.via, model.from, model.to, model.callId, model.cSeq, sdp.length(), model.contact, sdp);
    }
	
    private final String SDP = "v=0\r\n"
            + "o=SIPspeaker 0 0 IN IP4 " + ApplicationProperties.SIP_HOST + "\r\n"
            + "s=SIPspeaker \r\n"
            + "c=IN IP4 " + ApplicationProperties.SIP_HOST + "\r\n"
            + "t=0 0\r\n"
            + "m=audio %d RTP/AVP 3\r\n"
            + "a=rtpmap:3 GSM/8000\r\n"
            + "a=sendrecv\r\n\r\n";
    private String OK_FORMAT = "SIP/2.0 200 OK\r\n"
            + "Via: SIP/2.0/UDP %s\r\n"
            + "From: %s\r\n"
            + "To: %s\r\n"
            + "Call-ID: %s\r\n"
            + "CSeq: %d INVITE\r\n"
            + "Content-Length: %d\r\n"
            + "Contact: %s\r\n"
            + "Content-Type: application/sdp\r\n"
            + "\r\n"
            + "%s";
    
    public String byeMessage(SIPModel model) 
    {
        return String.format(BYE_FORMAT, model.to, model.via, model.to, model.from, model.callId, model.cSeq, model.contact);
    }
    private String BYE_FORMAT = "BYE %s SIP/2.0\r\n"
            + "Via: SIP/2.0/UDP %s\r\n"
            + "From: %s\r\n"
            + "To: %s\r\n"
            + "Call-ID: %s\r\n"
            + "CSeq: %d BYE\r\n"
            + "Contact: %s\r\n"
            + "Content-Length: 0\r\n";
}
