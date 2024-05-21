package src.Blocks;

import java.awt.Color;

public class L_Piece extends Figur{
    //    []
    //[][][]

    public L_Piece(){
        //get Color by Level
        b = create(Color.orange, 3);
    }

    public void setVisible(){
        b[2].visible=true;      //    []
        b[3].visible=true;      //[][][]
        b[4].visible=true;
        b[5].visible=true;
    }
    public void setXY(int x, int y){
        b[0].x = x;
        b[0].y = y;
    }
}
