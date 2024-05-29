package src;

import javax.sound.sampled.*;
import java.net.URL;

public class Sound {

    public static Sound music = new Sound();
    public static Sound se = new Sound();
    private Clip musicClip;
    private final URL[] url = new URL[10];

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

    public void play(int index, boolean isMusic) {
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

    public void loop(int index) {
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

    public void stop() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
        }
    }
}