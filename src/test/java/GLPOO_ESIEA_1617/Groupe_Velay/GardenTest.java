package GLPOO_ESIEA_1617.Groupe_Velay;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


/**
 * Created by mat on 01/04/2017.
 */
public class GardenTest {

    private Garden garden;

    @Before
    public void doBefore() {
        System.out.println("Debut Test Garden");
        garden = new Garden();
        garden.setFilenameMap("Ressources/Test/map.txt");

    }
    @Test
    public void testLoadMap() {
        garden.loadMap();
        assertEquals(6,garden.getSizeY());
        assertEquals(6,garden.getSizeY());
    }
    @Test
    public void testInitMap() {
        garden.initUI();
        garden.gettRepaint().stop();
        final MapObjects[][] arrayExpected =
                {
                        {newBlock(), newBlock(), newBlock(), newBlock(), newBlock(), newBlock()},
                        {newBlock(), newJardin(), newJardin(), newJardin(), newJardin(), newBlock()},
                        {newBlock(), newJardin(), newJardin(), newJardin(), newJardin(), newBlock()},
                        {newBlock(), newJardin(), newJardin(), newJardin(), newJardin(), newBlock()},
                        {newBlock(), newJardin(), newJardin(), newJardin(), newJardin(), newBlock()},
                        {newBlock(), newBlock(), newBlock(), newBlock(), newBlock(), newBlock()}
                };

        compareArray(arrayExpected);
    }

    @Test
    public void testLoadItems() {
        garden.initUI();
        garden.loadItems();
        garden.gettRepaint().stop();
        final MapObjects[][] arrayExpected =
                {
                        {newBlock(), newBlock(), newBlock(), newBlock(), newBlock(), newBlock()},
                        {newBlock(), newKid(), newJardin(), newKid(), newJardin(), newBlock()},
                        {newBlock(), newJardin(), newBlock(), newEgg(1), newJardin(), newBlock()},
                        {newBlock(), newEgg(3), newJardin(), newJardin(), newJardin(), newBlock()},
                        {newBlock(), newJardin(), newJardin(), newJardin(), newJardin(), newBlock()},
                        {newBlock(), newBlock(), newBlock(), newBlock(), newBlock(), newBlock()}
                };
        compareArray(arrayExpected);
    }

    @Test
    public void testAfterRun() {
        garden.initUI();
        garden.loadItems();
        garden.gettRepaint().setDelay(1);
        final MapObjects[][] arrayExpected =
                {
                        {newBlock(), newBlock(), newBlock(), newBlock(), newBlock(), newBlock()},
                        {newBlock(), newJardin(), newJardin(), newJardin(), newJardin(), newBlock()},
                        {newBlock(), newJardin(), newBlock(), newJardin(), newJardin(), newBlock()},
                        {newBlock(), newJardin(), newJardin(), newKid(), newJardin(), newBlock()},
                        {newBlock(), newJardin(), newKid(), newJardin(), newJardin(), newBlock()},
                        {newBlock(), newBlock(), newBlock(), newBlock(), newBlock(), newBlock()}
                };
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        compareArray(arrayExpected);
    }

    private void compareArray(MapObjects[][] arrayExpected){
        for (int row=0; row<6; row++)
            for (int col=0; col<6; col++){
                assertEquals(arrayExpected[row][col].getNumberEggs(),getNbOeuf(col,row));
                assertEquals(arrayExpected[row][col].getListEgg().size(),getListEgg(col,row).size());
                assertEquals(arrayExpected[row][col].getObj(),getObj(col,row));
                assertEquals(arrayExpected[row][col].isBusy(),getBusy(col,row));
            }
    }





    private boolean getBusy(int col, int row){
        MapObjects [][] array = garden.getGameMap();
        return array[row][col].isBusy();
    }

    private int getNbOeuf(int col, int row){
        MapObjects [][] array = garden.getGameMap();
        return array[row][col].getNumberEggs();
    }

    private ArrayList<Egg> getListEgg(int col, int row){
        MapObjects [][] array = garden.getGameMap();
        return array[row][col].getListEgg();
    }
    private Obj getObj(int col, int row){
        MapObjects [][] array = garden.getGameMap();
        return array[row][col].getObj();
    }

    private MapObjects newBlock(){
        return new MapObjects(new ArrayList<Egg>(),Obj.ROCK,true,0);
    }

    private MapObjects newJardin(){
        return new MapObjects(new ArrayList<Egg>(),Obj.JARDIN,false,0);
    }

    private MapObjects newEgg(int nombre){
        MapObjects MO = new MapObjects(new ArrayList<Egg>(),Obj.EGG,false,nombre);
        for (int i=0; i<nombre; i++){
            MO.getListEgg().add(new Egg());
        }
        return MO;
    }

    private MapObjects newKid(){
        return new MapObjects(new ArrayList<Egg>(),Obj.KID,true,0);
    }



}
