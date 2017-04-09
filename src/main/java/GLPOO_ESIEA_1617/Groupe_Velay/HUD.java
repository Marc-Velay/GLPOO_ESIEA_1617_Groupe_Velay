package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by mat on 02/04/2017.
 */
public class HUD extends JPanel {
    public HUD(){

    }

    public void init(ArrayList<Kid> listKid){
        this.setPreferredSize(new Dimension(700,300));
        this.setLayout(new GridLayout(listKid.size(), 2));
        for (Kid kid : listKid){
            kid.getJname().setText(kid.getName());
            this.add(kid.getJname());
            this.add(kid.getjBar());
        }
        this.setVisible(true);
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        repaint();
    }
}
