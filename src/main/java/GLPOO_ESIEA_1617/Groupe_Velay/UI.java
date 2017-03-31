package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;

public class UI extends JPanel{
    protected Image rock;
    protected Image terre;
    private int sizeX;
    private int sizeY;
    private int [][] gameMap;
    private JFrame frame = null;

    public UI(int sizeY, int sizeX, int [][]gameMap){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.gameMap = gameMap;
        loadImages();
        afficheMapConsole();
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(sizeX*30,sizeY*30));
        frame.setVisible(true);
        repaint();
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
                if (gameMap[y][x]==0)
                    afficherImage(rock,30*x,30*y,g);
                else if (gameMap[y][x]==1)
                    afficherImage(terre,x*30,y*30,g);
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
        afficheCarte(g);

    }

    public Image chargerImage(String nomImg) {
        Image img;
        System.out.println("Images/"+nomImg+".png");
        ImageIcon imageIcon = new ImageIcon("Image/"+nomImg+".png");
        img = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        return img;
    }

    public void loadImages(){
        rock = chargerImage("rock");
        terre = chargerImage("terre");
    }
}
