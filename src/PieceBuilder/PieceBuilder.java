package src.PieceBuilder;

import src.Blocks.Figure;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This class renders a five by five field of clickable <code>PieceOption</code> buttons. It serves as a simple drawing board
 * for creating custom Tetris pieces. <code>PieceBuilder</code> provides automated target validation, click-event handling,
 * and helper functions that transform its current selection of <code>PieceOption</code> into a valid
 * and optimized instance of <code>Figure</code> - a Tetris piece.
 * <p>
 * It is intended to be used within a <code>CustomPiecesCreationPanel</code>.
 * </p>
 *
 * @author Jannis
 * @version 1.0
 * @see PieceOption
 * @see Figure
 * @see JPanel
 * @see CustomPiecesCreationPanel
 */

public class PieceBuilder extends JPanel {

    private static final int PANEL_HEIGHT = 480;
    private static final int PANEL_WIDTH = 480;
    private static final int BUILDER_SIZE = 5;

    private final PieceOption[] options = new PieceOption[BUILDER_SIZE*BUILDER_SIZE];
    private final Set<Integer> selectedOptions = new HashSet<>();
    private final Set<Integer> validTargets = new HashSet<>();
    private int availableOptionsAmount;

    /**
     * Constructs an instance of <code>PieceBuilder</code> that sets its default state by leveraging helper functions
     * that clear any pre-existing state, set the preferred <code>JPanel</code> size, create the matrix/ field of <code>PieceOptions</code>
     * and calculate valid targets.
     *
     * @see PieceOption
     * @see JPanel
     */
    public PieceBuilder() {
        clearCurrent();
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(new GridLayout(BUILDER_SIZE, BUILDER_SIZE));
        create();
        availableOptionsAmount = 9;
        fetchValidTargets();
    }

    /**
     * Returns the static width of this <code>PieceBuilder's</code> <code>JPanel</code>.
     *
     * @return the width of the JPanel.
     * @see JPanel
     */
    public static int getPanelWidth() {
        return PANEL_WIDTH;
    }

    /**
     * Returns the static height of this <code>PieceBuilder's</code> <code>JPanel</code>.
     *
     * @return the height of the JPanel.
     * @see JPanel
     */
    public static int getPanelHeight() {
        return PANEL_HEIGHT;
    }

    /**
     * Resets this <code>PieceBuilder's</code> state to its default. Resets the selection to the standard value, calculates valid
     * targets anew and recreates the matrix/ field of <code>PieceOptions</code>.
     *
     * @see PieceOption
     */
    public void clearCurrent() {
        selectedOptions.clear();
        selectedOptions.add(12);
        availableOptionsAmount = 13;
        fetchValidTargets();
        create();
    }

    /**
     * Accesses <code>PieceBuilder's</code> current selection, cuts unused white space in the <code>PieceOption</code> matrix/ field
     * and returns the smallest possible instance of <code>Figure</code> with a random color. This also resets the <code>PieceBuilder</code>
     * state to instantly enable new creations.
     *
     * @return <code>Figure</code>
     * @see Figure
     */
    public Figure createCustomPieceFromCurrentBuilder() {

        int adjustedSize = getLargestSideOfOptions();
        PieceOption[] minimizedOptions = minimizeOptionsWhitespace(adjustedSize);
        int[] visibleBlocks = mapOptionsToIndices(minimizedOptions);

        clearCurrent();
        return new Figure(fetchRandomColor(), adjustedSize, visibleBlocks);
    }

    private void create() {
        removeAll();
        for (int i = 0; i < BUILDER_SIZE * BUILDER_SIZE; i++) {                         //create a 5x5 field of PieceOptions
            PieceOption pieceOption = new PieceOption(i);
            pieceOption.addActionListener(e -> {
                PieceOption pieceOption1 = (PieceOption) e.getSource();
                handleOptionClick(pieceOption1);                                        //handle click events
            });
            pieceOption.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            options[i] = pieceOption;
            add(pieceOption);                                                           //add PieceOption to view
        }
        updatePieceOptionsFromPieceBuilderState();                                      //initial coloring
        revalidate();
        repaint();
    }

    private void handleOptionClick(PieceOption pieceOption) {
        int identifier = pieceOption.getIdentifier();
        if(selectedOptions.contains(identifier) || !validTargets.contains(identifier)) return;
        selectedOptions.add(identifier);

        fetchValidTargets();
        updatePieceOptionsFromPieceBuilderState();
    }

