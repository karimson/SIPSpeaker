import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.*;
import javax.media.control.FormatControl;
import javax.media.control.TrackControl;
import javax.media.datasink.DataSinkEvent;
import javax.media.datasink.DataSinkListener;
import javax.media.format.*;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;

public class AudioHandler {
	
	private MediaLocator mediaLocator = null;
    private DataSink dataSink = null;
    private Processor mediaProcessor = null;
    private Processor processor = null;
    private static final Format[] FORMATS = new Format[] {new AudioFormat(AudioFormat.GSM_RTP)};
	private static final ContentDescriptor CONTENT_DESCRIPTOR = new ContentDescriptor(ContentDescriptor.RAW_RTP);
    public AudioHandler(String ip, int port) throws NoDataSourceException, MalformedURLException, IOException, NoProcessorException, CannotRealizeException, NoDataSinkException, NotRealizedError
	{
		File audioFile = new File("message1.wav");
		DataSource source = Manager.createDataSource(new MediaLocator(audioFile.toURI().toURL()));
		
		mediaProcessor = Manager.createRealizedProcessor(new ProcessorModel(source, FORMATS, CONTENT_DESCRIPTOR));
		mediaLocator = new MediaLocator(String.format("rtp://%s:%d/audio", ip, port));
		
		dataSink = Manager.createDataSink(mediaProcessor.getDataOutput(), mediaLocator);

	}
	
	public void startTransmitting() throws IOException {
        mediaProcessor.start();
        dataSink.open();
        dataSink.start();
        
        RTPDataSinkListener dsl = new RTPDataSinkListener();
        
        try {
			dsl.waitForTransmission();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        dataSink.stop();
        dataSink.close();
        mediaProcessor.stop();
        mediaProcessor.close();
    }

    public void stopTransmitting() throws IOException {
        
    }
    
    class RTPDataSinkListener implements DataSinkListener 
    {
    	boolean end = false;
    	
    	public void dataSinkUpdate(DataSinkEvent event) 
    	{
    		if (event instanceof javax.media.datasink.EndOfStreamEvent) 
    		{
    			end = true;
    		}   
        }
    	
    	public void waitForTransmission() throws InterruptedException {
    		while (!end) 
    		{
    			Thread.sleep(50);
    			DataSinkEvent dsEvent = new DataSinkEvent(dataSink);
    			dataSinkUpdate(dsEvent);
    		}
    	}
    }
}

