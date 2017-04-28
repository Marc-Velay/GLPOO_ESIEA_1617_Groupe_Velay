package glpoo_esiea_1617_velay;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class KidTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPos() {
		Kid kid = new Kid(1,1,1);
		kid.setPosX(1);
		kid.setPosY(1);
		assertEquals("Could not set X coordinate !",1,kid.posX);
		assertEquals("Could not set Y coordinate !",1,kid.posY);
	}
	
	@Test
	public void testStartPos() {
		Kid kid = new Kid(1,1,1);
		kid.setStartPosX(1);
		kid.setStartPosY(1);
		assertEquals("Could not get X coordinate !",1,kid.getStartPosX());
		assertEquals("Could not get Y coordinate !",1,kid.getStartPosX());
	}
	
}	
