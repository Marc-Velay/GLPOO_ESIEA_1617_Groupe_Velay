package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by kafim on 09/04/2017.
 */
public class Editeur extends JPanel implements ActionListener,MouseListener, MouseMotionListener {
    private JButton bSave;
    private Garden garden;
    private Image rock;
    private Image terre;
    private Image oeuf;
    private Image kidE;
    private Image kidW;
    private Image kidS;
    private Image kidN;
    private Font font = new Font("Courier", Font.BOLD, 20);
    private int sizeX;
    private int sizeY;
    private int blocSize;
    private int xActuel;
    private int yActuel;
    private MapObjects [][] gameMap;
    private boolean BLOCKED = false;

    public Editeur(int sizeY, int sizeX, int blocSize, MapObjects [][]gameMap){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.blocSize = blocSize;
        this.gameMap = gameMap;
        loadImages();
        bSave = new JButton("Sauver");
        bSave.addActionListener(this);
        add(bSave);
    }

    public void afficheCarte(Graphics g){
        g.setFont(font);
        g.setColor(Color.black);

        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++){
                afficherImage(terre,x*blocSize,y*blocSize,g); // On affiche la terre partout
                if (gameMap[y][x].getObj().equals(Obj.ROCK))
                    afficherImage(rock,blocSize*x,blocSize*y,g);
                else if (gameMap[y][x].getObj().equals(Obj.EGG)){
                    afficherImage(oeuf,x*blocSize,y*blocSize,g);
                    g.drawString(String.valueOf(gameMap[y][x].getNumberEggs()), x*blocSize+4*blocSize/5, y*blocSize+blocSize);
                } else if (gameMap[y][x].getObj().equals(Obj.EGGANDKID)){
                    afficherImage(oeuf,x*blocSize,y*blocSize,g);
                    g.drawString(String.valueOf(gameMap[y][x].getNumberEggs()), x*blocSize+4*blocSize/5, y*blocSize+blocSize);
                }
            }
        }

    }

    public void afficheMapConsole() {
        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++){
                System.out.printf("%4s",gameMap[y][x].getObj().toString());
            }
            System.out.println();
        }
    }
/*    private void drawKids(Graphics g){
        for (Kid kid : listKid){
            switch (kid.getDirection()){
                case 'E':
                    afficherImage(kidE,kid.getPosX()*blocSize,kid.getPosY()*blocSize,g);
                    break;
                case 'N':
                    afficherImage(kidN,kid.getPosX()*blocSize,kid.getPosY()*blocSize,g);
                    break;
                case 'W':
                    afficherImage(kidW,kid.getPosX()*blocSize,kid.getPosY()*blocSize,g);
                    break;
                case 'S':
                    afficherImage(kidS,kid.getPosX()*blocSize,kid.getPosY()*blocSize,g);
                    break;
            }
        }
    }*/

    private void afficherImage(Image img, int x, int y, Graphics g) {
        g.drawImage(img, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        afficheCarte(g);
        //afficheMapConsole();
        afficherImage(rock,xActuel,yActuel,g);
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public Image chargerImage(String nomImg) {
        Image img;
        System.out.println("Ressources/Images/"+nomImg+".png");
        ImageIcon imageIcon = new ImageIcon("Ressources/Images/"+nomImg+".png");
        img = imageIcon.getImage().getScaledInstance(blocSize, blocSize, Image.SCALE_DEFAULT);
        return img;
    }

    private void loadImages(){
        rock  = chargerImage("mur");
        oeuf  = chargerImage("oeuf");
        terre = chargerImage("terre");
        kidE = chargerImage("E1");
        kidW = chargerImage("O1");
        kidS = chargerImage("S1");
        kidN = chargerImage("N1");
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (!BLOCKED){
            BLOCKED = true;
            if (e.getX() > blocSize && e.getX() <blocSize*(sizeX+2) && e.getY() > blocSize && e.getY() <blocSize*(2+sizeY)){
                System.out.println("souris clique : " + e.getX() + " " + e.getY());
                if (gameMap[e.getY()/blocSize-1][e.getX()/blocSize].getObj().equals(Obj.JARDIN))
                    gameMap[e.getY()/blocSize-1][e.getX()/blocSize].setObj(Obj.ROCK);
                else
                    gameMap[e.getY()/blocSize-1][e.getX()/blocSize].setObj(Obj.JARDIN);
            }
            repaint();
            BLOCKED = false;
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        xActuel = e.getX()-blocSize/2;
        yActuel = e.getY()-4*blocSize/3;
    }
}
