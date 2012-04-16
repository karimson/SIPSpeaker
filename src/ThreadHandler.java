import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ThreadHandler extends Thread
{
	private Socket socket = null;

public ThreadHandler(Socket inputSocket)
{
	this.socket = inputSocket;
}

public void run()
{
	RequestResponseHandler rrh = new RequestResponseHandler();
	PrintWriter out = null;
	BufferedReader in = null;
	HTTPModel httpModel;

	try 
	{
		out = new PrintWriter(socket.getOutputStream());
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		httpModel = new HTTPModel();
		
		char[] inBuff = new char[10000];
		int charsRead = 0;
		String inputLine = "";  

		while (in.ready())
		{
			charsRead = in.read(inBuff, 0, 10000);
			inputLine += String.valueOf(inBuff,0, charsRead); 
		}
		
		if (charsRead != 0)
		{
			String processedLine;
			int inLineLength = inputLine.split("(\r\n|\r|\n)").length;
			for (int i = 0; i < inLineLength; i++)
			{
				processedLine = inputLine.split("(\r\n|\r|\n)")[i];
				if (processedLine.equals(""))
				{
					continue;
				}
				rrh.processRequest(processedLine, httpModel);
			}
			                   
			if (httpModel.type.equals("GET") || httpModel.type.equals("POST"))
			{
                                if(httpModel.path.equals("/") || httpModel.path.equals("/status"))
                                {
                                    if(httpModel.type.equals("POST") && !httpModel.path.equals("/status"))
                                    {
                                    	
                                    }
                                    rrh.processOutput(httpModel, out);
                                }
                                else
                                {
                                    out.write(rrh.get404());
                                }
			}
			else
			{
				out.write("HTTP command not supported");
			}
			
			out.close();
			in.close();
			socket.close();
		}	
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}
	
}

}
