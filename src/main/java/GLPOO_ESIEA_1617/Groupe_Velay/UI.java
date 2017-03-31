package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType.W;

public class UI extends JPanel implements ActionListener {
    private Image rock;
    private Image terre;
    private int sizeX;
    private int sizeY;
    private int blocSize;
    private char [][] gameMap;

    public UI(int sizeY, int sizeX, int blocSize, char [][]gameMap){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.blocSize = blocSize;
        this.gameMap = gameMap;
        initMap();
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
        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++){
                if (gameMap[y][x]=='b')
                    afficherImage(rock,blocSize*x,blocSize*y,g);
                else if (gameMap[y][x]=='0')
                   afficherImage(terre,x*blocSize,y*blocSize,g);
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
        img = imageIcon.getImage().getScaledInstance(blocSize, blocSize, Image.SCALE_DEFAULT);
        return img;
    }

    public void loadImages(){
        rock = chargerImage("mur");
        terre = chargerImage("terre");
    }

    public void initMap(){
        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++){
                gameMap[y][x] = '0'; // 0 pour 0 oeuf
            }
        }
        initBorderMap();
    }

    public void initBorderMap() {
        for (int x = 0; x<sizeX; x++){
            gameMap[0][x] = 'b';
            gameMap[sizeY-1][x] = 'b'; // b pour bord
        }
        for (int y = 0; y<sizeY; y++){
            gameMap[y][0] = 'b';
            gameMap[y][sizeX-1] = 'b';
        }
    }

}
