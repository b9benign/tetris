package src;

import src.PieceBuilder.PieceBuilder;

import javax.swing.JFrame;

public class Main {
    public static void main (String[] args) {

        JFrame window = new JFrame("Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        //GamePanel gp = new GamePanel();
        //window.add(gp);
        //window.pack();

        //window.setLocationRelativeTo(null);
        //window.setVisible(true);

        //gp.launchMode1();

        PieceBuilder pieceBuilder = new PieceBuilder();
        window.add(pieceBuilder);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
