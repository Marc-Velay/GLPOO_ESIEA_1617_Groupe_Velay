package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Garden extends JPanel{
    private int sizeX;
    private int sizeY;
    private int [][] gameMap;
    private Scanner fic = null;
    private String filenameMap = "map.txt";
    private UI ui;


    public Garden () {
        loadMap();
        gameMap = new int[sizeY][sizeX];
        ui = new UI(sizeY,sizeX,gameMap);
    }

    private void loadMap (){
        fic = openFile(filenameMap);
        fic.next();
        sizeX = fic.nextInt();
        sizeY = fic.nextInt();
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

    public Image chargerImage(String nomImg) {
        Image img;
        System.out.println("Image/"+nomImg+".png");
        ImageIcon imageIcon = new ImageIcon("Image/"+nomImg+".png");
        img = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        return img;
    }


}
