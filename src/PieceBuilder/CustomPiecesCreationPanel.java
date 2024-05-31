package src.PieceBuilder;

import src.Blocks.Figure;
import src.KeyHandler;
import src.Main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class CustomPiecesCreationPanel extends JPanel implements Runnable {

    public static final int PANEL_WIDTH = 1280;
    public static final int PANEL_HEIGHT = 720;

    private final ArrayList<Figure> customPieces;
    private int customPieceAmount;

    private final int pieceDisplaySize = PieceBuilder.getPanelHeight() / 2;
    private final int[][] pieceDisplaysCoords;

    Thread pieceCreationThread;
    PieceBuilder pieceBuilder;

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
        int displayFieldSize = 3;
        int x = 1;
        int y = 1;
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

        buttons[2] = new JButton("Play");
        buttons[2].setBounds(PANEL_WIDTH - pieceDisplaySize, PANEL_HEIGHT-(buttonHeight), pieceDisplaySize, buttonHeight);
        buttons[2].setEnabled(false);
        buttons[2].addActionListener(e -> {
            System.out.println("SIZE: " + customPieces.size());
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

    private void drawDisplayFrameAtDisplayCoords(int posX, int posY) {
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
