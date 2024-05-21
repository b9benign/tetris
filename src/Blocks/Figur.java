package src.Blocks;

import java.awt.Color;
import java.awt.Graphics2D;

import src.PlayManager;

public class Figur {
    public Block b[] = new Block[0];
    public int arrayLength;
    public int arrayWidth;
    int autoDropCounter = 0;

    public Block[] create(Color c, int arrayWidth) {
        this.arrayWidth = arrayWidth;
        this.arrayLength = arrayWidth * arrayWidth;
        Block b[] = new Block[this.arrayLength];
        for(int i=0; i< this.arrayLength;i++){
            b[i]= new Block(c);
        }
        return b;
    };
    public void setVisible(){

    }
    public void setXY(int x, int y){

    }
    public void updateXY(int direction){

    }
    public void rotate(){
        
    }
    public void update(){
        autoDropCounter++;
        if(autoDropCounter==PlayManager.dropInterval){
            b[0].y+=Block.SIZE;
            autoDropCounter=0;
        }
    }
    public void draw(Graphics2D g2){
        int o = 0;
        for(int i = 0; i < this.arrayLength;i++){ // loop over index
            if(i < this.arrayLength && i >= 0){ //no index out of bound error check
                if(b[i].visible==true){ //wich blocks are visible
                    g2.setColor(b[i].c);    //color from figur
                    g2.fillRect(b[0].x+((i-o*this.arrayWidth) * Block.SIZE), b[0].y+(o * Block.SIZE), Block.SIZE, Block.SIZE);  //draw blocks 
                    g2.setColor(Color.BLACK);   //border color black
                    g2.drawRect(b[0].x+((i-o*this.arrayWidth) * Block.SIZE), b[0].y+(o * Block.SIZE), Block.SIZE, Block.SIZE);  //draw border
                }
            }
            if(i==(this.arrayWidth*(o+1))-1){ // if i is longer than array width then go to next row
                o++;
            }
        }
    }
}
