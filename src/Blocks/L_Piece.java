package src.Blocks;

import java.awt.Color;

public class L_Piece extends Figure {
    //      [3]
    //[4][5][6]

    public L_Piece(){
        //get Color by Level
        b = create(Color.orange, 3);
        setVisible(new int[]{2, 3, 4, 5}); //0-Indizierung beachten
        //rotate("Right");
        rotate("Left"); //Nur Testweise an dieser Stelle-> sollte später vermutlich auf Game-Ebene getriggert werden
        rotate("Left");
        rotate("Right");
    }

    public void setXY(int x, int y){
        b[0].x = x;
        b[0].y = y;
    }
}
