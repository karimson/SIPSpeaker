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
		
		try 
		{
			Thread.sleep(5000);
		} 
		catch (InterruptedException e) 
		{
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
		}
    }
    else if(sipModel.type.toUpperCase().equals("BYE"))
    {
    	//kasta användaren;
    }
    else if(sipModel.type.toUpperCase().equals("ACK") && state.equals("OK SENT"))
    {
    	try {
            AudioHandler ah = new AudioHandler(sipModel.IP, sipModel.port);
            ah.startTransmitting();
            Thread.sleep(10000);
            //sipMessage.updateVia(message);
            sipSend(messenger.byeMessage(sipModel).getBytes());
            System.out.println("Sent bye message..");
    		state = "SENT BYE";
    		}
    		catch (Exception exp) 
    		{
                System.out.println(exp.getMessage());
            }
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
