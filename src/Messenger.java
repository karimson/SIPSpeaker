
public class Messenger
{
	public String ringMessage(SIPModel model)
	{
        return String.format(RING_FORMAT, model.via, model.from, model.to, model.callId, model.cSeq, model.contact);
	}
	
	private String RING_FORMAT = "SIP/2.0 180 Ringing\r\n"
            + "Via: %s\r\n"
            + "From: %s\r\n"
            + "To: %s\r\n"
            + "Call-ID: %s\r\n"
            + "CSeq: %d INVITE\r\n"
            + "Contact: %s\r\n"
            + "Content-Length: 0\r\n\r\n";
	
	public String okMessage(SIPModel model) {
        String sdp = String.format(SDP, 7079); // mediaport
        return String.format(OK_FORMAT,
                model.via,
                sdp.length(),
                model.contact,
                model.callId,
                model.cSeq,
                model.from,
                model.to,
                sdp);
    }
	
    private final String SDP = "v=0\r\n"
            + "o=SIP_SPEAKER_SESSION 0 0 IN IP4 " + "127.0.0.1" + "\r\n"
            + "s=SIP SPEAKER V 1.0\r\n"
            + "c=IN IP4 " + "127.0.0.1" + "\r\n"
            + "t=0 0\r\n"
            + "m=audio %d RTP/AVP 0\r\n"
            + "a=rtpmap:0 ULAW/8000\r\n"
            + "a=sendonly\r\n\r\n";
    private String OK_FORMAT = "SIP/2.0 200 OK\r\n"
            + "Via: %s\r\n"
            + "Content-Length: %d\r\n"
            + "Contact: %s\r\n"
            + "Call-ID: %s\r\n"
            + "Content-Type: application/sdp\r\n"
            + "CSeq: %d INVITE\r\n"
            + "From: %s\r\n"
            + "To: %s\r\n"
            + "\r\n"
            + "%s";

}
