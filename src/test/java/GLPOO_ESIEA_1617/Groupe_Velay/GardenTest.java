package GLPOO_ESIEA_1617.Groupe_Velay;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


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
    public void testInit() {
        garden.init();
        final char[][] arrayExpected =
                {
                        {'b', 'b', 'b', 'b', 'b', 'b'},
                        {'b', '0', '0', '0', '0', 'b'},
                        {'b', '0', 'r', '1', '0', 'b'},
                        {'b', '3', '0', '0', '0', 'b'},
                        {'b', '0', '0', '0', '0', 'b'},
                        {'b', 'b', 'b', 'b', 'b', 'b'}
                };
        for (int row=0; row<6; row++)
            assertArrayEquals(arrayExpected[row],getRow(row));
    }

    private char [] getRow(int row){
        final char [][] array = garden.getGameMap();
        return array[row];
    }

}
