
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

}
