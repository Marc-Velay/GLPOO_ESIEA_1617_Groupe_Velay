package glpoo_esiea_1617_velay;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * Created by mat on 01/04/2017.
 */
public class Kid extends Player {
    private GrapheAStar grapheAStar;
    private Path pathA;
    private GraphPoint target;
    private ArrayList<java.lang.Character> path;
    private ArrayList<GameObjects> listEgg;
    private JLabel Jname;
    private JProgressBar jBar;
    private char startDirection;
    private int startPosX;
    private int startPosY;
    
    public ArrayList<java.lang.Character> getPath() {
        return path;
    }

    public void setPath(ArrayList<java.lang.Character> path) {
        this.path = path;
    }


    public JLabel getJname() {
        return Jname;
    }

    public void setJname(JLabel jname) {
        Jname = jname;
    }


    public JProgressBar getjBar() {
        return jBar;
    }

    public void setjBar(JProgressBar jBar) {
        this.jBar = jBar;
    }


    public Kid(int max, int sizeX, int sizeY){
        path = new ArrayList<java.lang.Character>();
        listEgg = new ArrayList<GameObjects>();
        jBar = new JProgressBar(0,max);
        Jname = new JLabel();
        grapheAStar = new GrapheAStar(sizeX, sizeY);
        pathA = new Path(false);
        target = new GraphPoint();
    }

    public void initPos(int posX, int posY, char direction){
        setPosX(posX);
        setPosY(posY);
        setStartPosX(posX);
        setStartPosY(posY);
        setDirection(direction);
    }

    protected boolean pickUp(GameObjects [][] gameMap){
        final int nbEgg = gameMap[posY][posX].getNumEggs();
        System.out.println(nbEgg);
        if (nbEgg>0){
            gameMap[posY][posX].setNumEggs(nbEgg-1);
            if (nbEgg-1==0) gameMap[posY][posX].setType(GameItemsList.Kid);
            listEgg.add(gameMap[posY][posX]);
            gameMap[posY][posX].setNumEggs(nbEgg-1);
            this.jBar.setValue(jBar.getValue()+1);
            return true;
        }
        return false;
    }

    public void updatePath(ArrayList<GraphPoint> listEgg, GameObjects [][] gameMap){
        findNextEgg(listEgg, gameMap);
        System.out.println("Update Path()");
        ArrayList<java.lang.Character> chemin = new ArrayList<>();
        if (pathA.isExist()){
            grapheAStar.initAStar(gameMap, posX, posY,target.getX(), target.getY());
            if (grapheAStar.isPathExist()){
                this.setPathA(this.getGrapheAStar().FillPathA());
                System.out.println(posX + " " +posY);
                for (GraphPoint p : pathA.getPath()){
                    // NORD
                    if (p.getX() == posX && p.getY() == posY-1){
                        switch (direction){
                            case 'E':
                                chemin.add('G');
                                break;
                            case 'W':
                                chemin.add('D');
                                break;
                            case 'N':
                                chemin.add('A');
                                break;
                            case 'S':
                                chemin.add('D');
                                break;
                        }
                    } else if (p.getX() == posX && p.getY() == posY+1){
                        // S
                        switch (direction){
                            case 'E':
                                chemin.add('D');
                                break;
                            case 'W':
                                chemin.add('G');
                                break;
                            case 'N':
                                chemin.add('D');
                                break;
                            case 'S':
                                chemin.add('A');
                                break;
                        }

                    } else if (p.getX() == posX-1 && p.getY() == posY){
                        // W
                        switch (direction){
                            case 'E':
                                chemin.add('D');
                                break;
                            case 'W':
                                chemin.add('A');
                                break;
                            case 'N':
                                chemin.add('G');
                                break;
                            case 'S':
                                chemin.add('D');
                                break;
                        }
                    } else if (p.getX() == posX+1 && p.getY() == posY){
                        // E
                        switch (direction){
                            case 'E':
                                chemin.add('A');
                                break;
                            case 'W':
                                chemin.add('D');
                                break;
                            case 'N':
                                chemin.add('D');
                                break;
                            case 'S':
                                chemin.add('G');
                                break;
                        }
                    }

                    System.out.print(p+" , ");
                }
                System.out.println();
                path.clear();
                path.addAll(chemin);
            }
        }
    }

    private void findNextEgg(ArrayList<GraphPoint> listEgg, GameObjects[][] gameMap) {
        int min = Integer.MAX_VALUE;
        int indMin = 0;
        int cpt = 0;
        System.out.println("Recherche de l'oeuf le plus proche");
        if (listEgg.size() > 0) {
            for (GraphPoint p : listEgg){
            	System.out.println(posX + " " + posY);
                gameMap[p.getY()][p.getX()].getGrapheAStar().initAStar(gameMap, posX, posY, p.getX(), p.getY());
                if (gameMap[p.getY()][p.getX()].getGrapheAStar().isPathExist()){
                    gameMap[p.getY()][p.getX()].setDistToPlayer(gameMap[p.getY()][p.getX()].getGrapheAStar().FillPathA().getPath().size());
                    System.out.println("Result Dist : == " + gameMap[p.getY()][p.getX()].getGrapheAStar().FillPathA().getPath().size() );
                    if (gameMap[p.getY()][p.getX()].getDistToPlayer() < min){
                        min = gameMap[p.getY()][p.getX()].getDistToPlayer();
                        indMin = cpt;
                    }
                }
                cpt++;
            }
            System.out.println("min : -- " +indMin +  "Dist " + min);
            target.setX(listEgg.get(indMin).getX());
            target.setY(listEgg.get(indMin).getY());
            pathA.setExist(true);
        } else pathA.setExist(false);
    }

    public void move(GameObjects [][] gameMap){
        if (pickUp(gameMap)){
            System.out.println("Oeuf Ramassé");
        }
        else if (!path.isEmpty()) {
            final char dir = path.get(0);
            System.out.println(this);
            switch (dir) {
                case 'D':
                    path.remove(0);
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
                    path.remove(0);
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
                            moveTop(gameMap,this);
                            break;
                        case 'E':
                            moveRight(gameMap,this);
                            break;
                        case 'W':
                            moveLeft(gameMap,this);
                            break;
                        case 'S':
                            moveDown(gameMap,this);
                            break;
                    }

            }
            System.out.println("Direction = " + direction);
        } else {
            System.out.println("Path is empty" /*+ listEgg*/);

        }
    }

    @Override
    public String toString() {
        return "Kid{" +
                "Name=" + name +
                ", posX=" + posX +
                ", posY=" + posY +
                ", direction=" + direction +
                ", path=" + path +
                '}';
    }


    public char getStartDirection() {
        return startDirection;
    }

    public void setStartDirection(char startDirection) {
        this.startDirection = startDirection;
    }

    public int getStartPosY() {
        return startPosY;
    }

    public void setStartPosY(int startPosY) {
        this.startPosY = startPosY;
    }

    public int getStartPosX() {
        return startPosX;
    }

    public void setStartPosX(int startPosX) {
        this.startPosX = startPosX;
    }

    public GrapheAStar getGrapheAStar() {
        return grapheAStar;
    }

    public void setGrapheAStar(GrapheAStar grapheAStar) {
        this.grapheAStar = grapheAStar;
    }

    public Path getPathA() {
        return pathA;
    }

    public void setPathA(Path pathA) {
        this.pathA = pathA;
    }
}
