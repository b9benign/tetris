package src.PieceBuilder;

import javax.swing.*;

public class PieceOption extends JButton {
    private final int identifier;

    public PieceOption(int identifier) {
        this.identifier = identifier;
    }

    public PieceOption() {
        this.identifier = 99999;
    }

    public int getIdentifier() {
        return identifier;
    }
}
