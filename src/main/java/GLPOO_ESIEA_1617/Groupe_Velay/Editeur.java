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
    private Image objImgActuel;
    private Obj objActuel;
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
    private Kid kidActual;
    private ArrayList<Kid> listKid;
    private MapObjects [][] gameMap;
    private boolean BLOCKED = false;

    public Editeur(int sizeY, int sizeX, int blocSize, MapObjects [][]gameMap, ArrayList<Kid> listKid){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.blocSize = blocSize;
        this.gameMap = gameMap;
        this.listKid = listKid;
        loadImages();
        bSave = new JButton("Sauver");
        bSave.addActionListener(this);
        add(bSave);
        objImgActuel = terre;
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
    private void drawKids(Graphics g){
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
    }

    private void afficherImage(Image img, int x, int y, Graphics g) {
        g.drawImage(img, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        afficheCarte(g);
        //afficheMapConsole();
        drawKids(g);
        afficherImage(objImgActuel,xActuel,yActuel,g); // on affiche l'élément actuel

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
        kidE  = chargerImage("E1");
        kidW  = chargerImage("O1");
        kidS  = chargerImage("S1");
        kidN  = chargerImage("N1");
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (!BLOCKED){
            BLOCKED = true;
            System.out.println("souris clique : " + e.getX() + " " + e.getY());
            // si on clique dans le terrain
            if (e.getX() > blocSize && e.getX() <blocSize*(sizeX+2) && e.getY() > blocSize && e.getY() <blocSize*(sizeY)){
                System.out.println("souris clique : " + e.getX() + " " + e.getY());
                switch (objActuel){
                    case ROCK:
                        gameMap[e.getY()/blocSize-1][e.getX()/blocSize].setObj(Obj.ROCK);
                        break;
                    case EGG:
                        gameMap[e.getY()/blocSize-1][e.getX()/blocSize].setObj(Obj.EGG);
                        break;
                    case KID:
                        gameMap[e.getY()/blocSize-1][e.getX()/blocSize].setObj(Obj.KID);
                        kidActual.setPosX(e.getX()/blocSize);
                        kidActual.setPosY(e.getY()/blocSize-1);
                        listKid.add(kidActual);
                        break;
                    case JARDIN:
                        gameMap[e.getY()/blocSize-1][e.getX()/blocSize].setObj(Obj.JARDIN);
                        break;
                }
                /*if (gameMap[e.getY()/blocSize-1][e.getX()/blocSize].getObj().equals(Obj.JARDIN))
                    gameMap[e.getY()/blocSize-1][e.getX()/blocSize].setObj(Obj.ROCK);
                else
                    gameMap[e.getY()/blocSize-1][e.getX()/blocSize].setObj(Obj.JARDIN);
                    */
            }
            // si on clique dans le HUD pour choisir un objet à rajouter
            if (e.getX() > blocSize/2 && e.getX() < 15*blocSize/2 && e.getY() > 2*blocSize+sizeY*blocSize && e.getY() < 3*blocSize+sizeY*blocSize){
                System.out.println("CLIQUE DANS LE HUD" +sizeY);
                if (e.getX()>blocSize/2 && e.getX()<3*blocSize/2){
                    addSelected(1);
                    objImgActuel = terre;
                    objActuel = Obj.JARDIN;
                } else if (e.getX()>3*blocSize/2 && e.getX()<5*blocSize/2){
                    addSelected(3);
                    objImgActuel = rock;
                    objActuel = Obj.ROCK;
                } else if (e.getX()>5*blocSize/2 && e.getX()<7*blocSize/2){
                    objImgActuel = oeuf;
                    objActuel = Obj.EGG;
                } else if (e.getX()>7*blocSize/2 && e.getX()<9*blocSize/2){
                    objImgActuel = kidE;
                    objActuel = Obj.KID;
                    kidActual = new Kid(0);
                    kidActual.setDirection('E');
                } else if (e.getX()>9*blocSize/2 && e.getX()<11*blocSize/2){
                    objImgActuel = kidN;
                    objActuel = Obj.KID;
                    kidActual = new Kid(0);
                    kidActual.setDirection('N');
                } else if (e.getX()>11*blocSize/2 && e.getX()<13*blocSize/2){
                    objImgActuel = kidW;
                    objActuel = Obj.KID;
                    kidActual = new Kid(0);
                    kidActual.setDirection('W');
                } else if (e.getX()>13*blocSize/2 && e.getX()<15*blocSize/2){
                    objImgActuel = kidS;
                    objActuel = Obj.KID;
                    kidActual = new Kid(0);
                    kidActual.setDirection('S');
                }

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

    private void addSelected (int posX){
        // ici on mettra en valeur quelle obj est selectionné.
    }
}
