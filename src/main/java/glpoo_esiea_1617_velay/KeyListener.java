package glpoo_esiea_1617_velay;

import java.awt.event.KeyEvent;

/**
 * Created by kafim on 16/04/2017.
 */
public class KeyListener implements java.awt.event.KeyListener{

    EditorGardenUI carte;

    public KeyListener(EditorGardenUI carte){
        this.carte = carte;
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
