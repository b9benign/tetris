package src.Blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import src.PlayManager;

public abstract class Figure {
    public Block[] b = new Block[0];
    public int arrayLength;
    public int arrayWidth;
    int autoDropCounter = 0;

    public Block[] create(Color c, int arrayWidth) {
        this.arrayWidth = arrayWidth;
        this.arrayLength = arrayWidth * arrayWidth;
        Block[] b = new Block[this.arrayLength];
        for (int i = 0; i < this.arrayLength; i++) {
            b[i] = new Block(c);
        }
        return b;
    }

    public void rotate(String dummy) {
        //TODO: Event/ EventHandler nach ButtonClick auflösen und jeweiliger Funktion zuordnen
        if (dummy.equals("Left")) {
            rotateLeft();
        } else if (dummy.equals("Right")) {
            rotateRight();
        }
    }

    private void rotateLeft() {             //counter-clockwise rotation
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
    }

    private void rotateRight() {            //clockwise rotation
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
    }


    public void draw(Graphics2D g2) {
        int o = 0;
        for (int i = 0; i < this.arrayLength; i++) { // loop over index
            if (b[i].visible) { //wich blocks are visible
                g2.setColor(b[i].c);    //color from figur
                g2.fillRect(b[0].x + ((i - o * this.arrayWidth) * Block.SIZE), b[0].y + (o * Block.SIZE), Block.SIZE, Block.SIZE);  //draw blocks
                g2.setColor(Color.BLACK);   //border color black
                g2.drawRect(b[0].x + ((i - o * this.arrayWidth) * Block.SIZE), b[0].y + (o * Block.SIZE), Block.SIZE, Block.SIZE);  //draw border
            }
            if (i == (this.arrayWidth * (o + 1)) - 1) { // if i is longer than array width then go to next row
                o++;
            }
        }
    }

    public void update() {
        autoDropCounter++;
        if (autoDropCounter == PlayManager.dropInterval) {
            b[0].y += Block.SIZE;
            autoDropCounter = 0;
        }
    }

    public void updateXY(int direction) {

    }

    public void setVisible(int[] blockIndices) {
        Set<Integer> set = new HashSet<>();
        for (int num : blockIndices) {
            set.add(num);
        }
        System.out.println("TEST  :   " + set);
        for (int i = 0; i < b.length; i++) {
            b[i].visible = set.contains(i); //Nur übergebene Indizes sind nun sichtbar
        }
    }

    public void setXY(int x, int y) {

    }
}
