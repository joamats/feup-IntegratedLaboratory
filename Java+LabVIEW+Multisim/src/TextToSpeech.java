import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {
	
	public static void speak(String message) {
		
	Voice Voice; //Creating object of Voice class
	Voice = VoiceManager.getInstance().getVoice("kevin");//Getting voice
    if (Voice != null) {
    	Voice.allocate();//Allocating Voice
    }
    try {
    	Voice.setRate(140);//Setting the rate of the voice
    	Voice.setPitch(120);//Setting the Pitch of the voice
    	Voice.setVolume(3);//Setting the volume of the voice 
    	Voice.speak(message);//Calling speak() method
        
    } catch (Exception e1) {
        e1.printStackTrace();
    }
	}
	
//	public static void main(String[] args) {
//	
//		speak("Ocular. Beyond Control.");
//	}
}
