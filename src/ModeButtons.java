package src;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class ModeButtons extends JButton{
    public static int modeButtonWidth = 400;
    public static int modeButtonHeight = 100;
    
    public ModeButtons(int mode){

        setBackground(Color.gray);
        setBorderPainted(true);
        setBorder(new LineBorder(Color.darkGray));
        setOpaque(true);
        setVisible(true);
        setText("Gamemode " + mode);
    }
}
