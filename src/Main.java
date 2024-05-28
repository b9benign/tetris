package src;

import javax.swing.JFrame;

public class Main {
    public static int gameMode = 0;
    public static JFrame window = new JFrame("Tetris");
    static Menu menu = new Menu();
    static Game game = new Game();
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
            window.remove(game);
            window.validate();
        }else{
            game = new Game();
            window.add(game);
            window.remove(menu);
            window.validate();
            game.launch();
        }
    }
}
