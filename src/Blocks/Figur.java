package src.Blocks;

import java.awt.Color;
import java.awt.Graphics2D;

public class Figur {
    public Block b[] = new Block[4];
    public Block tempB [] = new Block[4];

    public void create(Color c) {
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        b[4] = new Block(c);
        b[5] = new Block(c);
        b[6] = new Block(c);
        b[7] = new Block(c);
        b[8] = new Block(c);
    };
    public void setXY(int x, int y){

    }
    public void updateXY(int direction){

    }
    public void rotate(){
        
    }
    public void update(){

    }
    public void draw(Graphics2D g2){

    }

}
