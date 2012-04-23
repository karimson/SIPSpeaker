import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;


public class SipServer
{
	Map<Integer, String> callRegister = new HashMap<Integer, String>();
	Map<Integer, Integer> portRegister = new HashMap<Integer, Integer>();
    public void startSipServer() throws InterruptedException, SocketException, Exception
    {
    	DatagramSocket serverSocket = null;
    	
        try
        {
			serverSocket = new DatagramSocket(ApplicationProperties.SIP_PORT);
		} 
        catch (SocketException e1)
        {
			e1.printStackTrace();
		}
	        
        while (true)
        {
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try 
            {
            	System.out.println("Waiting for packet..");
				serverSocket.receive(packet);
				System.out.println("Packet recieved..");
			} 
            catch (IOException e)
			{
				e.printStackTrace();
			}
            
            CallHandler ch = new CallHandler(serverSocket, packet, this);
            ch.start();           
        }
    }
    
    public synchronized void removeCall(int callId)
    {
    	callRegister.remove(callId);
    	portRegister.remove(callId);
    }
    
    public synchronized void addCall(int callId, int port)
    {
    	callRegister.put(new Integer(callId), "RINGING NOT SENT");
    	portRegister.put(new Integer(callId), new Integer(port));
    }
    
    public synchronized boolean callExists(int callId)
    {
    	return callRegister.containsKey(callId);
    }
    
    public synchronized void setState(int callId, String state)
    {
    	callRegister.put(callId, state);
    }
    
    public synchronized String getState(int callId)
    {
    	return callRegister.get(callId);
    }
    
    public synchronized Integer getPort(int callId)
    {
    	return portRegister.get(callId);
    }
}
