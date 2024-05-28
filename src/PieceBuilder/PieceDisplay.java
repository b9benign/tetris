package src.PieceBuilder;

import src.Blocks.Figure;
import src.Blocks.L_Piece;

import javax.swing.*;
import java.awt.*;

public class PieceDisplay extends JPanel {
    Figure piece;
    private final int displaySize, posX, posY;

    public PieceDisplay(int displaySize, int posX, int posY) {
        setVisible(true);
        this.displaySize = displaySize;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        draw(g2d);
    }

    public void draw(Graphics2D g2d) {
        Figure piece = new L_Piece();

        super.paintComponent(g2d);
        piece.setXY( posX + 50, posY + 50);
        piece.draw(g2d);
        g2d.setColor(Color.GRAY);
        g2d.drawRect(posX, posY, displaySize, displaySize);
    }
}
