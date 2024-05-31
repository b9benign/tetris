package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

import src.Blocks.Figure;
import src.PieceBuilder.CustomPiecesCreationPanel;

public class Game extends JPanel implements Runnable{
    
    public static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    final int FPS = 60;

    public static boolean gameOver = false;
    Thread GameThread;
    CustomPiecesCreationPanel creationPanel;
    PlayManager pm;
    Field field;
    KeyHandler KeyHandler;

    public Game() {

        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
        this.requestFocusInWindow();

        ArrayList<Figure> FigureList = new ArrayList<Figure>();
        
        FigureList.add(new Figure(Color.green, 3, new int[]{1, 2, 3, 4}));
        FigureList.add(new Figure(Color.red, 3, new int[]{0, 1, 4, 5}));
        FigureList.add(new Figure(Color.yellow, 2, new int[]{0, 1, 2, 3}));
        FigureList.add(new Figure(Color.orange, 3, new int[]{2, 3, 4, 5}));
        FigureList.add(new Figure(Color.BLUE, 3, new int[]{0, 3, 4, 5}));
        FigureList.add(new Figure(Color.magenta, 3, new int[]{1, 3, 4, 5}));
        FigureList.add(new Figure(Color.CYAN, 4, new int[]{2, 6, 10, 14}));

        pm = new PlayManager(FigureList);
        field = new Field();
        if(Main.gameMode==3){
            addRandomBlocks();
        }
    }
    private void addRandomBlocks(){
        for(int i=0; i<6;i++){
            for(int o = 0; o<12;o++){
                int rand = new Random().nextInt(10)+1;
                if(rand>4){
                    Color color = Field.getRandomColor();
                    Field.FieldArray[i][o].c = color;
                    Field.FieldArray[i][o].visible = true;
                }
            }
        }
    }

    public Game(ArrayList<Figure> figureList) {
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
        this.requestFocusInWindow();

        pm = new PlayManager(figureList);
        field = new Field();
    }

    public void launch(){
        GameThread = new Thread(this);
        if(Main.gameMode!=0){
            Sound.music.loop(0);
        }
        GameThread.start();
    }
    public void run(){
        // Game Loop
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(GameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime- lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }
    private void update(){
        if(!KeyHandler.pausePressed || gameOver){
            pm.update();
            field.update();
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        field.draw(g2);
        pm.draw(g2);
        if(KeyHandler.pausePressed){
            System.out.println("PAUSE");
            JButton backToMenu = new JButton("Back to Menu");
            backToMenu.setFocusable(false);
            backToMenu.setBounds(440, 380, 400, 50);
            backToMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Main.gameMode=0;
                KeyHandler.pausePressed=false;
                Main.setWindow();
                }
            });
            add(backToMenu);
        }
        if(!KeyHandler.pausePressed && !gameOver){
            removeAll();
        }
        if(gameOver){
            JButton gameOverButton = new JButton("Back to Menu");
            gameOverButton.setFocusable(false);
            gameOverButton.setBounds(440, 380, 400, 50);
            gameOverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Main.gameMode=0;
                gameOver=false;
                Main.setWindow();
                }
            });
            add(gameOverButton);
        }
    }
}
