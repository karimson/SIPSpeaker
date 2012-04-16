
public class SIPModel
{
	String invite;
	String via;
	String from;
	String to;
	String callId;
	String contact;
	int cSeq;
	
	public SIPModel(String from, String to, String via, String contact, String callId, int cSeq)
	{
		this.from = from;
		this.to = to;
        this.via = via;
        this.contact = contact;
        this.callId = callId;
        this.cSeq = cSeq;
    }
}
