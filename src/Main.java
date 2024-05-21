package src;

import javax.swing.JFrame;

import src.Sound.MusicPlayer;

public class Main {
    public static void main (String[] args) {

        JFrame window = new JFrame("Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        MusicPlayer musicPlayer;
        musicPlayer = new MusicPlayer();
        musicPlayer.playMusic("src/Sound/Tetris_Soundtrack.wav");
    }
}
