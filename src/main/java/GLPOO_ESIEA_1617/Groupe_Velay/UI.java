package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class UI extends JPanel implements ActionListener {
    protected Image rock;
    protected Image terre;
    private int sizeX;
    private int sizeY;
    private int [][] gameMap;

    public UI(int sizeY, int sizeX, int [][]gameMap){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.gameMap = gameMap;
        loadImages();
        afficheMapConsole();
    }

    public void afficheObjects(Graphics g){
        /*for (int i=0; i<listObj.size(); i++){
            afficherImage(terre,listObj.get(i).getPos().getX()*30,listObj.get(i).getPos().getY()*30,g);
            afficherImage(imgO,listObj.get(i).getPos().getX()*30,listObj.get(i).getPos().getY()*30,g);
        }*/
    }

    public void afficheCarte(Graphics g){
        System.out.println("TOO");
        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++){
               // if (gameMap[y][x]==0)
                    afficherImage(rock,30*x,30*y,g);
                //else if (gameMap[y][x]==1)
                //   afficherImage(terre,x*30,y*30,g);
            }
        }
    }

    public void afficheMapConsole() {
        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++)
                System.out.printf("%3d",gameMap[y][x]);
            System.out.println();
        }
    }

    public void afficherImage(Image img, int x, int y, Graphics g) {
        g.drawImage(img, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        afficheCarte(g);
        System.out.println("dans l'affichage");
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("time B : " + System.currentTimeMillis());
        repaint();
    }

    public Image chargerImage(String nomImg) {
        Image img;
        System.out.println("Images/"+nomImg+".png");
        ImageIcon imageIcon = new ImageIcon("Images/"+nomImg+".png");
        img = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        return img;
    }

    public void loadImages(){
        rock = chargerImage("mur");
        terre = chargerImage("terre");
    }
}
