package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by kafim on 14/04/2017.
 */
public class AskDialog extends JDialog implements ActionListener{
    private JComboBox sizeX, sizeY;
    private JLabel lsizeX, lsizeY;
    private JButton ok;
    private int X, Y;

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    private boolean over = false;

    @Override
    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    @Override
    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public AskDialog(JFrame parent, String title, boolean modal){
        super(parent, title, modal);
        this.setSize(300, 100);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        JPanel panelQ = new JPanel();
        GridLayout gl = new GridLayout(2, 2);
        panelQ.setLayout(gl);
        add(panelQ,BorderLayout.CENTER);
        ok = new JButton("Valider");
        add(ok, BorderLayout.SOUTH);
        lsizeX = new JLabel("Taille X");
        lsizeY = new JLabel("Taille Y");
        sizeX = new JComboBox();
        sizeY = new JComboBox();

        panelQ.add(lsizeX);
        panelQ.add(sizeX);
        panelQ.add(lsizeY);
        panelQ.add(sizeY);
        sizeX.addItem("6");
        sizeX.addItem("7");
        sizeX.addItem("8");
        sizeX.addItem("9");
        sizeX.addItem("10");
        sizeX.addItem("11");
        sizeX.addItem("12");
        sizeX.addItem("13");
        sizeY.addItem("6");
        sizeY.addItem("7");
        sizeY.addItem("8");
        sizeY.addItem("9");
        sizeY.addItem("10");
        sizeX.setSelectedIndex(7);
        sizeY.setSelectedIndex(4);
        ok.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        X = (int)Integer.valueOf(sizeX.getSelectedItem().toString());
        Y = (int)Integer.valueOf(sizeY.getSelectedItem().toString());
        over = true;
        System.out.println(sizeX.getSelectedItem());
        setVisible(false);
    }
}
