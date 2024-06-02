package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * This class implements <code>KeyListener</code> and changes public booleans based on the Keys pressed on the Keyboard.
 *
 * @author Richard
 * @version 1.0
 * 
 * @see KeyListener
 */
public class KeyHandler implements KeyListener {

    public static boolean upPressed, downPressed, rightPressed, leftPressed, keyQPressed, keyEPressed, pausePressed, mutePressed;
    /**
     * We dont use this function, but it is needed as KeyHandler implements <code>KeyListener</code>.
     * 
     * @see KeyListener
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * If a Button is pressed, the corresponding boolean is set to true.
     * special Events are teh Escape and M button, as they change to false on a second press on their Keys
     * 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W){
            upPressed = true;
        }
        if (code == KeyEvent.VK_S){
            downPressed = true;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if (code == KeyEvent.VK_Q){
            keyQPressed = true;
        }
        if (code == KeyEvent.VK_E){
            keyEPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE){
            if(pausePressed) {
                pausePressed = false;
                Sound.music.loop(0);
            }else{
                pausePressed = true;
                Sound.music.stop();
            }
        }
        if (code == KeyEvent.VK_M){
            if(mutePressed){
                mutePressed=false;
                Sound.music.loop(0);
            }else{
                mutePressed=true;
                Sound.music.stop();
            }
        }
    }

    /**
     * We dont use this function, but it is needed as KeyHandler implements <code>KeyListener</code>.
     * 
     * @see KeyListener
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
