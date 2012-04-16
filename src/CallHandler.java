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
    	byte[] ringMessageInBytes = messenger.ringMessage(sipModel).getBytes();
    	
		sipSend(ringMessageInBytes);
		System.out.println("Sent ringing message..");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		byte[] okMessageInBytes = messenger.okMessage(sipModel).getBytes();
		System.out.println("HEJ: " + messenger.okMessage(sipModel));
		sipSend(okMessageInBytes);
		System.out.println("Sent ok message..");
		
		state = "OK SENT";
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}//PLAY MESSAGE
		
		byte[] byeMessageInBytes = messenger.byeMessage(sipModel).getBytes();
		sipSend(byeMessageInBytes);
		System.out.println("Sent bye message..");
		state = "SENT BYE";
    }
    else if(sipModel.type.toUpperCase().equals("BYE"))
    {
    	
    }
    else
    {
    	
    }
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
