package src.Blocks;

import java.awt.Color;
import java.awt.Rectangle;
/**
 * This class is a single Block (<code>Figure</code> is created out of 4 Blocks).
 * Every Block has a Color, x and y and the boolean visible.
 *
 * @author Richard
 * @version 2.0
 * 
 * @see Block
 */
public class Block extends Rectangle{
    
    public int x,y;
    public static final int SIZE = 30;
    public Color c;
    public boolean visible = false;

    /**
     * creates a Block with the <code>Color</code> c.
     * 
     * @param c
     * @see Color
     */
    public Block(Color c){
        this.c = c;
    }
}
