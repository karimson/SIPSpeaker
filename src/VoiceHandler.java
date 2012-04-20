import javax.sound.sampled.AudioFileFormat;

import com.sun.speech.freetts.FreeTTS;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

public class VoiceHandler
{
	String voiceName = "kevin";
	VoiceManager voiceManager;
	Voice voice; 
	FreeTTS freetts;

	VoiceHandler()
	{
		voiceManager = VoiceManager.getInstance();
		voice = voiceManager.getVoice("kevin");

		voice.setPitch((float) 1.75);
		voice.setPitchShift((float) 0.75);
		voice.setStyle("business");  //"business", "casual", "robotic", "breathy"
		
		voice.allocate();
	}

	void setMessage(String message)
	{
		System.out.println(System.getProperty("user.dir")+"generated.wav");
		SingleFileAudioPlayer sfap = new SingleFileAudioPlayer(System.getProperty("user.dir")+"generated.wav", AudioFileFormat.Type.WAVE);
		voice.setAudioPlayer(sfap);
		voice.speak(message);
		sfap.close();
	}
}
