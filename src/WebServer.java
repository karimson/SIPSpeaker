import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;


public class WebServer extends Thread
{
	private static boolean listen;
	ServerSocket serverSocket = null;
	String currentMessage = "";
	
	public void startWebServer(int port) throws UnsupportedEncodingException
	{
		listen = true;
		
		try
		{
			serverSocket = new ServerSocket(port); 
		}
		catch(IOException e)
		{
			System.out.println("Error listening to port.");
			System.exit(0);
		}
		
		this.start();

	}
	
	public void run()
	{
		System.out.println("Web server started, waiting for incoming connections.");
		
		while(listen)
		{
			try 
			{
				Socket clientSocket = serverSocket.accept();
				ThreadHandler handler = new ThreadHandler(clientSocket);
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
