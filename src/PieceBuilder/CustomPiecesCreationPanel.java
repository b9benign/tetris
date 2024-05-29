package src.PieceBuilder;

import src.Blocks.Figure;
import src.KeyHandler;
import src.Main;
import src.ModeButtons;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;


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

    Thread pieceCreationThread;
    PieceBuilder pieceBuilder;
    PieceDisplay[] pieceDisplays;


    public CustomPiecesCreationPanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setLayout(null);
        setFocusable(true);

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
        update();
    }

    public void update() {}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        initializePieceBuilder();
        initializePieceDisplays(g);
        initializeButtons();
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

    private void initializePieceDisplays(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        pieceDisplays = new PieceDisplay[7];
        int displayFieldSize = 3;
        int x = 1;
        int y = 1;
        for (int i = 0; i < 7; i++) {
            int inversion = 6 - i;
            pieceDisplays[inversion] = new PieceDisplay(
                    pieceDisplaySize,
                    pieceDisplaySize * (3 - x),
                    PANEL_HEIGHT - (pieceDisplaySize * y)
            );
            pieceDisplays[inversion].draw(g2d);
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
            Main.main(null);
            KeyHandler.pausePressed=false;
        });

        buttons[1] = new JButton("Add Piece"); //Add Piece
        buttons[1].setBounds(PANEL_WIDTH - (2 * pieceDisplaySize), PANEL_HEIGHT-(buttonHeight), pieceDisplaySize, buttonHeight);
        buttons[1].addActionListener(e -> {
            System.out.println("PANEL_SELECTION: " + pieceBuilder.getSelectedOptions());
            System.out.println("PANEL_AVAILABLE: " + pieceBuilder.getAvailableOptionsAmount());
            customPieces.add(pieceBuilder.createCustomPieceFromCurrentBuilder());

            pieceDisplays[0].setPiece(customPieces.getFirst());

            System.out.println("CUSTOM PIECES: " + customPieces);
        });

        buttons[2] = new ModeButtons(4);
        buttons[2].setBounds(PANEL_WIDTH - pieceDisplaySize, PANEL_HEIGHT-(buttonHeight), pieceDisplaySize, buttonHeight);
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
