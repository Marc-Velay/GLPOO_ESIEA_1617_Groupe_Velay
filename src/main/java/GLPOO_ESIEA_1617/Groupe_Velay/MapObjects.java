package GLPOO_ESIEA_1617.Groupe_Velay;

import java.util.ArrayList;

public class MapObjects {

	private ArrayList<Egg> listEgg;
	private Obj obj;
	private boolean busy;
	private int numberEggs;


    public Obj getObj() {
        return obj;
    }

    public void setObj(Obj obj) {
        this.obj = obj;
    }

    public MapObjects() {
        listEgg = new ArrayList<Egg>();
        numberEggs = 0;
        obj = Obj.JARDIN;
        busy = false;
    }

    public MapObjects(ArrayList<Egg> listEgg, Obj obj, boolean busy, int numberEggs) {
        this.listEgg = listEgg;
        this.obj = obj;
        this.busy = busy;
        this.numberEggs = numberEggs;
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



	
}
