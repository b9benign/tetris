package src.PieceBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class PieceBuilder extends JPanel {

    private static final int BUILDER_PANEL_HEIGHT = 400;
    private static final int BUILDER_PANEL_WIDTH = 400;
    private static final int BUILDER_SIZE = 5;

    private final PieceOption[] options = new PieceOption[BUILDER_SIZE*BUILDER_SIZE];
    private final Set<Integer> selectedOptions = new HashSet<>();
    private final Set<Integer> validTargets = new HashSet<>();
    private final List<CustomPiece> customPieces = new ArrayList<>(); //outsource to parent
    private int availableOptionsAmount;

    public PieceBuilder() {
        clearAll();
        setPreferredSize(new Dimension(BUILDER_PANEL_WIDTH, BUILDER_PANEL_HEIGHT));
        setLayout(new GridLayout(BUILDER_SIZE, BUILDER_SIZE));
        create();
        availableOptionsAmount = 5;
        fetchValidTargets();
    }

    public void clearCurrent() {
        selectedOptions.clear();
        selectedOptions.add(12);
        availableOptionsAmount = 5;
        fetchValidTargets();
    }

    public void clearAll() {
        customPieces.clear();
        clearCurrent();
    }

    public void create() {
        removeAll();
        for (int i = 0; i < BUILDER_SIZE * BUILDER_SIZE; i++) {
            PieceOption pieceOption = new PieceOption(i);
            pieceOption.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PieceOption pieceOption = (PieceOption) e.getSource();
                    handleOptionClick(pieceOption);
                }
            });
            options[i] = pieceOption;
            add(pieceOption);
        }
        updatePieceOptionsFromPieceBuilderState();
        revalidate();
        repaint();
    }

    public void handleOptionClick(PieceOption pieceOption) {
        int identifier = pieceOption.getIdentifier();
        if(validTargets.contains(identifier)) {
            selectedOptions.add(identifier);
        }
        fetchValidTargets();
        updatePieceOptionsFromPieceBuilderState();
    }

    public Set<Integer> getSelectedOptions() {
        return selectedOptions;
    }

    public Set<Integer> getValidTargets() {
        return validTargets;
    }

    private void updatePieceOptionsFromPieceBuilderState() {
        for (PieceOption pieceOption : this.options) {
            pieceOption.updateColorFromPieceBuilderState(this);
        }
    }

    private void fetchValidTargets() {
        if(!validTargets.isEmpty()) validTargets.clear();
        if(availableOptionsAmount <= 0) {
            minimizeOptionsWhitespace();
            return;
        }
        for(int selectedOption : selectedOptions) {
            int[] neighbors = {
                    selectedOption - BUILDER_SIZE,
                    selectedOption + BUILDER_SIZE,
                    selectedOption - 1,
                    selectedOption + 1,
            };
            for(int neighbor : neighbors) {
                if(selectedOptions.contains(neighbor) || neighbor < 0 || neighbor > (BUILDER_SIZE*BUILDER_SIZE)) continue;
                validTargets.add(neighbor);
            }
        }
        availableOptionsAmount--;
    }

    //TODO: minify -> takes in field, cuts out whitespace if possible and returns (smaller) field;
    private PieceOption[] minimizeOptionsWhitespace() {

        int newSize = getLargestSideOfOptions();
        if (newSize == BUILDER_SIZE) return options; //no optimizations possible -> return options

        return createMinimizedOptionsFromSizeAdjustment(newSize);
    }

    //TODO: createCustomPiece -> needs minify -> returns CustomPiece(int[] visibleBlocks, int arrayWidth);
        //needs to be button that adds returned CustomPiece to array of CustomPieces for game mode 2
    private int getLargestSideOfOptions() {
        int top = BUILDER_SIZE;
        int left = BUILDER_SIZE;
        int bottom = 0;
        int right = 0;

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
        return Math.max((right - left + 1), (bottom - top + 1));
    }

    private PieceOption[] createMinimizedOptionsFromSizeAdjustment(int newSize) {
        return options; //placeholder
    }

    public CustomPiece createCustomPieceFromCurrentBuilder() {
        PieceOption[] minifiedOptions = minimizeOptionsWhitespace();
        int[] visibleBlocks = mapOptionsToIndices();
        int arrayWidth = 4;
        return new CustomPiece(visibleBlocks, arrayWidth);
    }

    private int[] mapOptionsToIndices() {
        return new int[]{ 1, 2 }; //placeholder
    }

    //TODO: CustomPiecesCreationPanel -> parent of PieceBuilder, takes in 7 custom pieces
        //can call "pieceBuilder.clearCurrent()" and "this.reset()"
        //if all 7 spaces filled: enable StartGame-Button -> new Panel with GameMode2(CustomPieces)
        //ReturnButton to main screen/ game mode selection
    //TODO: PieceDisplay
        //takes in X extends Figure -> display it for use in CustomPiecesCreationPanel and "NextPiece" in every game mode
}
