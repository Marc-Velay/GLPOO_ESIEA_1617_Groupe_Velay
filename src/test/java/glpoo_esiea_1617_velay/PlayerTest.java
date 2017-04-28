package glpoo_esiea_1617_velay;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testName() {
		Player n = new Player(); 
		assertNotNull("No player name inserted !",n.name);
	}

	@Test
	public void testScore() {
		Player n = new Player(); 
		n.score = 10;
		assertEquals("Could not get score !",10,n.getScore());
	}
	
	@Test
	public void testPlayerPos() {
		Player n = new Player(); 
		n.posX = 10;
		n.posY = 10;
		assertEquals("Wrong X coordinate !",10,n.posX);
		assertEquals("Wrong Y coordinate !",10,n.posY);
	}
	
	@Test
	public void testPlayerDirection() {
		Player n = new Player(); 
		n.direction = 'A';
		assertEquals("Could not get direction !",'A',n.direction);
	}
	/*
	@Test
	public void testMoveTop() {
		
		Player n = new Player();
		GameObjects fill = new GameObjects(0,0,0,0,GameItemsList.Empty);
		
		GameObjects[][] gameMap = new GameObjects [10][10];
		for(int i=0; i < 9; i++){
			for(int j=0; j < 9; j++){
				gameMap[i][j] = fill;
			}
		}
		Kid kid = new Kid(0,0,0);
		
		n.posX = 5;
		n.posY = 5;
		n.moveTop(gameMap, kid);
		
		assertEquals("Didn't move up !",5-1,n.posY);
	}
	*/
}
