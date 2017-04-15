package GLPOO_ESIEA_1617.Groupe_Velay;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by kafim on 14/04/2017.
 */
public class MiniView extends JPanel implements ActionListener {

    private boolean RUNNING = true;

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    private Garden garden;
    private Image rock;
    private Image terre;
    private Image oeuf;
    private Image kidE;
    private Image kidW;
    private Image kidS;
    private Image kidN;


    public MiniView (String fic){
        System.out.println(fic.substring(15));
        garden = new Garden(fic.substring(15), this);
        loadImages();
        this.setVisible(true);
    }

    public Image chargerImage(String nomImg) {
        Image img;
        System.out.println("Ressources/Images/"+nomImg+".png");
        ImageIcon imageIcon = new ImageIcon("Ressources/Images/"+nomImg+".png");
        img = imageIcon.getImage().getScaledInstance(garden.getBlocSize(),garden.getBlocSize(), Image.SCALE_DEFAULT);
        return img;
    }

    public void afficheCarte(Graphics g){
        g.setFont(garden.getFont());
        g.setColor(Color.black);

        for (int y = 0; y<garden.getSizeY(); y++){
            for (int x = 0; x<garden.getSizeX(); x++){
                afficherImage(terre,x*garden.getBlocSize(),y*garden.getBlocSize(),g); // On affiche la terre partout
                if (garden.getGameMap()[y][x].getObj().equals(Obj.ROCK))
                    afficherImage(rock,garden.getBlocSize()*x,garden.getBlocSize()*y,g);
                else if (garden.getGameMap()[y][x].getObj().equals(Obj.EGG)){
                    afficherImage(oeuf,x*garden.getBlocSize(),y*garden.getBlocSize(),g);
                    g.drawString(String.valueOf(garden.getGameMap()[y][x].getNumberEggs()), x*garden.getBlocSize()+4*garden.getBlocSize()/5, y*garden.getBlocSize()+garden.getBlocSize());
                } else if (garden.getGameMap()[y][x].getObj().equals(Obj.EGGANDKID)){
                    afficherImage(oeuf,x*garden.getBlocSize(),y*garden.getBlocSize(),g);
                    g.drawString(String.valueOf(garden.getGameMap()[y][x].getNumberEggs()), x*garden.getBlocSize()+4*garden.getBlocSize()/5, y*garden.getBlocSize()+garden.getBlocSize());
                }
            }
        }

        drawKids(g);
    }

    private void drawKids(Graphics g){
        for (Kid kid : garden.getListKid()){
            switch (kid.getDirection()){
                case 'E':
                    afficherImage(kidE,kid.getPosX()*garden.getBlocSize(),kid.getPosY()*garden.getBlocSize(),g);
                    break;
                case 'N':
                    afficherImage(kidN,kid.getPosX()*garden.getBlocSize(),kid.getPosY()*garden.getBlocSize(),g);
                    break;
                case 'W':
                    afficherImage(kidW,kid.getPosX()*garden.getBlocSize(),kid.getPosY()*garden.getBlocSize(),g);
                    break;
                case 'S':
                    afficherImage(kidS,kid.getPosX()*garden.getBlocSize(),kid.getPosY()*garden.getBlocSize(),g);
                    break;
            }
        }
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

    private void afficherImage(Image img, int x, int y, Graphics g) {
        g.drawImage(img, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);

        afficheCarte(g);
    }
    public void actionPerformed(ActionEvent e) {
        if (RUNNING) {
            for (Kid kid : garden.getListKid()) {
                kid.move(garden.getGameMap());
            }
            //System.out.println("time B : " + System.currentTimeMillis());
            repaint();
        }
    }

    public boolean isRUNNING() {
        return RUNNING;
    }

    public void setRUNNING(boolean RUNNING) {
        this.RUNNING = RUNNING;
    }
}
