
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.Codec;
import javax.media.DataSink;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoProcessorException;
import javax.media.NotRealizedError;
import javax.media.Processor;
import javax.media.UnsupportedPlugInException;
import javax.media.control.FormatControl;
import javax.media.control.TrackControl;
import javax.media.datasink.DataSinkEvent;
import javax.media.datasink.DataSinkListener;
import javax.media.format.AudioFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.PushBufferDataSource;
//import sip.VoIPserver;

/**
 * The Class that send RTP sound to the caller
 */
public class RTPHandler {

    private String obRTPAddress, obRTPPort, obVoicePath;
 //   private VoIPserver obVoIPServer;
    private Processor processor;
    private DataSink obDataSink;

    public RTPHandler(String obRTPAddress, String obRTPPort) throws MalformedURLException {
        this.obRTPAddress = obRTPAddress;
        this.obRTPPort = obRTPPort;
        this.obVoicePath = new File("message.wav").toURI().toURL().toString();
    //    this.obVoIPServer = obVoIPServer;
        this.processor = null;
        this.obDataSink = null;
    }
    


    public void send() {
        // Create a processor for from local file & return false if we cannot create it
        try {
            MediaLocator media = new MediaLocator(obVoicePath);
            processor = Manager.createProcessor(media);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
 //           obVoIPServer.setIsFinish();
        } catch (NoProcessorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
 //           obVoIPServer.setIsFinish();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
 //           obVoIPServer.setIsFinish();
        }
        // configure the processor
        processor.configure();
        while (processor.getState() != Processor.Configured) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              //  obVoIPServer.setIsFinish();
            }
        }

        processor.setContentDescriptor(new ContentDescriptor(ContentDescriptor.RAW_RTP));
        TrackControl track[] = processor.getTrackControls();
        boolean encodingOk = false;
        // Go through the tracks and try to program one of them to output gsm data.
        for (int i = 0; i < track.length; i++) {
        	System.out.println("Track length:"+track.length+ " TRACK 1:" + (track[i].getSupportedFormats())[i]);
        	if (!encodingOk && track[i] instanceof FormatControl) 
        	{
        		if (((FormatControl)track[i]).setFormat(new AudioFormat(AudioFormat.GSM_RTP, 8000, 8, 1)) == null) 
        		{
        			track[i].setEnabled(false);
        		} 
        		else 
        		{
        			encodingOk = true;
        		}
        		
        		
        	} else {
        		// we could not set this track to gsm, so disable it
        		track[i].setEnabled(false);
        	}
        }
        
        System.out.println(encodingOk);
        // At this point, we have determined where we can send out gsm data or not. realize the processor
        if (encodingOk) {
            processor.realize();
            while (processor.getState() != Processor.Realized) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
     //             obVoIPServer.setIsFinish();
                }
            }
            // get the output datasource of the processor and exit if we fail
            // DataSource ds = null;
            PushBufferDataSource ds = null;
            try {
                ds = (PushBufferDataSource) processor.getDataOutput();
            } catch (NotRealizedError e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
     //           obVoIPServer.setIsFinish();
            }
            // handling this datasource to manager for creating an RTP datasink our RTP datasink will multicast the audio
            // RTPDataSinkListener obDataSinkListener = null;
            try {
                String location = "rtp://" + obRTPAddress + ":" + obRTPPort
                        + "/audio";
                MediaLocator m = new MediaLocator(location);
                obDataSink = Manager.createDataSink(ds, m);
                /*
                // create a listener to control the datasink
                obDataSinkListener = new RTPDataSinkListener(obDataSink);
                obDataSinkListener.dataSinkUpdate(new
                DataSinkEvent(obDataSink));
                obDataSink.addDataSinkListener(obDataSinkListener);
                 */
                obDataSink.open();
                // now start the datasink and processor
                obDataSink.start();
                processor.start();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
  //              obVoIPServer.setIsFinish();
            }
            // Closing the processor will end the data stream to the data sink.
            // Wait for the end of stream to occur before closing the datasink
            try {
                Thread.currentThread().sleep(8000);
            } catch (InterruptedException ie) {
            }
            //Stop the transmission
            stop();
        } else {
           // obVoIPServer.setIsFinish();
        }
    }

    /** Stops the transmission if it is already started */
    public void stop() {
        synchronized (this) {
            // Stop the processor first
            if (processor != null) {
                processor.stop();
                processor.close();
                processor = null;
            }
            // Stop the datasink
            if (obDataSink != null) {
                obDataSink.close();
            }
            //Tell to the thread to close the session
            //obVoIPServer.setIsFinish();
        }
    }

    /**
     * Control the ending of the program prior to closing the data sink
     */
    class RTPDataSinkListener implements DataSinkListener {

        private boolean endOfStream = false;
        private DataSink obDataSink;

        public RTPDataSinkListener(DataSink obDataSink) {
            this.obDataSink = obDataSink;
        }

        // Flag the ending of the data stream
        public void dataSinkUpdate(DataSinkEvent event) {
            try {
                if (event instanceof javax.media.datasink.EndOfStreamEvent) {
                    endOfStream = true;
                }
            } catch (UnsupportedOperationException ue) {
                // TODO Auto-generated catch block
                ue.printStackTrace();
            }
        }

        /*
         * Cause the current thread to sleep if the data stream is still
         * available. This makes certain that JMF threads are done prior to
         * closing the data sink and finalizing the output file
         */
        public void waitEndOfStream(long checkTimeMs) {

            while (!endOfStream) {
                try {
                    Thread.currentThread().sleep(checkTimeMs);
                    DataSinkEvent obDataSinkEvent = new DataSinkEvent(
                            obDataSink);
                    dataSinkUpdate(obDataSinkEvent);
                    // Thread.sleep(checkTimeMs);
                } catch (InterruptedException ie) {
                    // TODO Auto-generated catch block
                    ie.printStackTrace();
                }
            }
        }
    }
}// Class end
