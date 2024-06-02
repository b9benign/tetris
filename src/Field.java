package src;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import src.Blocks.Block;

/**
 * This class creates a Field out of <code>Blocks</code>. 
 * 12 Blocks wide and 20 Blocks high.
 * It also handles what happenes when a Line is filled with Blocks.
 * The Field class is the one that changes the Score based on the amount of removed Lines.
 *
 * @author Richard
 * @version 1.0
 * 
 * @see Block
 */
public class Field {
    private static int WIDTH = 360;
    private static int HEIGHT = 600;
    private static int BlockSize = 30;
    private static int left_x;
    private static int top_y;
    private static int bottom_y;
    public static Block[][] FieldArray = new Block[20][12];

    /**
     * Construktor for Field class.
     * Sets the Color of every Block to gray.
     * And gives every Block the x and y of each left upper corner.
     * 
     */
    public Field(){
        left_x = (Game.WIDTH/2) - (WIDTH/2);
        top_y = 50;
        bottom_y = top_y + HEIGHT - BlockSize;
        for(int i=0; i<20;i++){
            for(int o = 0; o<12;o++){
                FieldArray[i][o] = new Block(Color.gray);
                FieldArray[i][o].x = left_x+(o*BlockSize);
                FieldArray[i][o].y = bottom_y-(i*BlockSize);
            }
        }
    }

    private void removeLine(int i){
        for(int j=i;j<(20-i);j++){
            for(int o = 0; o<12;o++){
                if(j==19){
                    FieldArray[j][o].visible = false;
                    FieldArray[j][o].c = Color.gray;
                }else{
                    FieldArray[j][o].visible=FieldArray[j+1][o].visible;
                    FieldArray[j][o].c=FieldArray[j+1][o].c;
                }
            }
        }
    }

    /**
     * returns on of the 7 Colors, is called by <code>addRandomBlocks</code> in the Game class.
     * 
     * @return Color
     * @see Game
     */
    public static Color getRandomColor(){
        int rand = new Random().nextInt(6)+1;
        Color color;
        switch(rand){
            case 1:
                color = Color.red;
                break;
            case 2:
                color = Color.green;
                break;
            case 3:
                color = Color.pink;
                break;
            case 4:
                color = Color.yellow;
                break;
            case 5:
                color = Color.blue;
                break;
            case 6:
                color = Color.magenta;
                break;
            default:
                color = Color.cyan;
        }
        return color;
    }
    private void addPointByRows(int i){
        if(Main.gameMode!=0){
            Sound.se.play(1, false);
        }
        if(i==1){
            PlayManager.counter += (PlayManager.level+1)*40;
        }else if(i==2){
            PlayManager.counter += (PlayManager.level+1)*100;
        }else if(i==3){
            PlayManager.counter += (PlayManager.level+1)*300;
        }else if(i==4){
            PlayManager.counter += (PlayManager.level+1)*1200;
        }else{
            PlayManager.counter += (PlayManager.level+1)*1500;
        }
    }
    /**
     * update is called 60 times per second (once per Frame),
     * it checks if Lines are filled with Blocks and 
     * gives a count on how many Lines where removed at once.
     * it calls <code>addPointByRows</code> with the count of removed Lines.
     *
     */
    public void update(){
        int removeLineCounter = 0;
        for(int j=0; j<4;j++){
            for(int i=0; i<20;i++){
                int linePieceCounter=0;
                for(int o = 0; o<12;o++){
                    if(FieldArray[i][o].visible){
                        linePieceCounter++;
                    }
                }
                if(linePieceCounter==12){
                    removeLine(i);
                    removeLineCounter++;
                    i--;
                }
            }
        }
        if(removeLineCounter!=0){
            addPointByRows(removeLineCounter);
        }
    }
    /**
     * This function, same as update, is also called every Frame.
     * it uses <code>Graphics2D</code> to draw every Block in the Field Array.
     * 
     * @param g2
     * 
     * @see Graphics2D
     */
    public void draw(Graphics2D g2){
        //Field Blocks
        int i = 0;
        int o = 0;
        while(HEIGHT-(o*BlockSize)>0){
            while(WIDTH-(i*BlockSize)>0){
                g2.setColor(FieldArray[o][i].c);
                g2.fillRect(left_x+(i*BlockSize), bottom_y-(o*BlockSize), BlockSize, BlockSize);
                g2.setColor(Color.BLACK);
                g2.drawRect(left_x+(i*BlockSize), bottom_y-(o*BlockSize), BlockSize, BlockSize);
                i++;
            }
            if(i>=12){
                i = 0;
                o++;
            }
        }
    }
}
