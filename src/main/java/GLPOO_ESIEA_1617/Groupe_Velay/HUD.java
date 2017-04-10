package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by mat on 02/04/2017.
 */
public class HUD extends JPanel {
    private int sizeX;
    private int sizeY;
    private int sizeHUD;
    private Image rock;
    private Image terre;
    private Image oeuf;
    private Image kidE;
    private Image kidW;
    private Image kidS;
    private Image kidN;
    private int blocSize;
    private Font font = new Font("Courier", Font.BOLD, 20);
    public HUD(){

    }
    public HUD(int sizeX, int sizeY, int blocSize, int sizeHUD){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.blocSize = blocSize;
        this.sizeHUD = sizeHUD;
        loadImages();
    }

    public void init(ArrayList<Kid> listKid){
        this.setPreferredSize(new Dimension(sizeX,sizeHUD));
        this.setLayout(new GridLayout(listKid.size(), 2));
        for (Kid kid : listKid){
            kid.getJname().setText(kid.getName());
            this.add(kid.getJname());
            this.add(kid.getjBar());
        }
        this.setVisible(true);
        repaint();
    }

    public void initEditeur(){
        this.setPreferredSize(new Dimension(sizeX,sizeHUD));
        this.setLayout(new GridLayout(4, 2));
        this.setVisible(true);
        repaint();
    }

    private void afficherImage(Image img, int x, int y, Graphics g) {
        g.drawImage(img, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        afficherImage(terre,blocSize/2,sizeY+blocSize,g);
        afficherImage(rock,3*blocSize/2,sizeY+blocSize,g);
        afficherImage(oeuf,5*blocSize/2,sizeY+blocSize,g);
        afficherImage(kidE,7*blocSize/2,sizeY+blocSize,g);
        afficherImage(kidN,9*blocSize/2,sizeY+blocSize,g);
        afficherImage(kidW,11*blocSize/2,sizeY+blocSize,g);
        afficherImage(kidS,13*blocSize/2,sizeY+blocSize,g);

        repaint();
    }

    private void loadImages(){
        rock  = chargerImage("mur");
        oeuf  = chargerImage("oeuf");
        terre = chargerImage("terre");
        kidE  = chargerImage("E1");
        kidW  = chargerImage("O1");
        kidS  = chargerImage("S1");
        kidN  = chargerImage("N1");
    }

    public Image chargerImage(String nomImg) {
        Image img;
        System.out.println("Ressources/Images/"+nomImg+".png");
        ImageIcon imageIcon = new ImageIcon("Ressources/Images/"+nomImg+".png");
        img = imageIcon.getImage().getScaledInstance(blocSize, blocSize, Image.SCALE_DEFAULT);
        return img;
    }
}
