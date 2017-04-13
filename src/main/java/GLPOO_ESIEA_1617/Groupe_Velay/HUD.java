package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.lang.Character;
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
    private Image A;
    private Image G;
    private Image D;
    private int kidActual;
    private int kidMax;
    private int blocSize;
    private ArrayList<Character> path;

    public int getEtape() {
        return etape;
    }

    public void setEtape(int etape) {
        this.etape = etape;
    }

    private int etape;
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
        this.setLayout(new GridLayout(sizeY-2, 2));
        this.setVisible(true);
        path = new ArrayList<Character>();
        repaint();
    }

    private void afficherImage(Image img, int x, int y, Graphics g) {
        g.drawImage(img, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        afficheEditeur(g);
    }

    public void afficheEditeur(Graphics g){
        if (etape == 1) {
            afficherImage(terre,blocSize/2,sizeY+blocSize,g);
            afficherImage(rock,3*blocSize/2,sizeY+blocSize,g);
            afficherImage(oeuf,5*blocSize/2,sizeY+blocSize,g);
            afficherImage(kidE,7*blocSize/2,sizeY+blocSize,g);
            afficherImage(kidN,9*blocSize/2,sizeY+blocSize,g);
            afficherImage(kidW,11*blocSize/2,sizeY+blocSize,g);
            afficherImage(kidS,13*blocSize/2,sizeY+blocSize,g);
        } else if (etape == 2) {
            System.out.println("REFRESH");
            g.setFont(font);
            g.setColor(Color.black);
            String str = "Enfant : " + (kidActual + 1) + " / " + kidMax;
            g.drawString(str, 10, blocSize / 2);
            int x = 2;
            int y = 0;
            if (path != null) {
                for (java.lang.Character c : path) {
                    x++;
                    if (x == sizeX) {
                        y++;
                        x = 0;
                    }
                    switch (c) {
                        case 'A':
                            afficherImage(A, x * blocSize, y * blocSize, g);
                            break;
                        case 'G':
                            afficherImage(G, x * blocSize, y * blocSize, g);
                            break;
                        case 'D':
                            afficherImage(D, x * blocSize, y * blocSize, g);
                            break;
                    }
                    //System.out.println("AJOUT : "+c);
                }
            }
        }

    }

    private void loadImages(){
        rock  = chargerImage("mur");
        oeuf  = chargerImage("oeuf");
        terre = chargerImage("terre");
        kidE  = chargerImage("E1");
        kidW  = chargerImage("O1");
        kidS  = chargerImage("S1");
        kidN  = chargerImage("N1");
        A  = chargerImage("A");
        G  = chargerImage("G");
        D  = chargerImage("D");
    }

    public Image chargerImage(String nomImg) {
        Image img;
        System.out.println("Ressources/Images/"+nomImg+".png");
        ImageIcon imageIcon = new ImageIcon("Ressources/Images/"+nomImg+".png");
        img = imageIcon.getImage().getScaledInstance(blocSize, blocSize, Image.SCALE_DEFAULT);
        return img;
    }

    public void setKidActual(int kidActual) {
        this.kidActual = kidActual;
    }

    public void setKidMax(int kidMax) {
        this.kidMax = kidMax;
    }

    public void printPath(ArrayList<java.lang.Character> path) {
        setPath(path);
    }

    public void setPath(ArrayList<Character> path) {
        this.path = path;
        repaint();
    }
}
