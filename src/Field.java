package src;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import src.Blocks.Block;

public class Field {
    public static int WIDTH = 360;
    public static int HEIGHT = 600;
    public static int BlockSize = 30;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;
    public static Block[][] FieldArray = new Block[20][12];
    public Field(){
        left_x = (Game.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH - BlockSize;
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
    public void removeLine(int i){
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
                color =Color.cyan;
        }
        return color;
    }
    public void addPointByRows(int i){
        Sound.se.play(1, false);
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
