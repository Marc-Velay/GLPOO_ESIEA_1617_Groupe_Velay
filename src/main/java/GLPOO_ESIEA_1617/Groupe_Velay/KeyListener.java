package GLPOO_ESIEA_1617.Groupe_Velay;

import java.awt.event.KeyEvent;

/**
 * Created by kafim on 16/04/2017.
 */
public class KeyListener implements java.awt.event.KeyListener{

    Editeur carte;

    public KeyListener(Editeur carte){
        this.carte = carte;
        System.out.println("INIKEY");
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("s : " + e.getKeyCode());
    }

    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
}
