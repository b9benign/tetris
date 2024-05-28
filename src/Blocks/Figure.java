package src.Blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


import src.KeyHandler;
import src.PlayManager;
import src.Sound;
import src.Field;
import src.Game;

public class Figure {
    //public Color c; and Array
    public Block b[] = new Block[0];
    public int arrayLength;
    public int arrayWidth;
    public Color c;
    public int[] visible;
    boolean rightCollision, leftCollision, bottomCollision, rotationCollision;
    public boolean active;
    int autoDropCounter = 0;
    int rotateCounter = 0;
    int waitNewBlockCounter = 0;

    public Figure(Color c, int arrayWidth, int[] visible){
        this.b = create(c, arrayWidth, visible);
        this.c = c;
        this.visible = visible;

        setVisible(visible);
    }

    public Block[] create(Color c, int arrayWidth, int[] visible) {
        this.arrayWidth = arrayWidth;
        this.arrayLength = arrayWidth * arrayWidth;

        Block b[] = new Block[this.arrayLength];
        for(int i=0; i< this.arrayLength;i++){
            b[i]= new Block(c);
        }
        return b;
    };
    public void setVisible(int[] blockIndices){
        Set<Integer> set = new HashSet<>();
        for (int num : blockIndices) {
            set.add(num);
        }
        for (int i = 0; i < b.length; i++) {
            b[i].visible = set.contains(i); //Nur übergebene Indizes sind nun sichtbar
        }
    }
    public void setXY(int x, int y){
        int o = 0;
        for(int i=0; i<b.length ;i++){
            b[i].x = x + (i-o*(arrayWidth))*Block.SIZE;
            b[i].y = y + o*Block.SIZE;
            if((i+1)%arrayWidth==0){
                o++;
            }
        }
    }
    public void rotate(){
        if(rotationCollision==false && active){
            if (KeyHandler.keyQPressed) {
                if(rotateCounter>=15){
                    rotateRight();
                    rotateCounter=0;
                    KeyHandler.keyQPressed=false;
                }
            } else if (KeyHandler.keyEPressed && active) {
                if(rotateCounter>=15){
                    rotateLeft();
                    rotateCounter=0;
                    KeyHandler.keyEPressed=false;
                }
            }
        }else{
            if (KeyHandler.keyQPressed && active) {
                KeyHandler.keyQPressed=false;
            }else if (KeyHandler.keyEPressed && active) {
                KeyHandler.keyEPressed=false;
            }
        }
    }

    private void rotateLeft() {             //counter-clockwise rotation
        Sound.se.play(3, false);
        ArrayList<Integer> tempIdx = new ArrayList<>();

        for (int i = 0; i < arrayWidth; i++) {
            for (int j = 0; j < arrayWidth; j++) {
                if(b[(i * arrayWidth + j)].visible) {

                    tempIdx.add(j * arrayWidth + (arrayWidth - 1 - i));

                }
            }
        }
        int[] newIdx = new int[tempIdx.size()];
        for(int x = 0; x < tempIdx.size(); x ++) {
            newIdx[x] = tempIdx.get(x);
        }
        setVisible(newIdx);
    };

    private void rotateRight() {            //clockwise rotation
        Sound.se.play(3, false);
        ArrayList<Integer> tempIdx = new ArrayList<>();
        for (int i = 0; i < arrayWidth; i++) {
            for (int j = 0; j < arrayWidth; j++) {
                if(b[(i * arrayWidth + j)].visible) {

                    tempIdx.add((arrayWidth - 1 - j) * arrayWidth + i);

                }
            }
        }
        int[] newIdx = new int[tempIdx.size()];
        for(int x = 0; x < tempIdx.size(); x ++) {
            newIdx[x] = tempIdx.get(x);
        }
        setVisible(newIdx);
    };
    public void checkMovementCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;
        rotationCollision = false;

