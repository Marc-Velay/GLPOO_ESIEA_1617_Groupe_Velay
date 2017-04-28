package glpoo_esiea_1617_velay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class PlayGardenUI extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5086729511314441949L;
	public JPanel hud;
	
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
    private int blocSize=25;
    private int sizeHUD;
    
	private static PlayGardenUI instance;
    
	public PlayGardenUI(int sizeX, int sizeY, int blocSize, int sizeHUD, GameObjects[][] gameMap, ArrayList<Kid> listKid) {
    	loadImages();
    	this.setLayout(new BorderLayout());
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.blocSize = blocSize;
        this.sizeHUD = sizeHUD;
        this.hud = new JPanel();
        //this.add(map,BorderLayout.CENTER);
        this.add(hud,BorderLayout.SOUTH);
        initHUD();
	}
	
    
	public static synchronized PlayGardenUI getInstance(int sizeX, int sizeY, int blocSize, int sizeHUD, GameObjects[][] gameMap, ArrayList<Kid> listKid) {
		if (instance == null) {
			instance = new PlayGardenUI(sizeX, sizeY, blocSize, sizeHUD, gameMap, listKid);
		}
		return instance;
	}
	
	public void initHUD() {
		hud.setPreferredSize(new Dimension(sizeX,sizeHUD));
		if(Menu.gameAuto == true) {
	        hud.setLayout(new GridLayout(PlayGarden.getListKid().size(), 2));
	        for (Kid kid : PlayGarden.getListKid()){
	            kid.getJname().setText(kid.getName());
	            hud.add(kid.getJname());
	            hud.add(kid.getjBar());
	        }
		} else {
	        hud.setLayout(new GridLayout(PlayGardenAuto.getListKid().size(), 2));
	        for (Kid kid : PlayGardenAuto.getListKid()){
	            kid.getJname().setText(kid.getName());
	            hud.add(kid.getJname());
	            hud.add(kid.getjBar());
	        }
		}
        hud.setVisible(true);
	}

    public Image chargerImage(String nomImg) {
        Image img;
        System.out.println("src/main/resources/Images/"+nomImg+".png");
        ImageIcon imageIcon = new ImageIcon("src/main/resources/Images/"+nomImg+".png");
        img = imageIcon.getImage().getScaledInstance(blocSize*2, blocSize*2, Image.SCALE_DEFAULT);
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


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(Menu.gameAuto == true) {
            afficheCarte(PlayGarden.getGameMap(), PlayGarden.getListKid(), g);
            afficheHud(PlayGarden.getListKid(), g);
            this.revalidate();
        } else {
            afficheCarte(PlayGardenAuto.getGameMap(), PlayGardenAuto.getListKid(), g);
            afficheHud(PlayGardenAuto.getListKid(), g);
        }
        System.out.println("dans l'affichage");
    }


    public void afficheMapConsole(GameObjects[][] gameMap) {
        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++){
                System.out.printf("%c",gameMap[y][x].getType());
            }
            System.out.println();
        }
    }

    public void afficheCarte(GameObjects[][] gameMap, ArrayList<Kid> listKid, Graphics g){
        g.setFont(font);
        g.setColor(Color.black);

        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++){
                afficherImage(terre,x*blocSize,y*blocSize,g); // On affiche la terre partout
                if (gameMap[y][x].getType().equals(GameItemsList.Rock))
                    afficherImage(rock,blocSize*x,blocSize*y,g);
                else if (gameMap[y][x].getType().equals(GameItemsList.Egg)){
                    afficherImage(oeuf,x*blocSize,y*blocSize,g);
                    g.drawString(String.valueOf(gameMap[y][x].getNumEggs()), x*blocSize+4*blocSize/5, y*blocSize+blocSize);
                } else if (gameMap[y][x].getType().equals(GameItemsList.EggAndKid)){
                    afficherImage(oeuf,x*blocSize,y*blocSize,g);
                    g.drawString(String.valueOf(gameMap[y][x].getNumEggs()), x*blocSize+4*blocSize/5, y*blocSize+blocSize);
                }
            }
        }

        drawKids(gameMap, listKid, g);
    }
    
    public void afficheHud(ArrayList<Kid> listKid, Graphics g){
    	hud.removeAll();
    	for(Kid kid : listKid) {
    		kid.getJname().setText(kid.getName());
            hud.add(kid.getJname());
            hud.add(kid.getjBar());
    	}
    	hud.revalidate();
    	hud.setVisible(true);
    }
    
    private void drawKids(GameObjects[][] gameMap, ArrayList<Kid> listKid, Graphics g){
        for (Kid kid : listKid){
            showPath(gameMap, kid, g);
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

    private void showPath(GameObjects[][] gameMap, Kid kid, Graphics g) {
        if (kid.getGrapheAStar().isPathExist() && !gameMap[kid.getPosY()][kid.getPosY()].getType().equals(GameItemsList.EggAndKid)) {
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
	public void actionPerformed(ActionEvent arg0) {
		if(Menu.gameAuto ==true) {
			PlayGarden.updateListEgg();
	        for (Kid kid : PlayGarden.getListKid()){
	            kid.move(PlayGarden.getGameMap());
	        }
		} else {
			PlayGardenAuto.updateListEgg();
	        for (Kid kid : PlayGardenAuto.getListKid()){
	            kid.move(PlayGardenAuto.getGameMap());
	            kid.updatePath(PlayGardenAuto.getListEgg(), PlayGardenAuto.getGameMap());
	        }
		}
        System.out.println("repaint");
        repaint();
		
	}
}
