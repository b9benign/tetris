package src.Blocks;
import java.awt.Color;
public class Reverse_Squiggly_Piece extends Figure{
    //[][]
    //  [][]
    public Reverse_Squiggly_Piece(){
        //get Color by Level
        b = create(Color.pink, 3);
        setVisible(new int[]{0, 1, 4, 5});
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
