public class SIPResponseHandler
{
	SIPModel sipModel = new SIPModel();
	
	public SIPModel processRequest(String request) 
	{
		String[] messageLines = request.split("\n");
		sipModel.contact = "<sip:"+ApplicationProperties.SIP_USER+"@"+ApplicationProperties.SIP_HOST+":"+ApplicationProperties.SIP_PORT+">";
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
				sipModel.via = messageLines[i].split(" ")[2].trim().replace("rport", "rport="+ApplicationProperties.SIP_PORT);
			}
			else if(messageLines[i].startsWith("From:"))
			{
				sipModel.from = messageLines[i].split(" ")[1].trim();
				sipModel.fromIp = sipModel.from.split("@")[1].split(">")[0];
			}
			else if(messageLines[i].startsWith("To:"))
			{
				sipModel.to = messageLines[i].split(" ")[1].trim() + "tag=1877284501"; 
				sipModel.requestedUser = messageLines[i].split(":")[1].split("@")[0];
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
				if (!sipModel.type.equals("ACK"))
				{
					sipModel.stringPort = messageLines[i].split(" ")[1].trim();
					sipModel.port = Integer.parseInt(sipModel.stringPort);
				}
				
			}
	    }
		
		return sipModel;
	}

}
