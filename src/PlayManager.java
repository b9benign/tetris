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
    ArrayList<Figure> FigureList;
    static Figure currentFigur;
    static Figure nextFigur;
    final int nextFigurX;
    final int nextFigurY;
    final int FigurStartX;
    final int FigurStartY;


    //Interval/gameSpeed
    public static int dropInterval = 60; //drop every 60 Frames = 1 sec

    public PlayManager(ArrayList<Figure> FigureList){
        this.FigureList = FigureList;

        left_x = (Game.WIDTH/2) - (WIDTH/2);
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
        //pick random nextFigur
        nextFigur = pickRandomFigure(FigureList);
    }
  
    private Figure pickRandomFigure(ArrayList<Figure> FigureList){
        Figure figure = null;
        int i = new Random().nextInt(FigureList.size());
        Color color = FigureList.get(i).c;
        int arrayWidth = FigureList.get(i).arrayWidth;
        int[] visible = FigureList.get(i).visible;

        figure = new Figure(color, arrayWidth, visible);
        
        return figure;
    };

    public void update(){
        if(currentFigur==null&&dropInterval>Figure.autoDropCounter){
            currentFigur = nextFigur;
            currentFigur.setXY(FigurStartX, FigurStartY);
            nextFigur = pickRandomFigure(FigureList);
        }else if(currentFigur!=null){
            currentFigur.active = true;
            currentFigur.update();
        }
        if(nextFigur!=null){
            nextFigur.active=false;
        }
        if(counter>=(level+1)*100 && Main.gameMode==1){ // every 100 point, change level
            //normal is 1000, but for showing the speeding up we used 100
            level++;
            dropInterval-=5;
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

        g2.setColor(Color.lightGray);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x+60, y+60);

        //draw Info on left Side
        g2.drawString("Keybinds: ", 80, 80);

        g2.setColor(Color.lightGray);
        g2.drawString("Esc", 80, 130);
        g2.drawString("-> Pause", 130, 130);

        g2.setColor(Color.GRAY);
        g2.drawString("A", 80, 180);
        g2.drawString("-> Move Left", 130, 180);

        g2.setColor(Color.lightGray);
        g2.drawString("D", 80, 230);
        g2.drawString("-> Move Right", 130, 230);

        g2.setColor(Color.GRAY);
        g2.drawString("S", 80, 280);
        g2.drawString("-> Move Down", 130, 280);

        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("Q", 80, 330);
        g2.drawString("-> Rotate Left", 130, 330);

        g2.setColor(Color.GRAY);
        g2.drawString("E", 80, 380);
        g2.drawString("-> Rotate Right", 130, 380);

        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("M", 80, 600);
        if(KeyHandler.mutePressed){
            g2.drawString("-> Deactivate Sounds", 130, 600);
        }else{
            g2.drawString("-> Activate Sounds", 130, 600);
        }

        //draw Info on right Side
        
        g2.drawString("Score: " + String.valueOf(counter), 920, 80);
        int levelcolor=level%5;
        if(levelcolor==1){
            g2.setColor(Color.RED);
        }else if(levelcolor==2){
            g2.setColor(Color.GREEN);
        }else if(levelcolor==3){
            g2.setColor(Color.BLUE);
        }else if(levelcolor==4){
            g2.setColor(Color.YELLOW);
        }else{
            g2.setColor(Color.ORANGE);
        }
        g2.drawString("Level: " + String.valueOf(level), 920, 130);
        
        if(currentFigur != null){
            currentFigur.draw(g2);
        }
        if(nextFigur!=null){
            nextFigur.setXY(nextFigurX, nextFigurY);
            nextFigur.draw(g2);
        }
        
        if(KeyHandler.pausePressed){
            x = left_x + 80;
            y = top_y + 320;
            g2.setColor(Color.white);
            g2.fillRect(left_x-50, y-200, WIDTH+100, HEIGHT/2);
            g2.setColor(Color.red);
            g2.drawRect(left_x-50, y-200, WIDTH+100, HEIGHT/2);
            g2.setColor(Color.RED);
            g2.setFont(g2.getFont().deriveFont(50f));
            g2.drawString("PAUSED", x, y-40);
        }
        if(Game.gameOver){
            x = left_x + 80;
            y = top_y + 320;
            g2.setColor(Color.white);
            g2.fillRect(left_x-50, y-200, WIDTH+100, HEIGHT/2);
            g2.setColor(Color.red);
            g2.drawRect(left_x-50, y-200, WIDTH+100, HEIGHT/2);
            g2.setColor(Color.RED);
            g2.setFont(g2.getFont().deriveFont(50f));
            g2.drawString("Game Over", x-40, y-90);
            g2.drawString("Score: " + counter, x-10, y-40);
        }
    }
    public static void emptyCurrentFigure() {
        currentFigur = null;
    }
}
