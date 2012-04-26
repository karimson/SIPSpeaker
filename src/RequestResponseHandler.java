import java.io.*;


public class RequestResponseHandler
{	
	public void processRequest(String request, HTTPModel httpModel)
	{
	
		if(request.startsWith("GET"))
		{
			httpModel.type = "GET";
			httpModel.path = request.split(" ")[1].trim();
		}
		else if(request.startsWith("POST"))
		{
			httpModel.type = "POST";
			httpModel.path = request.split(" ")[1].trim();
		}
		else if (request.startsWith("Connection:")) 
		{
			httpModel.connection = request.substring(12).trim();
		}
		else if (request.startsWith("User-Agent:")) 
		{
			httpModel.userAgent = request.substring(12).trim();
		}
		else if(request.startsWith("Host:"))
		{
			httpModel.host = request.split(" ")[1];
		}
		else if (request.startsWith("Accept:")) 
		{
			httpModel.accept = request.substring(7).trim();
		}
		else if (request.startsWith("Accept-Encoding:")) 
		{
			httpModel.acceptEncoding = request.substring(16).trim();
		}
		else if (request.startsWith("Accept-Language:")) 
		{
			httpModel.acceptLanguage = request.substring(16).trim();
		}
		else if (request.startsWith("Accept-Charset:")) 
		{
			httpModel.acceptCharset = request.substring(15).trim();
		} 	
		else if(httpModel.type.equals("POST") && request.startsWith("MESSAGE"))
		{
			httpModel.data = request.split("=")[1].split("&")[0];
		}
		
	}

	public void processOutput(HTTPModel httpModel, PrintWriter out) 
	{
		if(httpModel.type.equals("POST"))
        {
            if(httpModel.path.contains("current"))
            {
               out.write(ApplicationProperties.getMessage());
            }   
            else if(httpModel.path.contains("delete"))
            {
            	System.out.println("hej hej hejehejeheje");
            	out.write("Current message deleted and set to default");
            	ApplicationProperties.setDefaultMessage();
            }
            else
            {
               out.write("Message changed.");
            }
        }
        else
        {
            if(httpModel.path.contains("current"))
            {
               out.write(ApplicationProperties.getMessage());
            }
            else if(httpModel.path.contains("delete"))
            {
            	System.out.println("hej hej hejehejeheje");
            	out.write("Current message deleted and set to default");
            	ApplicationProperties.setDefaultMessage();
            }
            else
            {
               out.write(getPage("sipspeaker.html"));
            }
        }
	}
	
        
    public String getPage(String fileName)
    {
        BufferedReader in = null;
        String data = "";
        String inputLine = "";
        String response = "";
        
        try 
        {
        	in = new BufferedReader(new FileReader(fileName));
        }
        catch (FileNotFoundException e)
        {
        	System.out.println("File not found");
        }
        try 
        {
        	while ((inputLine = in.readLine()) != null) 
        	{
                data += inputLine + "\r\n";
        	}
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        response = "HTTP/1.0 200 OK\r\n";
        response += "Content-Type: text/html\r\n";
        response += "Content-Length: " + data.length() + "\r\n";
        response += "Connection: close\r\n";
        response += "\r\n";
        response += data;
        
        return response;
    }
    
    public String get404()
    {
        String response = "HTTP/1.0 404 Not Found\r\n";
        response += "\r\n";
        
        return response;
    }

	public String getMessage(HTTPModel httpModel) 
	{
		return httpModel.data;
	}
	
}
