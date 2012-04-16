import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;


public class CallHandler extends Thread
{
	private DatagramSocket socket;
	private DatagramPacket packet;
	private SocketAddress address;
	SIPModel sipModel;
	SIPResponseHandler srh = new SIPResponseHandler();
	private String state;
	Messenger messenger = new Messenger();

public CallHandler(DatagramSocket serverSocket, DatagramPacket packet)
{
	this.socket = serverSocket;
	this.packet = packet;
	this.address = packet.getSocketAddress();
	this.state = "INVITE";
}

public void run()
{
	String message = new String(packet.getData());
    sipModel = srh.processRequest(message);
    
    if(sipModel.type.toUpperCase().equals("INVITE") && state.equals("INVITE"))
    {
    	sipModel.contact = "<sip:server@127.0.0.1>";
    	byte[] ringMessageInBytes = messenger.ringMessage(sipModel).getBytes();
    	System.out.println("HEJ: " + messenger.ringMessage(sipModel));
		sipSend(ringMessageInBytes);
		System.out.println("Sent ringing message..");
		
		byte[] okMessageInBytes = messenger.okMessage(sipModel).getBytes();
		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		sipSend(okMessageInBytes);
		System.out.println("Sent ok message..");
    }
   /* else if(sipModel.type.toUpperCase().equals("BYE"))
    {
    	
    }
    else if(sipModel.type.toUpperCase().equals("OK"))
    {
    	
    }
    else
    {
    
    }*/
}

public void sipSend(byte[] messageInBytes) 
{
	DatagramPacket packet;
	
	try {
		packet = new DatagramPacket(messageInBytes, messageInBytes.length, address);
		socket.send(packet);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

}
