import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.URL;

import javax.media.MediaLocator;


public class CallHandler extends Thread
{
	private DatagramSocket socket;
	private DatagramPacket packet;
	private SocketAddress address;
	private SipServer server;
	private SIPModel sipModel;
	private SIPResponseHandler srh = new SIPResponseHandler();
	private Messenger messenger = new Messenger();

public CallHandler(DatagramSocket serverSocket, DatagramPacket packet, SipServer sipServer)
{
	this.socket = serverSocket;
	this.packet = packet;
	this.address = packet.getSocketAddress();
	this.server = sipServer;
}

public void run()
{
	sipModel = srh.processRequest(new String(packet.getData()));
    if(sipModel.type.toUpperCase().equals("INVITE") && !server.callExists(sipModel.callId))
    {
    	server.addCall(sipModel.callId);
    	byte[] ringMessageInBytes = messenger.ringMessage(sipModel).getBytes();
    	
		sipSend(ringMessageInBytes);
		System.out.println("Sent ringing message..");
		
		try 
		{
			Thread.sleep(3000);
			byte[] okMessageInBytes = messenger.okMessage(sipModel).getBytes();
			sipSend(okMessageInBytes);
			System.out.println(new String(okMessageInBytes));
			System.out.println("Sent ok message..");
			server.setState(sipModel.callId, "OK SENT");
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

    }
    else if(sipModel.type.toUpperCase().equals("BYE") && server.callExists(sipModel.callId))
    {
    	server.removeCall(sipModel.callId);
    }
    else if(sipModel.type.toUpperCase().equals("ACK") && server.callExists(sipModel.callId))
    {
    	if(server.getState(sipModel.callId).equals("OK SENT"))
    	{
    		server.setState(sipModel.callId,"SENDING DATA");
    	
	    	try 
	    	{/*
	    		
	            RTPHandler rtph = new RTPHandler(sipModel.fromIp, portFromString);
	            rtph.send();
	            rtph.stop();
	            //Thread.currentThread().sleep(10000);
	            //sipMessage.updateVia(message);
	    		String portFromString = "" + sipModel.port; portFromString.trim();
	    		URL message = new File("message1.wav").toURI().toURL();
	    		AudioTransmit at = new AudioTransmit(new MediaLocator(message), sipModel.fromIp, sipModel.stringPort);
	    		String result = at.start();
	    		
	    		try {
	    		    Thread.currentThread().sleep(10000);
	    		} catch (InterruptedException ie) {
	    		}

	    		// Stop the transmission
	    		at.stop();
	    		*/
	    		AudioHandler ah = new AudioHandler(sipModel.fromIp, 7078);
	    		ah.startTransmitting();
	    		try {
	    		    Thread.currentThread().sleep(10000);
	    		} catch (InterruptedException ie) {
	    		}

	    		
	    		
	    		ah.stopTransmitting();
	    		
	    		sipSend(messenger.byeMessage(sipModel).getBytes());
	            System.out.println("Sent bye message..");
	            server.setState(sipModel.callId, "BYE SENT");
	    	}
	    	catch (Exception exp) 
			{
	            System.out.println(exp.getMessage());
	        }
	    	server.removeCall(sipModel.callId);
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
