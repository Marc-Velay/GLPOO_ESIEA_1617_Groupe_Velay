package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.*;
import java.lang.Character;
import java.util.ArrayList;
import java.util.Scanner;

public class Garden extends JFrame{
    private int sizeX;
    private int sizeY;
    private int sizeHUD;
    private int blocSize;
    private int fps;
    private MapObjects [][] gameMap;
    private Scanner fic = null;
    private String filenameMap = "Ressources/map.txt";
    private String filenameKids = "Ressources/kids.txt";
    private UI map;
    private Editeur mapEdit;
    private HUD hud;
    private ArrayList<Kid> listKid;
    private ArrayList<Kid> listKidDone;
    private int maxEgg;

    public Timer gettRepaint() {
        return tRepaint;
    }

    public void settRepaint(Timer tRepaint) {
        this.tRepaint = tRepaint;
    }

    private Timer tRepaint;


    public Garden () {
        sizeHUD = 150;
        listKid = new ArrayList<Kid>();

    }

    public Garden (String fileName, MiniView miniView) {
        this.filenameMap = fileName;
        sizeHUD = 150;
        listKid = new ArrayList<Kid>();
        loadMap();
        blocSize = 25;
        maxEgg = 0;
        gameMap = new MapObjects[sizeY][sizeX];
        initMap();
        loadItemsEditeurMini();
        fps = 1;
        tRepaint = new Timer(1000/fps, (ActionListener) miniView);
    }

    protected void initUI(){
        loadMap();
        fps = 1;
        maxEgg = 0;
        gameMap = new MapObjects[sizeY][sizeX];

        map = new UI(sizeY,sizeX,blocSize,gameMap,listKid);
        hud = new HUD(sizeX, sizeY, blocSize, sizeHUD);
        initMap();
        this.setTitle("Garden");
        this.setSize(sizeX*blocSize, sizeY*blocSize+40+sizeHUD);
        this.add(map, BorderLayout.CENTER);
        this.add(hud, BorderLayout.SOUTH);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        tRepaint = new Timer(1000/fps, (ActionListener) map);
        tRepaint.start();
    }

    protected void initEditeur(){
        loadMapEditeur();
        blocSize = 50;
        fps = 10;
        maxEgg = 0;
        gameMap = new MapObjects[sizeY][sizeX];

        initMap();
        hud = new HUD(sizeX, sizeY, blocSize, sizeHUD);
        hud.initEditeur();
        hud.repaint();
        mapEdit = new Editeur(sizeY,sizeX,blocSize,gameMap, listKid, hud);
        this.addMouseListener((MouseListener) mapEdit);
        this.addMouseMotionListener((MouseMotionListener) mapEdit);
        this.setTitle("Editeur");
        this.setSize(sizeX*blocSize, sizeY*blocSize+40+sizeHUD);
        this.add(mapEdit, BorderLayout.CENTER);
        this.add(hud, BorderLayout.SOUTH);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        tRepaint = new Timer(1000/fps, (ActionListener) mapEdit);
        tRepaint.start();
    }

    private void loadMapEditeur() {
        AskDialog askDialog = new AskDialog(null, "Taille de la carte", true);
        while (!askDialog.isOver()){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(askDialog.getX());
        System.out.println(askDialog.getY());
        sizeX = askDialog.getX()+2;
        sizeY = askDialog.getY()+2;
        askDialog.setVisible(false);

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

    protected void loadKids(){
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


    protected void initMap(){
        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++){
                gameMap[y][x] = new MapObjects();
            }
        }
        initBorderMap();
    }

    public void loadItems() {
        loadEggsAndRock();
        loadKids();
        hud.init(listKid);
    }

    public void loadItemsEditeurMini() {
        loadEggsAndRock();
        loadKids();
    }

    private void initBorderMap() {
        for (int x = 0; x<sizeX; x++){
            gameMap[0][x].setBusy(true);
            gameMap[0][x].setObj(Obj.ROCK);
            gameMap[sizeY-1][x].setBusy(true);
            gameMap[sizeY-1][x].setObj(Obj.ROCK);
        }
        for (int y = 0; y<sizeY; y++){
            gameMap[y][0].setBusy(true);
            gameMap[y][0].setObj(Obj.ROCK);
            gameMap[y][sizeX-1].setBusy(true);
            gameMap[y][sizeX-1].setObj(Obj.ROCK);
        }
    }


    private void loadEggsAndRock(){
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
            }
        }
        fic.close();
    }

    private void addChocolate (int posX, int posY, char number){
        if (posX>0 && posX<sizeX && posY>0 && posY<sizeY) {
            final int nbEgg = java.lang.Character.toChars(number)[0];
            gameMap[posY][posX].setNumberEggs(nbEgg);
            gameMap[posY][posX].setObj(Obj.EGG);
            for (int egg = 0; egg < nbEgg; egg++) {
                gameMap[posY][posX].getListEgg().add(new Egg());
                maxEgg++;
            }
        } else {
            System.out.println("Chargement d'un oeuf en dehors du terrain : WHAT A WASTE");
        }
    }

    private void addRock (int posX, int posY){
        if (posX>0 && posX<sizeX && posY>0 && posY<sizeY) {
            gameMap[posY][posX].setBusy(true);
            gameMap[posY][posX].setObj(Obj.ROCK);
        } else {
            System.out.println("Chargement d'un rocher en dehors du terrain : MISSED");
        }
    }

    private void addKid(String line){

        int posX = line.charAt(2)-'0';
        int posY = line.charAt(4)-'0';
        System.out.println("KOKO"+ posX + " " + posY);

        if (posX>0 && posX<sizeX && posY>0 && posY<sizeY) {
            if (gameMap[posY][posX].isBusy()) {
                System.out.println("Chargement d'un enfant sur un rocher : FIRST BLOOD");
            }
            else {
                Kid kid = new Kid(maxEgg);
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
                if (gameMap[posY][posX].getObj().equals(Obj.EGG))  gameMap[posY][posX].setObj(Obj.EGGANDKID);
                else gameMap[posY][posX].setObj(Obj.KID);
                gameMap[posY][posX].setBusy(true);
                listKid.add(kid);
            }
        } else {
            System.out.println("Chargement d'un enfant en dehors du terrain");
        }
    }


    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getBlocSize() {
        return blocSize;
    }

    public void setBlocSize(int blocSize) {
        this.blocSize = blocSize;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public Scanner getFic() {
        return fic;
    }

    public void setFic(Scanner fic) {
        this.fic = fic;
    }

    public String getFilenameMap() {
        return filenameMap;
    }

    public void setFilenameMap(String filenameMap) {
        this.filenameMap = filenameMap;
    }

    public UI getMap() {
        return map;
    }

    public void setMap(UI map) {
        this.map = map;
    }

    public MapObjects[][] getGameMap() {
        return gameMap;
    }

    public void setGameMap(MapObjects[][] gameMap) {
        this.gameMap = gameMap;
    }

    public String getFilenameKids() {
        return filenameKids;
    }

    public void setFilenameKids(String filenameKids) {
        this.filenameKids = filenameKids;
    }

    public ArrayList<Kid> getListKid() {
        return listKid;
    }

    public void setListKid(ArrayList<Kid> listKid) {
        this.listKid = listKid;
    }
}
