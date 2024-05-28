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

    public void play(int index, boolean isMusic) {
        if (index < 0 || index >= url.length || url[index] == null) {
            System.err.println("Invalid sound index or URL not found.");
            return;
        }

        try (AudioInputStream ais = AudioSystem.getAudioInputStream(url[index])) {
            Clip clip = AudioSystem.getClip();

            if (isMusic) {
                if (musicClip != null && musicClip.isRunning()) {
                    musicClip.stop();
                    musicClip.close();
                }
                musicClip = clip;
            }

            clip.open(ais);
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
        if (index < 0 || index >= url.length || url[index] == null) {
            System.err.println("Invalid sound index or URL not found.");
            return;
        }

        try (AudioInputStream ais = AudioSystem.getAudioInputStream(url[index])) {
            if (musicClip != null && musicClip.isRunning()) {
                musicClip.stop();
                musicClip.close();
            }
            musicClip = AudioSystem.getClip();
            musicClip.open(ais);
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