package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;

    public static boolean gameOver = false;
    Thread GameMode1;

    PlayManager pm;
    Field field;

    public GamePanel() {

        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        //add Keylistener
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        //create FigureList for Gamemode1
        ArrayList<String> FigureList = new ArrayList<String>();
        FigureList.add("L_Piece");
        FigureList.add("Reverse_L_Piece");
        FigureList.add("Squiggly_Piece");
        FigureList.add("Reverse_Squiggly_Piece");
        FigureList.add("Square_Piece");
        FigureList.add("T_Piece");
        FigureList.add("Line_Piece");

        pm = new PlayManager(FigureList);
        field = new Field();
    }
    public void launchMode1(){
        GameMode1 = new Thread(this);
        GameMode1.start();
    }
    public void run(){
        // Game Loop
        double drawInterval = 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(GameMode1 != null){

            currentTime = System.nanoTime();

            delta += (currentTime- lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }
    private void update(){
        if(KeyHandler.pausePressed==false || gameOver){
            pm.update();
            field.update();
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        field.draw(g2);
        pm.draw(g2);
    }
}
