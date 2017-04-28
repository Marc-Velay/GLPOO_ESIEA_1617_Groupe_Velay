package glpoo_esiea_1617_velay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class EditorGardenHUD extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5325385097352407135L;
	private static EditorGardenHUD instance;
	private static int sizeX;
    private static int sizeY;
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
    private Font font = new Font("Courier", Font.BOLD, 20);
    private int etape;
    private int kidActual;
    private int kidMax;
    private int blocSize;
    private ArrayList<Character> path;
    
    public EditorGardenHUD(int sizeX, int sizeY, int blocSize, int sizeHUD){
        EditorGardenHUD.sizeX = sizeX;
        EditorGardenHUD.sizeY = sizeY;
        this.blocSize = blocSize;
        loadImages();
        this.setPreferredSize(new Dimension(sizeX,sizeHUD));
        this.setLayout(new GridLayout(sizeY-2, 2));
        this.setVisible(true);
        path = new ArrayList<Character>();
        repaint();
    }
    
    
	public static synchronized EditorGardenHUD getInstance(int sizeX, int sizeY, int blocSize, int sizeHUD) {
		if (instance == null) {
			instance = new EditorGardenHUD(sizeX, sizeY, blocSize, sizeHUD);
		}
		return instance;
	}
	
	private void afficherImage(Image img, int x, int y, Graphics g, JPanel p) {
        g.drawImage(img, x, y, p);
        Toolkit.getDefaultToolkit().sync();
    }


    public void draw(Graphics g, JPanel p){
        afficheEditeur(g,p);
    }

    public void afficheEditeur(Graphics g, JPanel p){
        if (etape == 1) {
            afficherImage(terre,/*blocSize/2*/300,/*sizeY * blocSize + 40*/300,g,p);
            afficherImage(rock,3*blocSize/2,sizeY+blocSize,g,p);
            afficherImage(oeuf,5*blocSize/2,sizeY+blocSize,g,p);
            afficherImage(kidE,7*blocSize/2,sizeY+blocSize,g,p);
            afficherImage(kidN,9*blocSize/2,sizeY+blocSize,g,p);
            afficherImage(kidW,11*blocSize/2,sizeY+blocSize,g,p);
            afficherImage(kidS,13*blocSize/2,sizeY+blocSize,g,p);
            
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
                            afficherImage(A, x * blocSize, y * blocSize, g,p);
                            break;
                        case 'G':
                            afficherImage(G, x * blocSize, y * blocSize, g,p);
                            break;
                        case 'D':
                            afficherImage(D, x * blocSize, y * blocSize, g,p);
                            break;
                    }
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
        System.out.println(Game.IMGPATH+nomImg+".png");
        ImageIcon imageIcon = new ImageIcon(Game.IMGPATH+nomImg+".png");
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

    public int getEtape() {
        return etape;
    }

    public void setEtape(int etape) {
        this.etape = etape;
    }

    public static void setSizeX(int sizeX){
    	EditorGardenHUD.sizeX = sizeX;
    }
    public static void setSizeY(int sizeY){
    	EditorGardenHUD.sizeY = sizeY;
    }
}
