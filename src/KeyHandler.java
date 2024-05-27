package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public static boolean upPressed, downPressed, rightPressed, leftPressed, keyQPressed, keyEPressed, pausePressed;
    @Override
    public void keyTyped(KeyEvent e) {
    }

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
            }else{
                pausePressed = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
