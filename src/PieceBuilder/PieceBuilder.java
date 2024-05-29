package src.PieceBuilder;

import src.Blocks.Figure;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PieceBuilder extends JPanel {

    private static final int PANEL_HEIGHT = 480;
    private static final int PANEL_WIDTH = 480;
    private static final int BUILDER_SIZE = 5;

    private final PieceOption[] options = new PieceOption[BUILDER_SIZE*BUILDER_SIZE];
    private final Set<Integer> selectedOptions = new HashSet<>();
    private final Set<Integer> validTargets = new HashSet<>();
    private int availableOptionsAmount;

    public PieceBuilder() {
        clearCurrent();
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(new GridLayout(BUILDER_SIZE, BUILDER_SIZE));

        create();
        availableOptionsAmount = 13;
        fetchValidTargets();
    }

    public static int getPanelWidth() {
        return PANEL_WIDTH;
    }

    public static int getPanelHeight() {
        return PANEL_HEIGHT;
    }

    public void clearCurrent() {
        selectedOptions.clear();
        selectedOptions.add(12);
        availableOptionsAmount = 13;
        fetchValidTargets();
        create();
    }

    public Figure createCustomPieceFromCurrentBuilder() {

        int adjustedSize = getLargestSideOfOptions();
        PieceOption[] minimizedOptions = minimizeOptionsWhitespace(adjustedSize);
        int[] visibleBlocks = mapOptionsToIndices(minimizedOptions);

        clearCurrent();
        return new Figure(fetchRandomColor(), adjustedSize, visibleBlocks);
    }

    private void create() {
        removeAll();
        for (int i = 0; i < BUILDER_SIZE * BUILDER_SIZE; i++) {
            PieceOption pieceOption = new PieceOption(i);
            pieceOption.addActionListener(e -> {
                PieceOption pieceOption1 = (PieceOption) e.getSource();
                handleOptionClick(pieceOption1);
            });
            pieceOption.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            options[i] = pieceOption;
            add(pieceOption);
        }
        updatePieceOptionsFromPieceBuilderState();
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
        for (PieceOption pieceOption : options) {
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
        for(int selectedOption : selectedOptions) {
            int[] neighbors = {
                    selectedOption - BUILDER_SIZE,
                    selectedOption + BUILDER_SIZE,
                    selectedOption - 1,
                    selectedOption + 1,
            };
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

    private PieceOption[] minimizeOptionsWhitespace(int newSize) {
        if (newSize == BUILDER_SIZE) return options;

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
        Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.PINK, Color.BLACK };
        Random random = new Random();
        int randomIndex = random.nextInt(colors.length);
        return colors[randomIndex];
    }
}
