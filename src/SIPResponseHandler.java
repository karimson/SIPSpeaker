


public class SIPResponseHandler {
	Messenger messenger = new Messenger();
	SIPModel sipModel = new SIPModel();
	
	public SIPModel processRequest(String request) 
	{
		System.out.println(request);
		String[] messageLines = request.split("\n");
		
		for (int i=0; i<messageLines.length; i++)
		{
			if(messageLines[i].startsWith("OPTIONS"))
			{
				sipModel.type = "OPTIONS";
			}
			if(messageLines[i].startsWith("INVITE"))
			{
				sipModel.type = "INVITE";
				sipModel.invite = messageLines[i].split(" ")[1].trim();
			}
			else if(messageLines[i].startsWith("BYE"))
			{
				sipModel.type = "BYE";
			}
			else if(messageLines[i].startsWith("ACK"))
			{
				sipModel.type = "ACK";
			}
			else if(messageLines[i].startsWith("Via:"))
			{
				sipModel.via = messageLines[i].split(" ")[2].trim();
				
				System.out.println("SIPMODELVIA: " + sipModel.via);
			}
			else if(messageLines[i].startsWith("From:"))
			{
				sipModel.from = messageLines[i].split(" ")[1].trim(); //sip:alice...
			}
			else if(messageLines[i].startsWith("To:"))
			{
				sipModel.to = messageLines[i].split(" ")[1].trim(); //<> kvar, samma som from
			}
			else if(messageLines[i].startsWith("Call-ID:"))
			{
				sipModel.callId = messageLines[i].split(" ")[1].trim();
			}
			else if(messageLines[i].startsWith("CSeq:"))
			{
				sipModel.cSeq = Integer.parseInt(messageLines[i].split(" ")[1].trim());
			}
	    }
		
		return sipModel;
	}

}
