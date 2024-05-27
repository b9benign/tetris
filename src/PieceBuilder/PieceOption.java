package src.PieceBuilder;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Set;

public class PieceOption extends JButton {
    private final int identifier;

    public PieceOption(int identifier) {
        this.identifier = identifier;

        setBackground(Color.BLACK);
        setBorderPainted(true);
        setBorder(new LineBorder(Color.DARK_GRAY));
        setOpaque(true);
    }

    public PieceOption() {
        this.identifier = 99999;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void updateColorFromPieceBuilderState(PieceBuilder pieceBuilder) {
        Set<Integer> selectedOptions = pieceBuilder.getSelectedOptions();
        Set<Integer> validTargets = pieceBuilder.getValidTargets();
        boolean isSelected = selectedOptions.contains(identifier);
        boolean isValid = validTargets.contains(identifier);

        if (isSelected) {
            setBackground(Color.GREEN);
            setBorder(new LineBorder(Color.BLACK));
        } else if (isValid) {
            setBackground(Color.BLACK);
            setBorder(new LineBorder(Color.WHITE));
        } else {
            setBackground(Color.BLACK);
            setBorder(new LineBorder(Color.DARK_GRAY));
        }
    }
}
