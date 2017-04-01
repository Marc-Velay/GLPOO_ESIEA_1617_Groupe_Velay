package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI extends JPanel implements ActionListener {
    private Image rock;
    private Image terre;
    private Image oeuf;
    private Font font = new Font("Courier", Font.BOLD, 20);
    private int sizeX;
    private int sizeY;
    private int blocSize;
    private char [][] gameMap;

    public UI(int sizeY, int sizeX, int blocSize, char [][]gameMap){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.blocSize = blocSize;
        this.gameMap = gameMap;

        loadImages();
        afficheMapConsole();
    }

    public void afficheCarte(Graphics g){
        g.setFont(font);
        g.setColor(Color.black);

        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++){
                if (gameMap[y][x]=='b' || gameMap[y][x]=='r')
                    afficherImage(rock,blocSize*x,blocSize*y,g);
                else if (gameMap[y][x]=='0')
                   afficherImage(terre,x*blocSize,y*blocSize,g);
                else if (gameMap[y][x]>'0'){
                    afficherImage(oeuf,x*blocSize,y*blocSize,g);
                    g.drawString(String.valueOf(gameMap[y][x]), x*blocSize+4*blocSize/5, y*blocSize+blocSize);
                }
            }
        }
    }

    public void afficheMapConsole() {
        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++){
                System.out.printf("%c",gameMap[y][x]);
            }
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
        //System.out.println("dans l'affichage");
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("time B : " + System.currentTimeMillis());
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
    }



}
