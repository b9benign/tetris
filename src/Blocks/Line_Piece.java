package src.Blocks;
import java.awt.Color;
public class Line_Piece extends Figure{
    //[][][][]
    public Line_Piece(){
        //get Color by Level
        b = create(Color.CYAN, 4);
        setVisible(new int[]{2, 6, 10, 14});
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
}
