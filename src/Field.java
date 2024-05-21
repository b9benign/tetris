package src;

import java.awt.Color;
import java.awt.Graphics2D;

import src.Blocks.Block;

public class Field {
    public static int WIDTH = 360;
    public static int HEIGHT = 600;
    public static int BlockSize = 30;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;
    Block[][] FieldArray = new Block[][]{   };
    public Field(){
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH - BlockSize;
        top_y = 50;
        bottom_y = top_y + HEIGHT - BlockSize;
        Block Block1 = new Block(Color.GRAY);
        Block Block2 = new Block(Color.GRAY);
        Block Block3 = new Block(Color.GRAY);
        Block Block4 = new Block(Color.GRAY);
        Block Block5 = new Block(Color.GRAY);
        Block Block6 = new Block(Color.GRAY);
        Block Block7 = new Block(Color.GRAY);
        Block Block8 = new Block(Color.GRAY);
        Block Block9 = new Block(Color.GRAY);
        Block Block10 = new Block(Color.GRAY);
        Block Block11 = new Block(Color.GRAY);
        Block Block12 = new Block(Color.GRAY);

        Block[] Line1 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line2 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line3 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line4 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line5 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line6 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line7 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line8 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line9 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line10 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line11 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line12 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line13 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line14 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line15 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line16 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line17 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line18 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line19 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};
        Block[] Line20 = new Block[]{Block1, Block2, Block3, Block4, Block5, Block6, Block7, Block8, Block9, Block10, Block11, Block12};

        FieldArray = new Block[][] {Line1, Line2, Line3, Line4, Line5, Line6, Line7, Line8, Line9, Line10, Line11, Line12, Line13, Line14, Line15, Line16, Line17, Line18, Line19, Line20};
    }
    public void update(){

    }
    public void draw(Graphics2D g2){
        //Field Blocks
        int i = 0;
        int o = 0;
        while(HEIGHT-(o*BlockSize)>0){
            while(WIDTH-(i*BlockSize)>0){
                g2.setColor(FieldArray[o][i].c);
                g2.fillRect(left_x+(i*BlockSize), bottom_y-(o*BlockSize), BlockSize, BlockSize);
                FieldArray[o][i].x=left_x+(i*BlockSize);
                FieldArray[o][i].y=bottom_y-(o*BlockSize);
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
