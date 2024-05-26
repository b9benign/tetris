package src.PieceBuilder;

import src.Blocks.Figure;

import java.awt.*;

public class CustomPiece extends Figure {

    public CustomPiece(int[] visibleBlocks, int arrayWidth) {
        b = create(Color.ORANGE, arrayWidth);
        setVisible(visibleBlocks);
    }
}
