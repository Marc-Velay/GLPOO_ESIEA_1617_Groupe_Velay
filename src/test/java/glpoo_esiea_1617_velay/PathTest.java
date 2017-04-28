package glpoo_esiea_1617_velay;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PathTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEquals() {
		Path path = new Path();
		assertFalse("Equals not working !", path.equals(1, 1));
	}

	@Test
	public void testAddPoint() {
		Path path = new Path();
		GraphPoint e = new GraphPoint();
		path.getPath().add(e);
		assertFalse("Can't add point to path !", path.getPath().isEmpty());
	}
}