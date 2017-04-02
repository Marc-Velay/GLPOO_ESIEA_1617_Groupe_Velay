package GLPOO_ESIEA_1617.Groupe_Velay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by mat on 01/04/2017.
 */
public class Kid extends Character {
    private String Chemin = "";
    public Kid(){

    }

    public void initPos(int posX, int posY){
        setPosX(posX);
        setPosY(posY);
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
