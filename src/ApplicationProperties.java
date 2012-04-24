import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ApplicationProperties
{
	public static String DEFAULT_MESSAGE;
	public static String CURRENT_MESSAGE;
    public static int SIP_PORT;
    public static String SIP_USER;
    public static String SIP_HOST;
    public static String HTTP_INTERFACE;
    public static String HTTP_PORT;
    public static String MESSAGE_WAV;
    public static String filePath = "sipspeaker.cfg";

	public static void loadProperties()
	{
		Properties prop = new Properties();
		try
		{
			prop.load(new FileInputStream(filePath));
			DEFAULT_MESSAGE = prop.getProperty("default_message");
			CURRENT_MESSAGE = DEFAULT_MESSAGE;
			SIP_PORT = Integer.parseInt(prop.getProperty("sip_port"));
			SIP_USER = prop.getProperty("sip_user");
            HTTP_INTERFACE = prop.getProperty("http_interface");
            HTTP_PORT = prop.getProperty("http_port");
            MESSAGE_WAV = prop.getProperty("message_wav");
            SIP_HOST = prop.getProperty("sip_user");
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void updateUser(String sip_uri) 
	{
		SIP_USER = sip_uri.split("@")[0];
		SIP_HOST = sip_uri.split("@")[1].split(":")[0];
		SIP_PORT = Integer.parseInt(sip_uri.split("@")[1].split(":")[1]);
	}

	public static void updateHttp(String bindAdress) 
	{
		if(bindAdress.split(":").length == 0)
		{
			if(bindAdress.split(".").length < 0)
				HTTP_PORT = bindAdress;
			else
				HTTP_INTERFACE = bindAdress;
		}
		else
		{
			HTTP_INTERFACE = bindAdress.split(":")[0];
			HTTP_PORT = bindAdress.split(":")[1];
		}
	}
	
	public static void setMessage(String message)
	{
		CURRENT_MESSAGE = message;
		VoiceHandler vh = new VoiceHandler();
		vh.setMessage(ApplicationProperties.CURRENT_MESSAGE);
	}
	
	public static String getMessage()
	{
		return CURRENT_MESSAGE;
	}
	
	public static void setDefaultMessage()
	{
		CURRENT_MESSAGE = DEFAULT_MESSAGE;
		VoiceHandler vh = new VoiceHandler();
		vh.setMessage(ApplicationProperties.CURRENT_MESSAGE);
	}

	
}
