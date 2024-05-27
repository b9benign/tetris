package src;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import src.Blocks.Block;
import src.Blocks.Figure;
import src.Blocks.L_Piece;
import src.Blocks.Line_Piece;
import src.Blocks.Reverse_L_Piece;
import src.Blocks.Reverse_Squiggly_Piece;
import src.Blocks.Square_Piece;
import src.Blocks.Squiggly_Piece;
import src.Blocks.T_Piece;

public class PlayManager {
    
    //Main Area
    public static int counter = 0;
    public static int level = 0;
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    //Figur
    ArrayList<String> FigureList;
    ArrayList<String> NextFigurList;
    static Figure currentFigur;
    static Figure nextFigur;
    final int nextFigurX;
    final int nextFigurY;
    final int FigurStartX;
    final int FigurStartY;

    //Interval/gameSpeed
    public static int dropInterval = 60; //drop every 60 Frames = 1 sec

    public PlayManager(ArrayList<String> FigureList){
        this.FigureList = FigureList;
        this.NextFigurList = FigureList;

        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        FigurStartX = left_x + (WIDTH/2) - Block.SIZE;
        FigurStartY = top_y - Block.SIZE*2;

        nextFigurX = right_x + 150;
        nextFigurY = top_y + 475;
        //spawn random Figure, that is inside the FigureList
        currentFigur = pickRandomFigure(FigureList);
        currentFigur.setXY(FigurStartX, FigurStartY);
        nextFigur = pickRandomFigure(FigureList);
        //spawn Squiggly_Piece (only for testing)
        //currentFigur = new Squiggly_Piece();
    }
    private Figure pickRandomFigure(ArrayList<String> FigureList){
        String figureString = null;
        Figure figure = null;
        int i = new Random().nextInt(FigureList.size());
        figureString =  FigureList.get(i);
        if(figureString=="L_Piece"){
            figure = new L_Piece();
        }else if(figureString=="Reverse_L_Piece"){
            figure = new Reverse_L_Piece();
        }else if(figureString=="Squiggly_Piece"){
            figure = new Squiggly_Piece();
        }else if(figureString=="Reverse_Squiggly_Piece"){
            figure = new Reverse_Squiggly_Piece();
        }else if(figureString=="Square_Piece"){
            figure = new Square_Piece();
        }else if(figureString=="T_Piece"){
            figure = new T_Piece();
        }else if(figureString=="Line_Piece"){
            figure = new Line_Piece();
        }
        return figure;
    };

    public void update(){
        if(currentFigur==null){
            currentFigur = nextFigur;
            currentFigur.setXY(FigurStartX, FigurStartY);
            nextFigur = pickRandomFigure(NextFigurList);
        }else{
            currentFigur.active = true;
            currentFigur.update();
        }
        if(nextFigur!=null){
            nextFigur.active=false;
        }
        if(counter>=(level+1)*1000){
            level++;
            dropInterval--;
            //color Theme change
        }
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

        g2.drawString(String.valueOf(counter), 100, 100);
        
        if(currentFigur != null){
            currentFigur.draw(g2);
        }
        if(nextFigur!=null){
            nextFigur.setXY(nextFigurX, nextFigurY);
            nextFigur.draw(g2);
        }
        //nextFigur.draw(g2);
        
        if(KeyHandler.pausePressed){
            x = left_x + 80;
            y = top_y + 320;
            g2.setColor(Color.white);
            g2.fillRect(left_x-50, y-200, WIDTH+100, HEIGHT/2);
            g2.setColor(Color.red);
            g2.drawRect(left_x-50, y-200, WIDTH+100, HEIGHT/2);
            g2.setColor(Color.RED);
            g2.setFont(g2.getFont().deriveFont(50f));
            g2.drawString("PAUSED", x, y);
        }
        if(GamePanel.gameOver){
            x = left_x + 80;
            y = top_y + 320;
            g2.setColor(Color.white);
            g2.fillRect(left_x-50, y-200, WIDTH+100, HEIGHT/2);
            g2.setColor(Color.red);
            g2.drawRect(left_x-50, y-200, WIDTH+100, HEIGHT/2);
            g2.setColor(Color.RED);
            g2.setFont(g2.getFont().deriveFont(50f));
            g2.drawString("GameOver", x, y);
            g2.drawString("Score: " + counter, x, y+80);
        }
    }
    public static void emptyCurrentFigure() {
        currentFigur = null;
    }
    public static void Gameover(){
        GamePanel.gameOver = true;
    }
}
