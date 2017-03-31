package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.*;
import java.util.Scanner;

public class Garden extends JFrame{
    private int sizeX;
    private int sizeY;
    private int blocSize;
    private int fps;
    private char [][] gameMap;
    private Scanner fic = null;
    private String filenameMap = "map.txt";
    private UI map;


    public Garden () {
        loadMap();
        blocSize = 50;
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

    private void loadMap (){
        fic = openFile(filenameMap);
        fic.next();
        sizeX = fic.nextInt() + 2; // pour les bords
        sizeY = fic.nextInt() + 2; // pour les bords
        System.out.println(sizeX + " " +sizeY);
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

    private void initMap(){
        for (int y = 0; y<sizeY; y++){
            for (int x = 0; x<sizeX; x++){
                gameMap[y][x] = '0'; // 0 pour 0 oeuf
            }
        }
        initBorderMap();
        loadEggsAndRock();
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

    private void addChocolate (int posX, int posY, char number){
        gameMap[posY][posX] = java.lang.Character.toChars(number)[0];
    }

    private void addRock (int posX, int posY){
        gameMap[posY][posX] = 'r';
    }




}