        //Check Frame Collision
        //Left Wall
        for(int i = 0; i < b.length; i++){
            if(b[i].x == PlayManager.left_x && b[i].visible){
                leftCollision=true;
            }
        }
        for(int i = 0; i < Field.FieldArray.length;i++){
            for(int o = 0; o < Field.FieldArray[i].length;o++){
                for(int j = 0; j < b.length; j++){
                    if(b[j].y + Block.SIZE == Field.FieldArray[i][o].y && b[j].x - Block.SIZE == Field.FieldArray[i][o].x && b[j].visible && Field.FieldArray[i][o].visible){
                        leftCollision=true;
                    }
                }
            }
        }
        //Right Wall
        for(int i = 0; i < b.length; i++){
            if(b[i].x + Block.SIZE == PlayManager.right_x && b[i].visible){
                rightCollision=true;
            }
        }
        for(int i = 0; i < Field.FieldArray.length;i++){
            for(int o = 0; o < Field.FieldArray[i].length;o++){
                for(int j = 0; j < b.length; j++){
                    if(b[j].y + Block.SIZE == Field.FieldArray[i][o].y && b[j].x + 2*Block.SIZE == Field.FieldArray[i][o].x && b[j].visible && Field.FieldArray[i][o].visible){
                        rightCollision=true;
                    }
                }
            }
        }
        //Bottom Floor
        for(int i = 0; i < b.length; i++){
            if(b[i].y + Block.SIZE == PlayManager.bottom_y && b[i].visible){
                bottomCollision=true;
            }
        }
        for(int i = 0; i < Field.FieldArray.length;i++){
            for(int o = 0; o < Field.FieldArray[i].length;o++){
                for(int j = 0; j < b.length; j++){
                    if(b[j].y + Block.SIZE == Field.FieldArray[i][o].y &&b[j].x == Field.FieldArray[i][o].x && b[j].visible && Field.FieldArray[i][o].visible){
                        bottomCollision=true;
                    }
                }
            }
        }
    }
    public void checkRotationCollision(){
        //if Block[] overlaps Left Wall, Dont allow rotation
        for(int i = 0; i < b.length; i++){
            if(b[i].x + Block.SIZE == PlayManager.left_x){
                rotationCollision=true;
            }
        }
        //if Block[] overlaps Right Wall, Dont allow rotation
        for(int i = 0; i < b.length; i++){
            if(b[i].x == PlayManager.right_x){
                rotationCollision=true;
            }
        }
        //if Block[] overlaps Bottom Line, Dont allow rotation
        for(int i = 0; i < b.length; i++){
            if(b[i].y == PlayManager.bottom_y){
                rotationCollision=true;
            }
        }
    }
    public void update(){
        //rotation
        rotateCounter++;
        rotate();
        //check Collisions
        checkMovementCollision();
        checkRotationCollision();
        //Movements
        if(KeyHandler.leftPressed && active){
            if(leftCollision == false){
                for(int i=0; i < b.length; i++){
                    b[i].x -= Block.SIZE;
                }
            }
            KeyHandler.leftPressed = false;
        }
        if(KeyHandler.rightPressed && active){
            if(rightCollision == false){
                for(int i=0; i < b.length; i++){
                    b[i].x += Block.SIZE;
                }
            }
            KeyHandler.rightPressed = false;
        }
        if(KeyHandler.downPressed && active){
            if(bottomCollision == false){
                if(b[0].y + (arrayWidth+2)*Block.SIZE<=PlayManager.bottom_y){
                    for(int i=0; i < b.length; i++){
                        b[i].y += Block.SIZE;
                    }
                }
            }
            KeyHandler.downPressed = false;
        }
        //Figure reaches Bottom/lowest possible point
        if(bottomCollision==true && autoDropCounter==PlayManager.dropInterval/2){
            waitNewBlockCounter++;
            if(waitNewBlockCounter>=10){
                for(int i=0; i < b.length; i++){
                    if(b[i].y<=PlayManager.top_y){
                        Sound.music.stop();
                        Sound.se.play(2, false);
                        Game.gameOver = true;
                    }else{
                        PlayManager.emptyCurrentFigure();
                        setFieldBlocks();
                        Sound.se.play(4, false);
                    }
                }
            }
        }else{ //autodrop
            autoDropCounter++;
            if(autoDropCounter==PlayManager.dropInterval){
                if(bottomCollision==false){
                    for(int i=0; i < b.length; i++){
                        b[i].y += Block.SIZE;
                    }
                }
                autoDropCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2){
        int o = 0;
        for(int i = 0; i < this.arrayLength;i++){
            if(i < this.arrayLength && i >= 0){
                if(b[i].visible==true && b[i].y>=PlayManager.top_y){
                    g2.setColor(b[i].c);    //color from figur
                    g2.fillRect(b[i].x, b[i].y, Block.SIZE, Block.SIZE);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(b[i].x, b[i].y, Block.SIZE, Block.SIZE); 
                }
            }
            if(i==(this.arrayWidth*(o+1))-1){ // if i is longer than array width then go to next row
                o++;
            }
        }
    }
    public void setFieldBlocks(){
        for(int i = 0; i < Field.FieldArray.length;i++){
            for(int o = 0; o < Field.FieldArray[i].length;o++){
                for(int j = 0; j < b.length; j++){
                    if((b[j].y == Field.FieldArray[i][o].y && b[j].visible) && b[j].x == Field.FieldArray[i][o].x && b[j].visible){
                        Field.FieldArray[i][o].visible=true;
                        Field.FieldArray[i][o].c = b[j].c;
                    }
                }
            }
        }
    }
    public Color getColor(){
        return c;
    }
}
