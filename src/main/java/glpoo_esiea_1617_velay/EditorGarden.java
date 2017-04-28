package glpoo_esiea_1617_velay;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.Timer;

public class EditorGarden extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9095630581837905615L;
	private EditorGardenHUD hud;
    private EditorGardenUI editorUI;
	private static int sizeX;
	private static int sizeY;
    private int blocSize = 50;
    private int sizeHUD = 150;
    private int fps = 2;
    private int maxEgg = 0;
	private static GameObjects[][] gameMap;
	private ArrayList<Kid> listKid;
    private Scanner fic = null;
    private String filenameMap = "src/main/resources/map.txt";
    private Timer tRepaint;
	
	
	public EditorGarden(){
        if(loadMapEditeur()) {

    		//loadMap();
        	System.out.println(sizeX + " Y2:" + sizeY);
            EditorGarden.gameMap = new GameObjects[sizeY][sizeX];
            listKid = new ArrayList<Kid>();
            initMap();
            hud = new EditorGardenHUD(sizeX, sizeY, blocSize, sizeHUD);
           editorUI = new EditorGardenUI(sizeY, sizeX, blocSize, gameMap, listKid, hud);
            KeyListener k = new KeyListener(editorUI);
            this.addKeyListener(k);
            this.setFocusable(true);
            this.addMouseListener((MouseListener) editorUI);
            this.addMouseMotionListener((MouseMotionListener) editorUI);
            this.setTitle("Editeur");
            this.setSize(sizeX * blocSize, sizeY * blocSize + 40 + sizeHUD);
            this.add(editorUI, BorderLayout.CENTER);
            this.add(hud, BorderLayout.SOUTH);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            tRepaint = new Timer(1000 / fps, (ActionListener) editorUI);
            tRepaint.start();
            
        }
	}
	

	private Scanner openFile (String nomFichier){
	    File f = new File(nomFichier);
	    FileReader fr = null;
	    try {
	        fr = new FileReader(f);
	        BufferedReader br = new BufferedReader (fr);
	        return new Scanner (br);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public void loadMap (){
	    fic = openFile(filenameMap);
	    fic.next();
	    sizeX = fic.nextInt() + 2; // pour les bords
	    sizeY = fic.nextInt() + 2; // pour les bords
	    System.out.println(sizeX + " " +sizeY);
	    fic.close();
	}
	
	public static void initMap(){
		System.out.println(sizeX + " init " + sizeY);
	    for (int y = 0; y<sizeY; y++){
	        for (int x = 0; x<sizeX; x++){
	        	EditorGarden.gameMap[y][x] = new GameObjects(sizeX, sizeY, x, y, GameItemsList.Empty);
	        }
	    }
	    initBorderMap();
	}

    private static void initBorderMap() {
        for (int x = 0; x<sizeX; x++){
            gameMap[0][x].setOccupied(true);
            gameMap[0][x].setType(GameItemsList.Rock);
            gameMap[sizeY-1][x].setOccupied(true);
            gameMap[sizeY-1][x].setType(GameItemsList.Rock);
        }
        for (int y = 0; y<sizeY; y++){
            gameMap[y][0].setOccupied(true);
            gameMap[y][0].setType(GameItemsList.Rock);
            gameMap[y][sizeX-1].setOccupied(true);
            gameMap[y][sizeX-1].setType(GameItemsList.Rock);
        }
        
    }
    
    private boolean loadMapEditeur() {
        AskDialog askDialog = new AskDialog(null, "Taille de la carte", true);
        while (!askDialog.isOver()){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (askDialog.isCancel()){
            return false;
        } else {
            System.out.println(askDialog.getX());
            System.out.println(askDialog.getY());
            sizeX = askDialog.getX()+2;
            sizeY = askDialog.getY()+2;
            askDialog.setVisible(false);
            return true;
        }

    }
    
    //Setters
    
    public static void setSizeX(int sizeX){
    	EditorGarden.sizeX = sizeX;
    }
    public static void setSizeY(int sizeY){
    	EditorGarden.sizeY = sizeY;
    }
    
    //Getters
    
    public static GameObjects[][] getGameMap(){
    	return gameMap;
    }

    public static int getSizeX(){
    	return sizeX;
    }
    
    public static int getSizeY(){
    	return sizeY;
    }
    
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
