import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.*;
import javax.media.format.*;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;

public class AudioHandler {
	
	private MediaLocator mediaLocator;
    private DataSink dataSink;
    private Processor mediaProcessor;
    private static final Format[] FORMATS = new Format[]{new AudioFormat(AudioFormat.ULAW_RTP)};
    private static final ContentDescriptor CONTENT_DESCRIPTOR = new ContentDescriptor(ContentDescriptor.RAW_RTP);
	
	public AudioHandler(String ip, int port) throws NoDataSourceException, MalformedURLException, IOException, NoProcessorException, CannotRealizeException, NoDataSinkException, NotRealizedError
	{
		File audioFile = new File("message.wav");
		
        mediaLocator = new MediaLocator(String.format("rtp://%s:%d/audio", ip, port));
        DataSource source = Manager.createDataSource(new MediaLocator(audioFile.toURI().toURL()));

        mediaProcessor = Manager.createRealizedProcessor(new ProcessorModel(source, FORMATS, CONTENT_DESCRIPTOR));
        dataSink = Manager.createDataSink(mediaProcessor.getDataOutput(), mediaLocator);
    }
	
	public void startTransmitting() throws IOException {
        mediaProcessor.start();
        dataSink.open();
        dataSink.start();
    }

    public void stopTransmitting() throws IOException {
        dataSink.stop();
        dataSink.close();
        mediaProcessor.stop();
        mediaProcessor.close();
    }
}
