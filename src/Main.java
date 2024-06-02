package src;

import src.Blocks.Figure;
import src.PieceBuilder.CustomPiecesCreationPanel;

import javax.swing.JFrame;
import java.util.ArrayList;

/**
 * The Main class creates the <code>JFrame</code> wich displayes all <code>JPanels</code>.
 * It inizializes the <code>Menu</code>, <code>Game</code>, <code>CustomPiecesCreationPanel</code> and the <code>ArrayList</code> with all <code>Figures</code>.
 *
 * @author Richard
 * @version 2.0
 * 
 * @see Menu
 * @see Game
 * @see CustomPiecesCreationPanel
 * @see ArrayList
 * @see Figure
 */
public class Main {
    public static int gameMode = 0;
    private static JFrame window = new JFrame("Tetris");
    static Menu menu = new Menu();
    static Game game = new Game();
    static CustomPiecesCreationPanel panel = new CustomPiecesCreationPanel();
    public static ArrayList<Figure> figureList = new ArrayList<>();

    /**
     * the main function sets the standard Variabels for the <code>JFrame</code>.
     * It also calls the setWindow function.
     * @see JFrame
     */
    public static void main (String[] args) {

        setWindow();
        window.pack();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    /**
     * This function sets the Variable everything up base on the gameMode variable.
     * It initializes the gameMode / the menu.
     */
    public static void setWindow(){
        
        if(gameMode==0){
            menu = new Menu();
            window.add(menu);
            window.remove(game);
            window.remove(panel);
            window.validate();
        } else if (gameMode==2) {
            panel = new CustomPiecesCreationPanel();
            window.add(panel);
            window.remove(menu);
            window.validate();
            panel.launch();
        } else if (gameMode==4) {
            game = new Game(figureList);
            window.remove(menu);
            window.remove(panel);
            window.add(game);
            window.validate();
            game.launch();
            game.requestFocusInWindow();
        } else {
            game = new Game();
            window.add(game);
            window.remove(menu);
            window.validate();
            game.launch();
        }
    }
}
