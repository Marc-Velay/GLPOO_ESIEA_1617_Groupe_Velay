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

    public void move(char dir){
        switch (dir){
            case 't':
                moveTop();
                break;
            case 'd':
                moveDown();
                break;
            case 'l':
                moveLeft();
                break;
            case 'r':
                moveRight();
                break;
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
