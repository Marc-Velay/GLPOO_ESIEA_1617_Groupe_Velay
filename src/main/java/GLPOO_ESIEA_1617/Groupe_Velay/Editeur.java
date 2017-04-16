package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by kafim on 09/04/2017.
 */
public class Editeur extends JPanel implements ActionListener,MouseListener, MouseMotionListener {
    private JButton bSave;
    private JButton bPath;
    private JButton bNext;
    private JButton bPrev;
    private Garden garden;
    private Image objImgActuel;
    private Obj objActuel;
    private Image rock;
    private Image arrowLeft;
    private Image arrowRight;
    private Image arrowDown;
    private Image arrowUp;
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
    private int xPathActuel;
    private int yPathActuel;
    private Kid kidActual;
    private int kidNumber;
    private ArrayList<Kid> listKid;
    private MapObjects [][] gameMap;
    private boolean BLOCKED = false;
    private int etape;
    private HUD hud;

    public Editeur(int sizeY, int sizeX, int blocSize, MapObjects[][] gameMap, ArrayList<Kid> listKid, HUD hud, Garden garden){
        this.garden = garden;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.blocSize = blocSize;
        this.gameMap = gameMap;
        this.listKid = listKid;
        this.hud = hud;
        this.bPath = new JButton("Chemin des Kids");
        this.bPath.addActionListener(this);
        this.bNext = new JButton("Next");
        this.bNext.addActionListener(this);
        this.bPrev = new JButton("Prev");
        this.bPrev.addActionListener(this);
        loadImages();
        bSave = new JButton("Sauver");
        bSave.addActionListener(this);
        add(bPath);
        add(bPrev);
        add(bNext);
        add(bSave);
        bPrev.setVisible(false);
        bNext.setVisible(false);
        bSave.setVisible(false);
        objImgActuel = rock;
        objActuel = Obj.ROCK;
        etape = 1;
        hud.setEtape(1);
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
                System.out.printf("%4s",gameMap[y][x].isBusy());
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

    private void drawKid(Graphics g, Kid kid){
        if (kid != null) {
            switch (kid.getDirection()) {
                case 'E':
                    afficherImage(kidE, kid.getPosX() * blocSize, kid.getPosY() * blocSize, g);
                    break;
                case 'N':
                    afficherImage(kidN, kid.getPosX() * blocSize, kid.getPosY() * blocSize, g);
                    break;
                case 'W':
                    afficherImage(kidW, kid.getPosX() * blocSize, kid.getPosY() * blocSize, g);
                    break;
                case 'S':
                    afficherImage(kidS, kid.getPosX() * blocSize, kid.getPosY() * blocSize, g);
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
        //System.out.println("AFFICHAGE MAP");
        //afficheMapConsole();
        if (etape == 1){
            afficherImage(objImgActuel, xActuel, yActuel, g); // on affiche l'élément actuel
            drawKids(g);
        }
        if (etape == 2) {
            afficherChemin(g);
        }
    }

    private void afficherChemin(Graphics g) {
        drawKid(g,kidActual);
        drawPathKid(g, kidActual);
        repaint();
    }

    private void drawPathKid(Graphics g, Kid kid) {
        //System.out.println(yPathActuel+" "+ xPathActuel);
        if (!BLOCKED){
            if (xPathActuel<sizeX-1 && !gameMap[yPathActuel][xPathActuel + 1].isBusy()){
                afficherImage(arrowRight,(xPathActuel+1)*blocSize, yPathActuel*blocSize,g);
            }
            if (xPathActuel>0 && !gameMap[yPathActuel][xPathActuel-1].isBusy()){
                afficherImage(arrowLeft,(xPathActuel-1)*blocSize, yPathActuel*blocSize,g);
            }
            if (yPathActuel<sizeY-1 && !gameMap[yPathActuel+1][xPathActuel].isBusy()){
                afficherImage(arrowDown,xPathActuel*blocSize, (yPathActuel+1)*blocSize,g);
            }
            if (yPathActuel>0 && !gameMap[yPathActuel-1][xPathActuel].isBusy()){
                afficherImage(arrowUp,xPathActuel*blocSize, (yPathActuel-1)*blocSize,g);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
        if (e.getSource() == bPath){
            BLOCKED = true;
            etape = 2;
            this.remove(bPath);
            bPrev.setVisible(true);
            bNext.setVisible(true);
            hud.setEtape(2);
            hud.setKidMax(listKid.size());
            if (listKid.size()==kidNumber){
                this.remove(bPrev);
                this.remove(bNext);
                bSave.setVisible(true);
            }
            else if (listKid.size() > 0 && listKid.get(0) != null){
                kidActual = listKid.get(0);
                kidNumber = 0;
                xPathActuel = kidActual.getPosX();
                yPathActuel = kidActual.getPosY();
            }
            hud.repaint();
            BLOCKED = false;
        }
        if (e.getSource() == bNext){
            BLOCKED = true;
            kidNumber++;
            if (listKid.size()==kidNumber){
                this.remove(bPrev);
                this.remove(bNext);
                bSave.setVisible(true);
            }
            else if (listKid.size() > kidNumber && kidNumber >=0 && listKid.get(kidNumber) != null) {
                hud.setKidActual(kidNumber);
                hud.printPath(null);
                kidActual = listKid.get(kidNumber);
                xPathActuel = kidActual.getPosX();
                yPathActuel = kidActual.getPosY();
                System.out.println(kidActual);
            }
            else kidNumber--;
            hud.repaint();
            BLOCKED = false;
        }
        if (e.getSource() == bPrev){
            BLOCKED = true;
            kidNumber--;
            if (listKid.size() > kidNumber && kidNumber >= 0 && listKid.get(kidNumber) != null) {
                hud.setKidActual(kidNumber);
                hud.printPath(null);
                kidActual = listKid.get(kidNumber);
                xPathActuel = kidActual.getPosX();
                yPathActuel = kidActual.getPosY();
                System.out.println(kidActual);
            }
            else kidNumber++;
            hud.repaint();
            BLOCKED = false;
        }
        else if (e.getSource() == bSave){
            saveMapAndKidMoves();
        }
    }

    private void saveMapAndKidMoves() {
        JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
        String nom = jop.showInputDialog(null, "Rentrez le nom de la carte", "Request", JOptionPane.QUESTION_MESSAGE);
        jop2.showMessageDialog(null, "Carte enregistrée " + nom, "Message", JOptionPane.INFORMATION_MESSAGE);
        File fmap = new File ("Ressources/map/"+nom);
        File fkid = new File("Ressources/kid/"+nom);
        try
        {
            PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (fmap)));
            pw.println("J " + sizeX+" "+sizeY);
            for (int y=1; y<sizeY-1; y++){
                for (int x=1; x<sizeX-1; x++){
                    if (gameMap[y][x].getObj().equals(Obj.EGG)){
                        pw.println("C " + x + "-" + y + " " + gameMap[y][x].getNumberEggs());
                    } else if (gameMap[y][x].getObj().equals(Obj.ROCK)){
                        pw.println("R " + x + "-" + y);
                    }
                }
            }
            pw.close();
            pw = new PrintWriter (new BufferedWriter (new FileWriter (fkid)));

            for (Kid kid : listKid){
                String s = kid.getPath().toString();
                s = s.replaceAll("\\[","");
                s = s.replaceAll("\\]","");
                s = s.replaceAll(",","");
                s = s.replaceAll(" ","");
                pw.println("E " + kid.getStartPosX() + "-" + kid.getStartPosY() + " " + kid.getStartDirection() + " " + s + " " + "ORDI");
            }
            pw.close();
        }
        catch (IOException exception)
        {
            System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
        }
        garden.dispose();

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
        arrowLeft = chargerImage("arrowLeft");
        arrowRight = chargerImage("arrowRight");
        arrowUp = chargerImage("arrowUp");
        arrowDown = chargerImage("arrowDown");
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (!BLOCKED){
            BLOCKED = true;
            if (etape==1) {
                System.out.println("souris clique : " + e.getX() + " " + e.getY());
                // si on clique dans le terrain
                if (e.getX() > blocSize && e.getX() < blocSize * (sizeX + 2) && e.getY() > blocSize+25 && e.getY() < blocSize * (sizeY)) {
                    System.out.println("souris clique : " + e.getX() + " " + e.getY());
                    switch (objActuel) {
                        case ROCK:
                            gameMap[e.getY() / blocSize - 1][e.getX() / blocSize].setObj(Obj.ROCK);
                            gameMap[e.getY() / blocSize - 1][e.getX() / blocSize].setNumberEggs(0);
                            gameMap[e.getY() / blocSize - 1][e.getX() / blocSize].setBusy(true);
                            break;
                        case EGG:
                            gameMap[e.getY() / blocSize - 1][e.getX() / blocSize].setObj(Obj.EGG);
                            gameMap[e.getY() / blocSize - 1][e.getX() / blocSize].setNumberEggs(gameMap[e.getY() / blocSize - 1][e.getX() / blocSize].getNumberEggs() + 1);
                            break;
                        case KID:
                            char oldDirection = kidActual.getDirection();
                            gameMap[e.getY() / blocSize - 1][e.getX() / blocSize].setObj(Obj.KID);
                            gameMap[e.getY() / blocSize - 1][e.getX() / blocSize].setNumberEggs(0);
                            kidActual.setPosX(e.getX() / blocSize);
                            kidActual.setPosY(e.getY() / blocSize - 1);
                            kidActual.setStartPosX(e.getX() / blocSize);
                            kidActual.setStartPosY(e.getY() / blocSize - 1);
                            listKid.add(kidActual);
                            deleteKid(e.getY() / blocSize - 1, e.getX() / blocSize);
                            System.out.println(listKid);
                            kidActual = new Kid(0, sizeX, sizeY);
                            kidActual.setDirection(oldDirection);
                            kidActual.setStartDirection(oldDirection);
                            break;
                        case JARDIN:
                            gameMap[e.getY() / blocSize - 1][e.getX() / blocSize].setObj(Obj.JARDIN);
                            gameMap[e.getY() / blocSize - 1][e.getX() / blocSize].setNumberEggs(0);
                            break;
                    }
                }
                // si on clique dans le HUD pour choisir un objet à rajouter
                if (e.getX() > blocSize / 2 && e.getX() < 15 * blocSize / 2 && e.getY() > 2 * blocSize + sizeY * blocSize && e.getY() < 3 * blocSize + sizeY * blocSize) {
                    System.out.println("CLIQUE DANS LE HUD" + sizeY);
                    if (e.getX() > blocSize / 2 && e.getX() < 3 * blocSize / 2) {
                        addSelected(1);
                        objImgActuel = terre;
                        objActuel = Obj.JARDIN;
                    } else if (e.getX() > 3 * blocSize / 2 && e.getX() < 5 * blocSize / 2) {
                        addSelected(3);
                        objImgActuel = rock;
                        objActuel = Obj.ROCK;
                    } else if (e.getX() > 5 * blocSize / 2 && e.getX() < 7 * blocSize / 2) {
                        objImgActuel = oeuf;
                        objActuel = Obj.EGG;
                    } else if (e.getX() > 7 * blocSize / 2 && e.getX() < 9 * blocSize / 2) {
                        objImgActuel = kidE;
                        objActuel = Obj.KID;
                        kidActual = new Kid(0, sizeX, sizeY);
                        kidActual.setDirection('E');
                        kidActual.setStartDirection('E');
                    } else if (e.getX() > 9 * blocSize / 2 && e.getX() < 11 * blocSize / 2) {
                        objImgActuel = kidN;
                        objActuel = Obj.KID;
                        kidActual = new Kid(0, sizeX, sizeY);
                        kidActual.setStartDirection('N');
                        kidActual.setDirection('N');
                    } else if (e.getX() > 11 * blocSize / 2 && e.getX() < 13 * blocSize / 2) {
                        objImgActuel = kidW;
                        objActuel = Obj.KID;
                        kidActual = new Kid(0, sizeX, sizeY);
                        kidActual.setStartDirection('W');
                        kidActual.setDirection('W');
                    } else if (e.getX() > 13 * blocSize / 2 && e.getX() < 15 * blocSize / 2) {
                        objImgActuel = kidS;
                        objActuel = Obj.KID;
                        kidActual = new Kid(0, sizeX, sizeY);
                        kidActual.setDirection('S');
                        kidActual.setStartDirection('S');
                    }
                }
            }
            else if (etape==2){
                // au dessus
                if (e.getX() > xPathActuel*blocSize && e.getX() < (xPathActuel+1)*blocSize
                        && e.getY()+12 > yPathActuel*blocSize && e.getY()+12 < (yPathActuel+1)*blocSize){
                    if (!gameMap[yPathActuel-1][xPathActuel].getObj().equals(Obj.ROCK)){
                        switch (kidActual.getDirection()){
                            case 'N':
                                yPathActuel--;
                                kidActual.setPosY(yPathActuel);
                                kidActual.getPath().add('A');
                                break;
                            case 'W':
                                kidActual.getPath().add('D');
                                kidActual.setDirection('N');
                                break;
                            case 'E':
                                kidActual.getPath().add('G');
                                kidActual.setDirection('N');
                                break;
                            case 'S':
                                kidActual.getPath().add('D');
                                kidActual.setDirection('W');
                                break;
                        }

                    }
                }
                // en dessous
                if (e.getX() > xPathActuel*blocSize && e.getX() < (xPathActuel+1)*blocSize
                        && e.getY()+12 > (yPathActuel+2)*blocSize && e.getY()+12 < (yPathActuel+3)*blocSize){
                    if (!gameMap[yPathActuel+1][xPathActuel].getObj().equals(Obj.ROCK)) {
                        switch (kidActual.getDirection()){
                            case 'S':
                                yPathActuel++;
                                System.out.println("en dessous");
                                kidActual.setPosY(yPathActuel);
                                kidActual.getPath().add('A');
                                break;
                            case 'W':
                                kidActual.getPath().add('G');
                                kidActual.setDirection('S');
                                break;
                            case 'E':
                                kidActual.getPath().add('D');
                                kidActual.setDirection('S');
                                break;
                            case 'N':
                                System.out.println("en dessous");
                                kidActual.getPath().add('D');
                                kidActual.setDirection('E');
                                break;
                        }

                    }
                }
                // a droite
                if (e.getX() > (xPathActuel+1)*blocSize && e.getX() < (xPathActuel+2)*blocSize
                        && e.getY()+12 >(yPathActuel+1)*blocSize && e.getY()+12 < (yPathActuel+2)*blocSize){
                    if (!gameMap[yPathActuel][xPathActuel+1].getObj().equals(Obj.ROCK)) {
                        switch (kidActual.getDirection()){
                            case 'E':
                                xPathActuel++;
                                System.out.println("a droite");
                                kidActual.setPosX(xPathActuel);
                                kidActual.getPath().add('A');
                                break;
                            case 'N':
                                kidActual.setDirection('E');
                                kidActual.getPath().add('D');
                                break;
                            case 'S':
                                kidActual.getPath().add('G');
                                System.out.println("a droite");
                                kidActual.setDirection('E');
                                break;
                            case 'W':
                                kidActual.getPath().add('D');
                                System.out.println("a droite");
                                kidActual.setDirection('N');
                                break;
                        }


                    }
                }
                // a gauche
                if (e.getX() > (xPathActuel-1)*blocSize && e.getX() < xPathActuel*blocSize
                        && e.getY()+12 > (yPathActuel+1)*blocSize && e.getY()+12 < (yPathActuel+2)*blocSize){
                    if (!gameMap[yPathActuel][xPathActuel-1].getObj().equals(Obj.ROCK)) {
                        switch (kidActual.getDirection()){
                            case 'W':
                                xPathActuel--;
                                System.out.println("a gauche");
                                kidActual.setPosX(xPathActuel);
                                kidActual.getPath().add('A');
                                break;
                            case 'N':
                                kidActual.setDirection('W');
                                kidActual.getPath().add('D');
                                break;
                            case 'S':
                                System.out.println("a gauche");
                                kidActual.getPath().add('G');
                                kidActual.setDirection('W');
                                break;
                            case 'E':
                                kidActual.getPath().add('D');
                                System.out.println("a gauche");
                                kidActual.setDirection('S');
                                break;
                        }
                    }
                }
                if (kidActual!=null)
                    hud.printPath(kidActual.getPath());
                hud.repaint();
            }
            repaint();
            BLOCKED = false;
        }
    }

    private void deleteKid(int y, int x) {
        for (Kid kid : listKid){
            if (kid.getPosX() == x && kid.getPosY()== y && kid != kidActual){
                listKid.remove(kid);
                break;
            }
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
