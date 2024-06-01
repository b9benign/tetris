package src.PieceBuilder;

import src.Blocks.Figure;
import src.KeyHandler;
import src.Main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class leverages a <code>PieceBuilder</code> in combination with some standard JButtons in order to create a
 * screen that enables the user to create and save custom pieces short-term. These will automatically be optimized,
 * transformed and pushed into a list of <code>Figures</code> which, in turn, will be relayed to <code>Game</code>,
 * starting a game of custom Tetris.
 * <p>
 *     <code>CustomPiecesCreationPanel</code> is explicitly intended to be used between the main menu and an
 *     instance of <code>Game</code>.
 * </p>
 *
 * @author Jannis
 * @version 1.0
 * @see PieceBuilder
 * @see Figure
 * @see src.Game
 * @see JPanel
 * @see JButton
 * @see Runnable
 */

public class CustomPiecesCreationPanel extends JPanel implements Runnable {

    private static final int PANEL_WIDTH = 1280;
    private static final int PANEL_HEIGHT = 720;

    private final ArrayList<Figure> customPieces;
    private int customPieceAmount;

    private final int pieceDisplaySize = PieceBuilder.getPanelHeight() / 2;
    private final int[][] pieceDisplaysCoords;

    Thread pieceCreationThread;
    PieceBuilder pieceBuilder;

    /**
     * Constructs and instance of <code>CustomPiecesCreationPanel</code>, sets its dimensions, default state and
     * initializes its sub-components.
     *
     * @see PieceBuilder
     * @see JButton
     * @see JPanel
     */
    public CustomPiecesCreationPanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setLayout(null);
        setFocusable(true);
        setVisible(true);

        pieceDisplaysCoords = new int[7][2];
        customPieceAmount = 0;

        initializePieceBuilder();
        initializeButtons();
        initializePieceDisplaysCoords();

