


public class SIPResponseHandler {
	Messenger messenger = new Messenger();
	SIPModel sipModel = new SIPModel();
	
	public SIPModel processRequest(String request) 
	{
		String[] messageLines = request.split("\n");
		
		for (int i=0; i<messageLines.length; i++)
		{
			if(messageLines[i].startsWith("OPTIONS"))
			{
				sipModel.type = "OPTIONS";
			}
			if(messageLines[i].startsWith("INVITE"))
			{
				System.out.println("INVITE recieved");
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
			else if(messageLines[i].startsWith("CANCEL"))
			{
				sipModel.type = "CANCEL";
			}
			else if(messageLines[i].startsWith("Via:"))
			{
				sipModel.via = messageLines[i].split(" ")[2].trim().replace("rport", "rport=5060");
			}
			else if(messageLines[i].startsWith("From:"))
			{
				sipModel.from = messageLines[i].split(" ")[1].trim();
				sipModel.fromIp = sipModel.from.split("@")[1].split(">")[0];
			}
			else if(messageLines[i].startsWith("To:"))
			{
				sipModel.to = messageLines[i].split(" ")[1].trim() + "tag=1877284501"; 
			}
			else if(messageLines[i].startsWith("Call-ID:"))
			{
				sipModel.callId = Integer.parseInt(messageLines[i].split(" ")[1].trim());
			}
			else if(messageLines[i].startsWith("CSeq:"))
			{
				sipModel.cSeq = Integer.parseInt(messageLines[i].split(" ")[1].trim());
			}
			else if(messageLines[i].startsWith("m=audio"))
			{
				sipModel.port = Integer.parseInt(messageLines[i].split(" ")[1].trim());
			}
	    }
		
		return sipModel;
	}

}
