package src.Blocks;

import java.awt.Color;
import java.awt.Rectangle;

public class Block extends Rectangle{
    
    public int x,y;
    public static final int SIZE = 30;
    public Color c;
    public boolean visible = false;

    public Block(Color c){
        this.c = c;
    }
}
