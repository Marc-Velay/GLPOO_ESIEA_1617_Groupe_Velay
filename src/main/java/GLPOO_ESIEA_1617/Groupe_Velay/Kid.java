package GLPOO_ESIEA_1617.Groupe_Velay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by mat on 01/04/2017.
 */
public class Kid extends Character {
    public ArrayList<java.lang.Character> getPath() {
        return path;
    }

    public void setPath(ArrayList<java.lang.Character> path) {
        this.path = path;
    }

    private ArrayList<java.lang.Character> path;


    public Kid(){
        path = new ArrayList<java.lang.Character>();
    }

    public void initPos(int posX, int posY, char direction){
        setPosX(posX);
        setPosY(posY);
        setDirection(direction);
    }

    protected boolean pickUp(MapObjects [][] gameMap){
        final int nbEgg = gameMap[posY][posX].getNumberEggs();
        System.out.println(nbEgg);
        if (nbEgg>0){
            gameMap[posY][posX].setNumberEggs(nbEgg-1);
            if (nbEgg-1==0) gameMap[posY][posX].setObj(Obj.KID);
            return true;
        }
        return false;
    }

    public void move(MapObjects [][] gameMap){
        if (pickUp(gameMap)){
            System.out.println("Oeuf Ramassé");
        }
        else if (!path.isEmpty()) {
            final char dir = path.get(0);
            path.remove(0);
            switch (dir) {
                case 'D':
                    System.out.println("Tourne à droite");
                    switch (direction) {
                        case 'N':
                            direction = 'E';
                            break;
                        case 'E':
                            direction = 'S';
                            break;
                        case 'S':
                            direction = 'W';
                            break;
                        case 'W':
                            direction = 'N';
                            break;
                    }
                    break;
                case 'G':
                    System.out.println("Tourne à gauche");
                    switch (direction) {
                        case 'N':
                            direction = 'W';
                            break;
                        case 'E':
                            direction = 'N';
                            break;
                        case 'S':
                            direction = 'E';
                            break;
                        case 'W':
                            direction = 'S';
                            break;
                    }
                    break;
                case 'A':
                    System.out.println("Avance");
                    switch (direction) {
                        case 'N':
                            moveTop(gameMap);
                            break;
                        case 'E':
                            moveRight(gameMap);
                            break;
                        case 'W':
                            moveLeft(gameMap);
                            break;
                        case 'S':
                            moveDown(gameMap);
                            break;
                    }

            }
            System.out.println("Direction = " + direction);
        } else {
            System.out.println("Path is empty");
        }
    }

    @Override
    public String toString() {
        return "Kid{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", direction=" + direction +
                ", path=" + path +
                '}';
    }


}
