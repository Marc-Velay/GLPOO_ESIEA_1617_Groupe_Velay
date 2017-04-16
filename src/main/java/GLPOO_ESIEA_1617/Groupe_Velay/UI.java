package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class UI extends JPanel implements ActionListener {
    private final ArrayList<Point> listEgg;
    private Image rock;
    private Image terre;
    private Image oeuf;
    private Image kidE;
    private Image kidW;
    private Image kidS;
    private Image kidN;
    private Image kidPath;
    private Font font = new Font("Courier", Font.BOLD, 20);
    private int sizeX;
    private int sizeY;
    private int blocSize;
    private MapObjects [][] gameMap;
    private ArrayList<Kid> listKid;



    public UI(int sizeY, int sizeX, int blocSize, MapObjects[][] gameMap, ArrayList<Kid> listKid, ArrayList<Point> listEgg){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.blocSize = blocSize;
        this.gameMap = gameMap;
        this.listKid = listKid;
        this.listEgg = listEgg;
        loadImages();
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

        drawKids(g);
    }

    public void afficheMapConsole() {
        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++){
                System.out.printf("%c",gameMap[y][x].getObj());
            }
            System.out.println();
        }
    }
    private void drawKids(Graphics g){
        for (Kid kid : listKid){
            affichePath(kid, g);
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

    private void affichePath(Kid kid, Graphics g) {
        if (kid.getGrapheAStar().isPathExist()) {
            for (int i = 1; i < kid.getPathA().getPath().size(); i++) {
                afficherImage(kidPath, kid.getPathA().getPath().get(i).getX() * blocSize, kid.getPathA().getPath().get(i).getY() * blocSize, g);
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
        //System.out.println("dans l'affichage");
    }

    public void actionPerformed(ActionEvent e) {
        updateListEgg();
        for (Kid kid : listKid){
            kid.move(gameMap);
            kid.updatePath(listEgg, gameMap);
        }
        //System.out.println("time B : " + System.currentTimeMillis());
        repaint();
    }

    private void updateListEgg() {
        listEgg.clear();
        for (int y = 1; y<sizeY-1; y++){
            for (int x = 1; x<sizeX-1; x++ ){
                if (gameMap[y][x].getObj().equals(Obj.EGG)){
                    listEgg.add(new Point(x, y));
                }
            }
        }
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
        kidPath = chargerImage("path");
    }


    public Image getKidPath() {
        return kidPath;
    }

    public void setKidPath(Image kidPath) {
        this.kidPath = kidPath;
    }
}
