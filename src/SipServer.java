import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SipServer 
{
	private static boolean listen;
	
	public void startSipServer(int port)
	{
		listen = true;
		ServerSocket serverSocket = null;
		try
		{
			serverSocket = new ServerSocket(port); 
		}
		catch(IOException e)
		{
			System.out.println("Error listening to port.");
			System.exit(0);
		}
                
		System.out.println("Sip server started, waiting for incoming connections.");
                               
		while(listen)
		{
			try 
            {
				Socket clientSocket = serverSocket.accept();
				CallHandler handler = new CallHandler(clientSocket);
				handler.start();
			} 
            catch (IOException e) 
            {
				e.printStackTrace();
			} 
		}
		System.out.println("Server is now dead");
	}
	
	public static void kill()
	{
		listen = false;
	}
	
}
