
public class SIPResponseHandler {

	public void processRequest(String request, SIPModel sipModel) 
	{
		if(request.startsWith("INVITE"))
		{
			sipModel.invite = request.split(" ")[1].trim();
		}
		else if(request.startsWith("Via:"))
		{
			sipModel.via = request.split(" ")[2].trim();
		}
		else if(request.startsWith("From:"))
		{
			sipModel.from = request.split(" ")[1].trim(); //sip:alice...
		}
		else if(request.startsWith("To:"))
		{
			sipModel.to = request.split(" ")[1].trim(); //<> kvar, samma som from
		}
		else if(request.startsWith("Call-ID:"))
		{
			sipModel.callId = request.split(" ")[1].trim();
		}
		else if(request.startsWith("CSeq:"))
		{
			sipModel.cSeq = Integer.parseInt(request.split(" ")[1].trim());
		}
	}

}
