package glpoo_esiea_1617_velay;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.Timer;

public class PlayGardenAuto extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2957598524180576346L;
	
	private static int sizeX;
    private static int sizeY;
    private int sizeHUD = 150;
    private int blocSize = 25;
    private int fps = 1;
    private static ArrayList<Kid> listKid;
    private static ArrayList<GraphPoint> listEgg;
    private int maxEgg = 0;
    private Timer tRepaint;
    
    private static GameObjects [][] gameMap;
    
    private String filenameMap = "src/main/resources/map.txt";
    private String filenameKids = "src/main/resources/kids.txt";
    
    private PlayGardenUI gardenUI;
    
    private Scanner fic = null;
    
    

    public PlayGardenAuto() {
        PlayGardenAuto.listKid = new ArrayList<Kid>();
        PlayGardenAuto.listEgg = new ArrayList<GraphPoint>();
        loadMap();
        System.out.println("map loaded");
        gameMap = new GameObjects[sizeY][sizeX];
        initMap();
        loadItems();
        initUI();
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                tRepaint.stop();
            }
        });
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
	    blocSize = 50;
	    fic = openFile(filenameMap);
	    fic.next();
	    sizeX = fic.nextInt() + 2; // pour les bords
	    sizeY = fic.nextInt() + 2; // pour les bords
	    System.out.println(sizeX + " " +sizeY);
	    fic.close();
	}
	
	protected void initMap(){
	    for (int y = 0; y<sizeY; y++){
	        for (int x = 0; x<sizeX; x++){
	            gameMap[y][x] = new GameObjects(sizeX, sizeY, x, y, GameItemsList.Empty);
	        }
	    }
	    initBorderMap();
	}

    private void initBorderMap() {
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

    protected void initUI(){

    	gardenUI = PlayGardenUI.getInstance(sizeX, sizeY, blocSize, sizeHUD, gameMap, listKid);
        this.setTitle("Garden");
        this.setSize(sizeX*blocSize, sizeY*blocSize+40+sizeHUD);
        this.add(gardenUI, BorderLayout.CENTER);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tRepaint = new Timer(1000/fps, (ActionListener) gardenUI);
        tRepaint.start();
    }

    public void actionPerformed(ActionEvent e) {
        
    }

    static void updateListEgg() {
        listEgg.clear();
        for (int y = 1; y<sizeY-1; y++){
            for (int x = 1; x<sizeX-1; x++ ){
                if (gameMap[y][x].getType().equals(GameItemsList.Egg)){
                    listEgg.add(new GraphPoint(x, y));
                }
            }
        }
    }

    public void loadItems() {
        loadObjects();
        loadKids();
    }

    private void loadObjects(){
        String line = "";
        char type = ' ';
        int posX = 0, posY = 0, number = 0;
        fic = openFile(filenameMap);
        while (fic.hasNextLine()){
            line = fic.nextLine();
            if (line.length()>0) {
                type = line.charAt(0);
                posX = line.charAt(2)-'0';
                posY = line.charAt(4)-'0';
            }
            System.out.print(line);
            switch (type){
                case 'C':
                    number =  line.charAt(6)-'0';
                    addChocolate(posX,posY,(char)number);
                    break;
                case 'R':
                    addRock(posX,posY);
                    break;
                default:
            }
        }
        fic.close();
    }

    private void addChocolate (int posX, int posY, char number){
        if (posX>0 && posX<sizeX && posY>0 && posY<sizeY) {
            final int nbEgg = java.lang.Character.toChars(number)[0];
            gameMap[posY][posX].setNumEggs(nbEgg);
            gameMap[posY][posX].setType(GameItemsList.Egg);
            /*for (int egg = 0; egg < nbEgg; egg++) {
                gameMap[posY][posX].getListEgg().add(new Egg());
                maxEgg++;
            }*/
        } else {
            System.out.println("Chargement d'un oeuf en dehors du terrain : WHAT A WASTE");
        }
    }

    private void addRock (int posX, int posY){
        if (posX>0 && posX<sizeX && posY>0 && posY<sizeY) {
            gameMap[posY][posX].setOccupied(true);
            gameMap[posY][posX].setType(GameItemsList.Rock);
        } else {
            System.out.println("Chargement d'un rocher en dehors du terrain : MISSED");
        }
    }

    protected void loadKids(){
        System.out.println(this.filenameKids);

        String line = "";
        fic = openFile(filenameKids);
        while (fic.hasNextLine()){
            line = fic.nextLine();
            System.out.print(line);
            addKid(line);
        }
        fic.close();
        for (Kid kid : listKid)
            System.out.println(kid);
    }

    private void addKid(String line){

        int posX = line.charAt(2)-'0';
        int posY = line.charAt(4)-'0';
        System.out.println("Kid at: "+ posX + " " + posY);

        if (posX>0 && posX<sizeX && posY>0 && posY<sizeY) {
            if (gameMap[posY][posX].isOccupied()) {
                System.out.println("Chargement d'un enfant sur un rocher : Espace indisponible");
            }
            else {
                Kid kid = new Kid(maxEgg, sizeX, sizeY);
                final char direction = line.charAt(6);
                kid.initPos(posX,posY,direction);
                ArrayList<Character> name = new ArrayList<Character>();
                int index = 8;
                boolean stop = false;
                char c = ' ';
                do{
                    c = line.charAt(index);
                    if (c == ' ') stop = true;
                    if (c == 'A' || c == 'G' || c == 'D') kid.getPath().add(c);
                    else stop = true;
                    index++;
                } while (!stop);
                stop = false;
                do{
                    c = line.charAt(index);
                    name.add(c);
                    index++;
                    if (line.length()==index) stop = true;
                } while (!stop);
                kid.setName(name.toString());
                if (gameMap[posY][posX].getType().equals(GameItemsList.Egg))  gameMap[posY][posX].setType(GameItemsList.EggAndKid);
                else gameMap[posY][posX].setType(GameItemsList.Kid);
                gameMap[posY][posX].setOccupied(true);
                listKid.add(kid);
            }
        } else {
            System.out.println("Chargement d'un enfant en dehors du terrain");
        }
    }
    
    public static GameObjects[][] getGameMap(){
    	return gameMap;
    }
    
    public static ArrayList<Kid> getListKid() {
    	return listKid;
    }
    
    public static ArrayList<GraphPoint> getListEgg() {
    	return listEgg;
    }
    
    

}
