package src;

import javax.swing.JFrame;
public class Main {
    public static int gameMode = 0;
    public static void main (String[] args) {

        JFrame window = new JFrame("Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        Menu menu = new Menu();
        GameMode1 gm1 = new GameMode1();
        GameMode2 gm2 = new GameMode2();
        GameMode3 gm3 = new GameMode3();

        if(gameMode==0){
            window.remove(gm1);
            window.remove(gm2);
            window.remove(gm3);
            window.add(menu);
            window.pack();
            //menu.update();
            //menu.launchMenu();
        }else if(gameMode==1){
            window.remove(menu);
            window.add(gm1);
            window.pack();
            gm1.launchMode1();
        }else if(gameMode==2){
            window.remove(menu);
            window.add(gm2);
            window.pack();
            gm2.launchMode2();
        }else if(gameMode==3){
            window.remove(menu);
            window.add(gm3);
            window.pack();
            gm3.launchMode3();
        }
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        System.out.println("main");
    }
}
