import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class SipServer
{
	
    public void startSipServer(int port) throws InterruptedException, SocketException, Exception
    {

    	DatagramSocket serverSocket = null;

        try
        {
			serverSocket = new DatagramSocket(5070);
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
           
            CallHandler ch = new CallHandler(serverSocket, packet);
            ch.run();
        }
    }
}
