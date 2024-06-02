package src;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

    /**
     * This class represents the <code>JButtons</code> for each Gamemode in the Menu screen.
     *
     * @author Richard
     * @version 2.0
     * 
     * @see JButton
     */
    public class ModeButtons extends JButton{
    public static int modeButtonWidth = 400;
    public static int modeButtonHeight = 100;
    
    /**
     * Contructor for ModeButtons class.
     * It sets all standard Variables and takes an int based on the Gamemode variable(1,2 or 3).
     * 
     * @param mode
     */
    public ModeButtons(int mode){

        setBackground(Color.gray);
        setBorderPainted(true);
        setBorder(new LineBorder(Color.darkGray));
        setOpaque(true);
        setVisible(true);
        setText("Gamemode " + mode);
    }
}
