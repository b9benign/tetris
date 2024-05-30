package src.PieceBuilder;

import javax.swing.*;

/**
 * This is a helper-class providing easy access to a fixed numeric value, its identifier.
 * It is intended that any instance of this class is assigned a unique identifier upon creation, which, in turn,
 * is meant to represent a counter value in its parent (e.g. an index within an array of JButtons).
 * <br>
 * In this case, a <code>PieceOption</code> represents a button within an array of buttons rendered in their parent,
 * <code>PieceBuilder</code>. Each index of that array translates into one <code>PieceOption</code> with a
 * corresponding identifier.
 *
 * @author Jannis
 * @version 1.0
 * @see PieceBuilder
 * @see javax.swing.JButton
 */

public class PieceOption extends JButton {
    private final int identifier;

    /**
     * Constructs a PieceOption with a specified identifier.
     * It is the intended standard to create instances using this constructor whenever feasible.
     *
     * @param identifier the unique identifier for this PieceOption
     */
    public PieceOption(int identifier) {
        this.identifier = identifier;
    }

    /**
     * Constructs a PieceOption with a placeholder identifier.
     * This constructor is not meant to be used unless an instance cannot be assigned an identifier at creation.
     * The placeholder identifier is assigned to retain expected behaviors and avoid NullPointerExceptions.
     */
    public PieceOption() {
        this.identifier = 99999999;
    }
    
    /**
     * Returns the numeric identifier of this PieceOption.
     * @return numeric identifier
     */
    public int getIdentifier() {
        return identifier;
    }
}
