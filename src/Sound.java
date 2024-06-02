package src;

import javax.sound.sampled.*;
import java.net.URL;

/**
 * The class <code>Sound</code> represents sound objects for music and sound effects, with methods
 * to play, loop and stop them.
 * 
 * @author Liam
 * @version 1.0
 * @see URL
 * @see Clip
 */
public class Sound {

    public static Sound music = new Sound();
    public static Sound se = new Sound();
    private Clip musicClip;
    private final URL[] url = new URL[10];

    /** 
     * Constructs an instance of <code>Sound</code> class initializing the `url` array with URLs to
     * specific sound resources. Each index of the `url` array is being assigned a URL obtained using the
     * <code>getResource</code> method of the class, which loads the specified resource from the classpath. In this
     * case, the URLs are pointing to different sound files for the game, such as the Tetris soundtrack,
     * delete line sound, game over sound, rotation sound, and touch floor sound. These URLs will be used
     * later to load and play the corresponding sound clips.
     * 
     * @see URL
     * @see AudioInputStream
     * @see FloatControl
     */
    public Sound() {
        url[0] = getClass().getResource("resources/Tetris_Soundtrack.wav");
        url[1] = getClass().getResource("resources/delete_line.wav");
        url[2] = getClass().getResource("resources/gameover.wav");
        url[3] = getClass().getResource("resources/rotation.wav");
        url[4] = getClass().getResource("resources/touch_floor.wav");
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < url.length && url[index] != null;
    }

    private void loadClip(Clip clip, int index) throws Exception {
        AudioInputStream ais = AudioSystem.getAudioInputStream(url[index]);
        clip.open(ais);
    }

    private void setVolume(Clip clip) {
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(-40f);
    }

/**
 * The <code>play</code> method plays a sound or music clip at a specified index with volume control and handles
 * stopping previous music clips if necessary.
 * 
 * @param index is used to specify the index of the sound to be played.
 * It is used to identify the specific sound that should be loaded and played by the method.
 * @param isMusic is a boolean variable that indicates
 * whether the sound being played is music or not. If <code>isMusic</code> is <code>true</code>, it means that the sound
 * being played is music and not a sound effect.
 */
    public void play(int index, boolean isMusic) {
        if(!KeyHandler.mutePressed){
            if (!isValidIndex(index)) {
                System.err.println("Invalid sound index or URL not found.");
                return;
            }
    
            try {
                Clip clip = AudioSystem.getClip();
                loadClip(clip, index);
    
                setVolume(clip);
    
                if (isMusic) {
                    if (musicClip != null && musicClip.isRunning()) {
                        musicClip.stop();
                        musicClip.close();
                    }
                    musicClip = clip;
                }
    
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
    
                clip.start();
    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The <code>loop</code> function plays a sound clip continuously at a specified index with volume control and
     * error handling.
     * 
     * @param index is used to specify the index of the sound that you want to play in a loop.
     * This index is used to load the corresponding sound clip and set it to loop continuously.
     */
    public void loop(int index) {
        if(!KeyHandler.mutePressed){
            if (!isValidIndex(index)) {
                System.err.println("Invalid sound index or URL not found.");
                return;
            }
    
            try {
                if (musicClip != null && musicClip.isRunning()) {
                    musicClip.stop();
                    musicClip.close();
                }
    
                musicClip = AudioSystem.getClip();
                loadClip(musicClip, index);
    
                // Set volume (example value: 0.5 for 50% volume)
                setVolume(musicClip);
    
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
    }

    /**
     * The <code>stop</code> function checks if a music clip is playing and stops it before closing it.
     */
    public void stop() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
        }
    }
}