package GLPOO_ESIEA_1617.Groupe_Velay;

import java.util.ArrayList;

public class MapObjects {

	private ArrayList<Egg> listEgg;
	private Obj obj;
	private boolean busy;
	private int numberEggs;
	private GrapheAStar grapheAStar;
	private int dist;


    public Obj getObj() {
        return obj;
    }

    public void setObj(Obj obj) {
        this.obj = obj;
    }

    public MapObjects(int sizeX, int sizeY) {
        listEgg = new ArrayList<Egg>();
        numberEggs = 0;
        obj = Obj.JARDIN;
        busy = false;
        grapheAStar = new GrapheAStar(sizeX, sizeY);
        dist = Integer.MAX_VALUE;
    }

    public MapObjects(ArrayList<Egg> listEgg, Obj obj, boolean busy, int numberEggs, int sizeX, int sizeY) {
        this.listEgg = listEgg;
        this.obj = obj;
        this.busy = busy;
        this.numberEggs = numberEggs;
        grapheAStar = new GrapheAStar(sizeX, sizeY);
        dist = Integer.MAX_VALUE;
    }

    public ArrayList<Egg> getListEgg() {
        return listEgg;
    }

    public void setListEgg(ArrayList<Egg> listEgg) {
        this.listEgg = listEgg;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public int getNumberEggs() {
        return numberEggs;
    }

    public void setNumberEggs(int numberEggs) {
        this.numberEggs = numberEggs;
    }


    public GrapheAStar getGrapheAStar() {
        return grapheAStar;
    }

    public void setGrapheAStar(GrapheAStar grapheAStar) {
        this.grapheAStar = grapheAStar;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }
}