    private void updatePieceOptionsFromPieceBuilderState() {
        for (PieceOption pieceOption : options) {                                       //fetch association with sets for every PieceOption -> color accordingly
            int identifier = pieceOption.getIdentifier();
            boolean isSelected = selectedOptions.contains(identifier);
            boolean isValid = validTargets.contains(identifier);

            if (isSelected) {
                pieceOption.setBackground(Color.GREEN);
                pieceOption.setBorder(new LineBorder(Color.BLACK));
            } else if (isValid) {
                pieceOption.setBackground(Color.BLACK);
                pieceOption.setBorder(new LineBorder(Color.WHITE));
            } else {
                pieceOption.setBackground(Color.BLACK);
                pieceOption.setBorder(new LineBorder(Color.DARK_GRAY));
            }
        }
    }

    private void fetchValidTargets() {
        if(!validTargets.isEmpty()) validTargets.clear();
        if(availableOptionsAmount <= 0) return;

        //calculate neighboring indices of all selected options
        for(int selectedOption : selectedOptions) {
            int[] neighbors = {
                    selectedOption - BUILDER_SIZE,
                    selectedOption + BUILDER_SIZE,
                    selectedOption - 1,
                    selectedOption + 1,
            };

            //check neighbors eligibility for each selected option -> add to validTargets
            for(int neighbor : neighbors) {
                if(selectedOptions.contains(neighbor) || neighbor < 0 || neighbor > (BUILDER_SIZE*BUILDER_SIZE)) continue;
                int row = selectedOption / BUILDER_SIZE;
                int col = selectedOption % BUILDER_SIZE;
                boolean rowCondition = (row == (neighbor / BUILDER_SIZE));
                boolean colCondition = (col == (neighbor % BUILDER_SIZE));
                if(rowCondition || colCondition) validTargets.add(neighbor);
            }
        }
        availableOptionsAmount--;
    }

    private int getLargestSideOfOptions() {
        int top = BUILDER_SIZE;
        int left = BUILDER_SIZE;
        int bottom = 0;
        int right = 0;

        //find indices of outermost selections
        for(int i = 0; i < BUILDER_SIZE; i++) {
            for(int j = 0; j < BUILDER_SIZE; j++) {
                if(selectedOptions.contains(options[i * BUILDER_SIZE + j].getIdentifier())) {
                    top = Math.min(top, i);
                    left = Math.min(left, j);
                    bottom = Math.max(bottom, i);
                    right = Math.max(right, j);
                }
            }
        }

        //return the bigger value out of width and height
        return Math.max((right - left + 1), (bottom - top + 1));
    }

    private PieceOption[] minimizeOptionsWhitespace(int newSize) {
        if (newSize == BUILDER_SIZE) return options;

        /*
            if the selection can be represented by a square with a size < BUILDER_SIZE (5), resize selection to
            smaller square with a size of 5 > newSize > 0, e.g.:

                [ ][X][X][ ][ ]      ->      [X][X][ ]
                [ ][ ][X][X][ ]              [ ][X][X]
                [ ][X][X][ ][ ]              [X][X][ ]
                [ ][ ][ ][ ][ ]
                [ ][ ][ ][ ][ ]
         */

        int top = BUILDER_SIZE;
        int left = BUILDER_SIZE;
        PieceOption[] minimizedOptions = new PieceOption[newSize*newSize];

        for (int i = 0; i < BUILDER_SIZE; i++) {
            for (int j = 0; j < BUILDER_SIZE; j++) {
                if (selectedOptions.contains(options[i * BUILDER_SIZE + j].getIdentifier())) {
                    top = Math.min(top, i);
                    left = Math.min(left, j);
                }
            }
        }

        for (int i = top; i < top + newSize; i++) {
            for (int j = left; j < left + newSize; j++) {
                if (i < BUILDER_SIZE && j < BUILDER_SIZE) {
                    minimizedOptions[(i - top) * newSize + (j - left)] = new PieceOption(options[i * BUILDER_SIZE + j].getIdentifier());
                } else {
                    minimizedOptions[(i - top) * newSize + (j - left)] = new PieceOption();
                }
            }
        }

        return minimizedOptions;
    }

    private int[] mapOptionsToIndices(PieceOption[] minimizedOptions) {
        List<Integer> visibleBlocks = new ArrayList<>();

        //map set of selections to indices usable for Figure-creation
        for(PieceOption pieceOption : minimizedOptions) {
            if (selectedOptions.contains(pieceOption.getIdentifier())) {
                for (int i = 0; i < minimizedOptions.length; i++) {
                    if (minimizedOptions[i].getIdentifier() == pieceOption.getIdentifier()) visibleBlocks.add(i);
                }
            }
        }

        return visibleBlocks.stream().mapToInt(i -> i).toArray();
    }

    private Color fetchRandomColor() {
        Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.PINK, Color.ORANGE };
        Random random = new Random();
        int randomIndex = random.nextInt(colors.length);
        return colors[randomIndex];
    }
}
