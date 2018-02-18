package sharky.audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

	// ==================== Instance Data ====================
	
	private final String PATH;
	private final boolean REPEAT;
	
	private static final String AUDIO_FOLDER = "C:\\Users\\calreiter16\\Desktop\\AHA\\AHA 14-15\\Trimester 3 - 14-15\\AP Computer Science\\CalebReiter_Sharky\\res\\audio\\";
	
	// ==================== Static Objects ====================
	
	public static final Audio JAWS = new Audio(AUDIO_FOLDER + "Jaws Theme.wav", true); 
	public static final Audio GULP = new Audio(AUDIO_FOLDER + "Gulp.wav", false);

	// ==================== Constructor ====================
	
	public Audio(String path, boolean repeat) {
		PATH = path;
		REPEAT = repeat;		
	}
	
	// ==================== Public Methods ====================
	
	public void play() {
		try {
			File file = new File(PATH);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			if (REPEAT) clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			System.out.println("ERROR: could not load audio");
			e.printStackTrace();
		}
	}	
}
