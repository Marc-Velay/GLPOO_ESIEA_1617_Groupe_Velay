package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
        blocSize = 30;
        fps = 30;
        gameMap = new char[sizeY][sizeX];
        map = new UI(sizeY,sizeX,blocSize,gameMap);
        this.setTitle("Garden");
        this.setSize(sizeX*blocSize+15, sizeY*blocSize+38);
        this.setContentPane(map);
       // this.setResizable(false);
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




}
