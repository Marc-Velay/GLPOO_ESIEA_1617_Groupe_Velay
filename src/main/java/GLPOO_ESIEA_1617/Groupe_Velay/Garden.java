package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.event.ActionListener;
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
    private int blocSize;
    private int fps;
    private char [][] gameMap;
    private MapObjects [][] gameMapO;
    private Scanner fic = null;
    private String filenameMap = "Ressources/map.txt";
    private String filenameKids = "Ressources/kids.txt";
    private UI map;
    private ArrayList<Kid> listKid;


    public Garden () {

    }

    protected void init(){
        listKid = new ArrayList<Kid>();
        loadMap();
        //loadKids();
        fps = 30;
        gameMap = new char[sizeY][sizeX];
        map = new UI(sizeY,sizeX,blocSize,gameMap);
        initMap();
        this.setTitle("Garden");
        this.setSize(sizeX*blocSize+5, sizeY*blocSize+28);
        this.setContentPane(map);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Timer tRepaint = new Timer(1000/fps, (ActionListener) map);
        tRepaint.start();
    }

    protected void initO(){
        listKid = new ArrayList<Kid>();
        loadMap();

        fps = 30;
        gameMapO = new MapObjects[sizeY][sizeX];

        map = new UI(sizeY,sizeX,blocSize,gameMapO, listKid);
        initMapO();
        this.setTitle("Garden");
        this.setSize(sizeX*blocSize+5, sizeY*blocSize+28);
        this.setContentPane(map);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Timer tRepaint = new Timer(1000/fps, (ActionListener) map);
        tRepaint.start();
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
            addKidO(line);
        }
        fic.close();
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
                gameMap[y][x] = '0'; // 0 pour 0 oeuf
            }
        }
        initBorderMap();
        loadEggsAndRock();
    }

    protected void initMapO(){
        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++){
                gameMapO[y][x] = new MapObjects();
            }
        }
        initBorderMapO();
        loadEggsAndRockO();
        loadKids();
        for (Kid kid : listKid)
            System.out.println(kid);
    }

    private void initBorderMap() {
        for (int x = 0; x<sizeX; x++){
            gameMap[0][x] = 'b';
            gameMap[sizeY-1][x] = 'b'; // b pour bord
        }
        for (int y = 0; y<sizeY; y++){
            gameMap[y][0] = 'b';
            gameMap[y][sizeX-1] = 'b';
        }
    }
    private void initBorderMapO() {
        for (int x = 0; x<sizeX; x++){
            gameMapO[0][x].setBusy(true);
            gameMapO[0][x].setObj(Obj.ROCK);
            gameMapO[sizeY-1][x].setBusy(true);
            gameMapO[sizeY-1][x].setObj(Obj.ROCK);
        }
        for (int y = 0; y<sizeY; y++){
            gameMapO[y][0].setBusy(true);
            gameMapO[y][0].setObj(Obj.ROCK);
            gameMapO[y][sizeX-1].setBusy(true);
            gameMapO[y][sizeX-1].setObj(Obj.ROCK);
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
                    number =  line.charAt(6);
                    addChocolate(posX,posY,(char)number);
                    break;
                case 'R':
                    addRock(posX,posY);
                    break;
            }
        }
        fic.close();
    }

    private void loadEggsAndRockO(){
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
                    number =  line.charAt(6);
                    addChocolateO(posX,posY,(char)number);
                    break;
                case 'R':
                    addRockO(posX,posY);
                    break;
            }
        }
        fic.close();
    }

    private void addChocolate (int posX, int posY, char number){
        if (posX>0 && posX<sizeX && posY>0 && posY<sizeY) {
            gameMap[posY][posX] = java.lang.Character.toChars(number)[0];
        }
    }

    private void addRock (int posX, int posY){
        if (posX>0 && posX<sizeX && posY>0 && posY<sizeY) {
            gameMap[posY][posX] = 'r';
        } else {
            System.out.println("Chargement d'un rocher en dehors du terrain : MISSED");
        }
    }

    private void addChocolateO (int posX, int posY, char number){
        if (posX>0 && posX<sizeX && posY>0 && posY<sizeY) {
            final int nbEgg = java.lang.Character.toChars(number)[0];
            gameMapO[posY][posX].setNumberEggs(nbEgg);
            gameMapO[posY][posX].setObj(Obj.EGG);
            for (int egg = 0; egg < nbEgg; egg++) {
                gameMapO[posY][posX].getListEgg().add(new Egg());
            }
        } else {
            System.out.println("Chargement d'un oeuf en dehors du terrain : WHAT A WASTE");
        }
    }

    private void addRockO (int posX, int posY){
        if (posX>0 && posX<sizeX && posY>0 && posY<sizeY) {
            gameMapO[posY][posX].setBusy(true);
            gameMapO[posY][posX].setObj(Obj.ROCK);
        } else {
            System.out.println("Chargement d'un rocher en dehors du terrain : MISSED");
        }
    }

    private void addKidO(String line){

        int posX = line.charAt(2)-'0';
        int posY = line.charAt(4)-'0';
        System.out.println("KOKO"+ posX + " " + posY);

        if (posX>0 && posX<sizeX && posY>0 && posY<sizeY) {
            if (gameMapO[posY][posX].isBusy()) {
                System.out.println("Chargement d'un enfant sur un rocher : FIRST BLOOD");
            }
            else {
                Kid kid = new Kid();
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
                if (gameMapO[posY][posX].getObj().equals(Obj.EGG))  gameMapO[posY][posX].setObj(Obj.EGGANDKID);
                else gameMapO[posY][posX].setObj(Obj.KID);
                listKid.add(kid);
            }
        } else {
            System.out.println("Chargement d'un enfant en dehors du terrain");
        }
    }

    private void addKid(String line){
        Kid kid = new Kid();
        final int posX = line.charAt(2)-'0';
        final int posY = line.charAt(4)-'0';
        kid.initPos(posX,posY,'E');
        listKid.add(kid);
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

    public char[][] getGameMap() {
        return gameMap;
    }

    public void setGameMap(char[][] gameMap) {
        this.gameMap = gameMap;
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

}
