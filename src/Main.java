package src;

import src.PieceBuilder.CustomPiecesCreationPanel;

import javax.swing.JFrame;

public class Main {
    public static int gameMode = 0;
    public static JFrame window = new JFrame("Tetris");
    static Menu menu = new Menu();
    static GameMode1 gm1 = new GameMode1();
    //static GameMode2 gm2 = new GameMode2();
    static CustomPiecesCreationPanel creationPanel = new CustomPiecesCreationPanel();
    static GameMode3 gm3 = new GameMode3();
    public static void main (String[] args) {

        setWindow();
        window.pack();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    public static void setWindow(){

        if(gameMode==0){
            menu = new Menu();
            window.add(menu);
            window.remove(gm1);
            window.validate();
        }else if(gameMode==1){
            gm1 = new GameMode1();
            window.add(gm1);
            window.remove(menu);
            window.validate();
            gm1.launchMode1();
        }else if(gameMode==2){
            creationPanel = new CustomPiecesCreationPanel();

            //gm2 = new GameMode2();
            //window.add(gm2);
            window.add(creationPanel);
            window.remove(menu);
            window.validate();
            //after custom figure draw
            creationPanel.launch();

        }else if(gameMode==3){
            gm3 = new GameMode3();
            window.add(gm3);
            window.remove(menu);
            window.validate();
            gm3.launchMode3();
        }
    }
}