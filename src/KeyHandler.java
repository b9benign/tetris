package src;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler extends JFrame implements KeyListener {
    public static boolean upPressed, downPressed, rightPressed, leftPressed, keyQPressed, keyEPressed, pausePressed;
    @Override
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("HANDLER");
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
