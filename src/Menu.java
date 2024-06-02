package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * This class extends the <code>JPanel</code>, sets all standard Variables for the JPanel.
 * the Menu creates the Main Title Screen, it has 3 Buttons, on for each Gamemode.
 * Based on the Button pressed, the Game start with the Gamemode you chose.
 *
 * @author Richard
 * @version 1.0
 * 
 * @see JPanel
 */
public class Menu extends JPanel{
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    /**
     * Construktor for menuclass.
     * It sets the standard Variables for the <code>JPanel</code>
     * and calls createButtons.
     * 
     * @see JPanel
     */
    public Menu(){

        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setFocusable(true);

        createButtons();
    }
    /**
     * update is called 60 times per second (once per Frame),
     * it calls the repaint function.
     *
     */
    public void update(){
        repaint();
    }
    private void draw(Graphics2D g2){
        int x = 450;
        int y = 120;
        g2.setFont(g2.getFont().deriveFont(100f));
        g2.setColor(Color.PINK);
        g2.drawString("T", x+2, y);
        g2.setColor(Color.RED);
        g2.drawString("E", x+60, y);
        g2.setColor(Color.GREEN);
        g2.drawString("T", x+124, y);
        g2.setColor(Color.YELLOW);
        g2.drawString("R", x+181, y);
        g2.setColor(Color.BLUE);
        g2.drawString("I", x+246, y);
        g2.setColor(Color.MAGENTA);
        g2.drawString("S", x+268, y);
    }
    private void createButtons(){
        ModeButtons gameMode1 = new ModeButtons(1);
        gameMode1.setFocusable(false);
        gameMode1.setBounds(420, 200, 400, 50);
        gameMode1.setToolTipText("Experience the classic Tetris Fun!");
        gameMode1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Main.gameMode=1;
                Main.setWindow();
            }
        });
        add(gameMode1);

        ModeButtons gameMode2 = new ModeButtons(2);
        gameMode2.setFocusable(false);
        gameMode2.setBounds(420, 300, 400, 50);
        gameMode2.setToolTipText("Create Custom Figures and include them in your Adventure!");
        gameMode2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Main.gameMode=2;
                Main.setWindow();
            }
        });
        add(gameMode2);

        ModeButtons gameMode3 = new ModeButtons(3);
        gameMode3.setFocusable(false);
        gameMode3.setBounds(420, 400, 400, 50);
        gameMode3.setToolTipText("You like to fully Clear the Board, then you´re up for the Challenge!");
        gameMode3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Main.gameMode=3;
                Main.setWindow();
            }
        });
        add(gameMode3);
        invalidate();
    }
    /**
     * creates the g2 Variable, that is used by other draw funtions.
     * it calls the draw function with g2
     * 
     * @param g
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        draw(g2);
    }
}
