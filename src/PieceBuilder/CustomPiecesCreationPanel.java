package src.PieceBuilder;

import src.Blocks.Figure;
import src.KeyHandler;
import src.Main;
import src.ModeButtons;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


//TODO: CustomPiecesCreationPanel -> parent of PieceBuilder, takes in 7 custom pieces
//can call "pieceBuilder.clearCurrent()" and "this.reset()"
//if all 7 spaces filled: enable StartGame-Button -> new Panel with GameMode2(CustomPieces)
//ReturnButton to main screen/ game mode selection
//TODO: PieceDisplay
//takes in X extends Figure -> display it for use in CustomPiecesCreationPanel and "NextPiece" in every game mode

public class CustomPiecesCreationPanel extends JPanel implements Runnable {

    public static final int PANEL_WIDTH = 1280;
    public static final int PANEL_HEIGHT = 720;
    public final int pieceDisplaySize = PieceBuilder.getPanelHeight() / 2;
    private final ArrayList<Figure> customPieces;
    private final int[][] pieceDisplaysCoords;
    private int customPieceAmount;

    Thread pieceCreationThread;
    PieceBuilder pieceBuilder;
    PieceDisplay[] pieceDisplays;

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

    public void launch() {
        pieceCreationThread = new Thread(this);
        pieceCreationThread.start();
    }

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

    public void update() {
        revalidate();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        int displayFieldSize = 3;
        int x = 1;
        int y = 1;
        for (int i = 0; i < 7; i++) {
            int inversion = 6 - i;
            pieceDisplaysCoords[inversion][0] = pieceDisplaySize * (3 - x);
            pieceDisplaysCoords[inversion][1] = PANEL_HEIGHT - (pieceDisplaySize * y);
            if(x >= displayFieldSize) {
                x = 1;
                y++;
            } else {
                x++;
            }
        }
    }

    private void initializeButtons() {
        int buttonHeight = pieceDisplaySize / 3;
        JButton[] buttons = new JButton[5];

        buttons[0] = new JButton("Return to Menu");
        buttons[0].setBounds(0, 0, pieceDisplaySize, buttonHeight);
        buttons[0].addActionListener(e -> {
            Main.gameMode=0;
            Main.setWindow();
            KeyHandler.pausePressed=false;
        });

        buttons[1] = new JButton("Add Piece");
        buttons[1].setBounds(PANEL_WIDTH - (2 * pieceDisplaySize), PANEL_HEIGHT-(buttonHeight), pieceDisplaySize, buttonHeight);
        buttons[1].addActionListener(e -> {
            int inversion = 7 - customPieceAmount;
            if (inversion <= 0) {
                buttons[1].setEnabled(false);
                buttons[2].setEnabled(true);
                return;
            }
            customPieces.add(pieceBuilder.createCustomPieceFromCurrentBuilder());
            customPieceAmount++;

            pieceBuilder.clearCurrent();
            System.out.println("NEW_AMOUNT: " + customPieceAmount);
        });

        buttons[2] = new JButton("Play");
        buttons[2].setBounds(PANEL_WIDTH - pieceDisplaySize, PANEL_HEIGHT-(buttonHeight), pieceDisplaySize, buttonHeight);
        buttons[2].setEnabled(false);
        buttons[2].addActionListener(e -> {
            Main.figureList.clear();
            Main.figureList.addAll(customPieces);
            Main.gameMode=4;
            Main.setWindow();
        });

        buttons[3] = new JButton("Reset Current");
        buttons[3].setBounds(PANEL_WIDTH - (2 * pieceDisplaySize), 0, pieceDisplaySize, buttonHeight);
        buttons[3].addActionListener(e -> {
            pieceBuilder.clearCurrent();
        });

        buttons[4] = new JButton("Reset All");
        buttons[4].setBounds(PANEL_WIDTH - pieceDisplaySize, 0, pieceDisplaySize, buttonHeight);
        buttons[4].addActionListener(e -> {
            customPieceAmount = 0;
            customPieces.clear();
            pieceBuilder.clearCurrent();
            buttons[1].setEnabled(true);
            buttons[2].setEnabled(false);
        });

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
}
