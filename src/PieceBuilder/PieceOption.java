package src.PieceBuilder;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class PieceOption extends JButton {
    private final int identifier;
    Set<Integer> selectedOptions = new HashSet<>();
    Set<Integer> validTargets = new HashSet<>();

    public PieceOption(int identifier) {
        this.identifier = identifier;
    }

    public PieceOption() {
        this.identifier = 99999;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void updateColorFromPieceBuilderState(PieceBuilder pieceBuilder) {
        selectedOptions = pieceBuilder.getSelectedOptions();
        validTargets = pieceBuilder.getValidTargets();
        boolean isSelected = selectedOptions.contains(identifier);
        boolean isValid = validTargets.contains(identifier);

        if (isSelected) {
            setBackground(Color.RED);
            setBorder(new LineBorder(Color.BLACK));
        } else if (isValid) {
            setBackground(Color.BLACK);
            setBorder(new LineBorder(Color.WHITE));
        } else {
            setBackground(Color.BLACK);
            setBorder(new LineBorder(Color.DARK_GRAY));
        }
    }

    public void clearColorStateCache() {
        selectedOptions.clear();
        validTargets.clear();
        setBackground(Color.BLACK);
        setBorder(new LineBorder(Color.DARK_GRAY));
        repaint();
    }
}
