package src;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import src.Blocks.Block;
import src.Blocks.Figur;
import src.Blocks.L_Piece;

public class PlayManager {
    
    //Main Area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    //Figur
    Figur currentFigur;
    final int FigurStartX;
    final int FigurStartY;

    //Interval/gameSpeed
    public static int dropInterval = 60; //drop every 60 Frames = 1 sec

    public PlayManager(){
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        FigurStartX = left_x + (WIDTH/2) - Block.SIZE;
        FigurStartY = top_y - Block.SIZE*3;

        //test for spawn of L_Block
        currentFigur = new L_Piece();
        currentFigur.setVisible();
        currentFigur.setXY(FigurStartX, FigurStartY);
    }
    public void update(){
        currentFigur.update();
    }
    public void draw(Graphics2D g2){
        // Draw Play Area
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2f));
        g2.drawRect(left_x-2, top_y-2, WIDTH+4, HEIGHT+4);

        //Draw next Block Frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);

        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x+60, y+60);

        if(currentFigur != null){
            currentFigur.draw(g2);
        }
    }
}
