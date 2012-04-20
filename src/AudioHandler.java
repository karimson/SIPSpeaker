import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.*;
import javax.media.format.*;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioHandler
{
	private MediaLocator mediaLocator = null;
    private DataSink dataSink = null;
    private Processor mediaProcessor = null;
	File audioFile;
    
    private static final Format[] FORMATS = new Format[] {new AudioFormat(AudioFormat.GSM_RTP)};
	private static final ContentDescriptor CONTENT_DESCRIPTOR = new ContentDescriptor(ContentDescriptor.RAW_RTP);
	
	public AudioHandler(String ip, int port) throws NoDataSourceException, MalformedURLException, IOException, NoProcessorException, CannotRealizeException, NoDataSinkException, NotRealizedError
	{
		/*om inte specat, använd message1.wav */
		//audioFile = new File("message1.wav");
		/*om specat, använd "generated.wav" OBS! GENERERA INTE HÄR, för att undvika generering varje gång man får ett samtal...*/
		audioFile = new File("generated.wav");
		
		DataSource source = Manager.createDataSource(new MediaLocator(audioFile.toURI().toURL()));
		
		mediaProcessor = Manager.createRealizedProcessor(new ProcessorModel(source, FORMATS, CONTENT_DESCRIPTOR));
		mediaLocator = new MediaLocator(String.format("rtp://%s:%d/audio", ip, port));
		
		dataSink = Manager.createDataSink(mediaProcessor.getDataOutput(), mediaLocator);
	}
	
	public void transmit() throws IOException, InterruptedException
	{
        mediaProcessor.start();
        dataSink.open();
        dataSink.start();

        System.out.println(messageDuration(audioFile));
        Thread.sleep((long) messageDuration(audioFile)*1000);
        
        dataSink.stop();
        dataSink.close();
        mediaProcessor.stop();
        mediaProcessor.close();

    }
    
    VoiceHandler verlaine;

    void setup(){
     verlaine = new VoiceHandler("kevin16");
     verlaine.mluv("hi boz you got damn whore") ;
    }

	public static double messageDuration(File file)
	{   
		AudioInputStream stream = null;

		try {
			stream = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		javax.sound.sampled.AudioFormat messageFormat = stream.getFormat();

		return file.length()/messageFormat.getSampleRate()/messageFormat.getChannels();
	}

}