        customPieces = new ArrayList<>();
    }

    /**
     * Initializes a <code>Thread</code> of this <code>Runnable</code> and starts it.
     *
     * @see Thread
     * @see Runnable
     */
    public void launch() {
        pieceCreationThread = new Thread(this);
        pieceCreationThread.start();
    }

    /**
     * Runs this <code>Runnable</code> and invokes <code>update</code> for as long as its <code>Thead</code> is alive.
     *
     * @see Runnable
     * @see Thread
     */
    public void run() {
        double drawInterval = (double) 1000000000 /60;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while(pieceCreationThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime- lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1){
                update();
                delta--;
            }
        }
    }

    /**
     * Invokes revalidation and repainting of this <code>Runnable</code> for as long as its <code>Thread</code> is alive.
     *
     * @see Runnable
     * @see Thread
     */
    public void update() {
        revalidate();
        repaint();
    }

    /**
     * Custom repaint-functionality. Repaints this component and triggers update/ repainting of each <code>Figure</code>
     * that was created thus far.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see Figure
     * @see Graphics
     * @see Graphics2D
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);

        if (!customPieces.isEmpty()) {
            for (int i = 0; i < customPieces.size(); i++) {
                int posX = pieceDisplaysCoords[i][0] + pieceDisplaySize / 4;
                int posY = pieceDisplaysCoords[i][1] + pieceDisplaySize / 4;
                Figure currentPiece = customPieces.get(i);
                currentPiece.setXY(posX, posY);
                currentPiece.draw(g2d);
            }
        }
    }

    private void initializePieceBuilder() {
        pieceBuilder = new PieceBuilder();
        pieceBuilder.setBounds(
                PANEL_WIDTH-PieceBuilder.getPanelWidth(),
                (PANEL_HEIGHT / 2) - (PieceBuilder.getPanelHeight() / 2) + pieceDisplaySize / 6,
                PieceBuilder.getPanelWidth(),
                PieceBuilder.getPanelHeight());
        this.add(pieceBuilder);
    }

    private void initializePieceDisplaysCoords() {

        //setup: 3 displays per row
        int displayFieldSize = 3;
        int x = 1;
        int y = 1;

        //calculates coordinates used to display created custom pieces
        for (int i = 0; i < 7; i++) {
            int inversion = 6 - i;
            pieceDisplaysCoords[inversion][0] = pieceDisplaySize * (3 - x);
            pieceDisplaysCoords[inversion][1] = PANEL_HEIGHT - (pieceDisplaySize * y);
            drawDisplayFrameAtDisplayCoords(pieceDisplaysCoords[inversion][0], pieceDisplaysCoords[inversion][1]);
            if(x >= displayFieldSize) {
                x = 1;
                y++;
            } else {
                x++;
            }
        }
    }

    private void initializeButtons() {

        //create every necessary button and assign respective functionality
        int buttonHeight = pieceDisplaySize / 3;
        JButton[] buttons = new JButton[5];

        //return to the main menu
        buttons[0] = new JButton("Return to Menu");
        buttons[0].setBounds(0, 0, pieceDisplaySize, buttonHeight);
        buttons[0].addActionListener(e -> {
            Main.gameMode=0;
            Main.setWindow();
            KeyHandler.pausePressed=false;
        });

        //add a piece to customPieces from current PieceBuilder selection
        buttons[1] = new JButton("Add Piece");
        buttons[1].setBounds(PANEL_WIDTH - (2 * pieceDisplaySize), PANEL_HEIGHT-(buttonHeight), pieceDisplaySize, buttonHeight);
        buttons[1].addActionListener(e -> {
            int maxAmount = 7;
            Figure newPiece = pieceBuilder.createCustomPieceFromCurrentBuilder();
            customPieces.add(newPiece);
            customPieceAmount++;
            pieceBuilder.clearCurrent();

            if (customPieceAmount == maxAmount) {
                buttons[1].setEnabled(false);
                buttons[2].setEnabled(true);
            }
        });

        //relay the customPieces to "Game" and start the game via Main
        buttons[2] = new JButton("Play");
        buttons[2].setBounds(PANEL_WIDTH - pieceDisplaySize, PANEL_HEIGHT-(buttonHeight), pieceDisplaySize, buttonHeight);
        buttons[2].setEnabled(false);
        buttons[2].addActionListener(e -> {
            Main.figureList.clear();
            Main.figureList.addAll(customPieces);
            Main.gameMode=4;
            Main.setWindow();
        });

        //reset the current PieceBuilder
        buttons[3] = new JButton("Reset Current");
        buttons[3].setBounds(PANEL_WIDTH - (2 * pieceDisplaySize), 0, pieceDisplaySize, buttonHeight);
        buttons[3].addActionListener(e -> pieceBuilder.clearCurrent());

        //reset the entire CustomPiecesCreationPanel and start anew
        buttons[4] = new JButton("Reset All");
        buttons[4].setBounds(PANEL_WIDTH - pieceDisplaySize, 0, pieceDisplaySize, buttonHeight);
        buttons[4].addActionListener(e -> {
            customPieceAmount = 0;
            customPieces.clear();
            pieceBuilder.clearCurrent();
            buttons[1].setEnabled(true);
            buttons[2].setEnabled(false);
        });

        //some general button styling
        for(JButton button : buttons) {
            button.setFocusable(false);
            button.setBackground(Color.GRAY);
            button.setForeground(Color.BLACK);
            button.setBorderPainted(true);
            button.setBorder(new LineBorder(Color.BLACK));
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.add(button);
        }
    }

    private void drawDisplayFrameAtDisplayCoords(int posX, int posY) {
        //draws frames (borders) around the coordinates where custom pieces will later be displayed at

        JPanel displayFrame = new JPanel();

        displayFrame.setSize(pieceDisplaySize, pieceDisplaySize);
        displayFrame.setLocation(posX, posY);

        displayFrame.setBorder(new LineBorder(Color.DARK_GRAY));
        displayFrame.setOpaque(false);
        displayFrame.setBackground(null);

        add(displayFrame);
        repaint();
        revalidate();
    }
}
