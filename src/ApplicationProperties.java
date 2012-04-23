import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ApplicationProperties
{
	public static String DEFAULT_MESSAGE;
    public static int SIP_PORT;
    public static String SIP_USER;
    public static String SIP_HOST;
    public static String HTTP_INTERFACE;
    public static String HTTP_PORT;
    public static String MESSAGE_WAV;

	public static void updateProperties(String filePath, String user, String http)
	{
		Properties prop = new Properties();
		try
		{
			prop.load(new FileInputStream(filePath));
			DEFAULT_MESSAGE = prop.getProperty("default_message");
			
			SIP_PORT = Integer.parseInt(prop.getProperty("sip_port"));
			SIP_USER = prop.getProperty("sip_user");
            HTTP_INTERFACE = prop.getProperty("http_interface");
            HTTP_PORT = prop.getProperty("http_port");
            MESSAGE_WAV = prop.getProperty("message_wav");
            SIP_HOST = user;
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

}
