package glpoo_esiea_1617_velay;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NodeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNodePos() {
		Node node = new Node(1,1);
		assertEquals(" Incorrect X pos !", 1, node.getX());
		assertEquals(" Incorrect Y pos !", 1, node.getY());
	}
	
	@Test
	public void testNodeDist() {
		Node node = new Node(1,1);
		node.setDist(1);
		assertEquals(" Incorrect distance !", 1, node.getDist());
	}
	
	@Test
	public void testNodePred() {
		Node node = new Node(1,1);
		node.setPred('l');
		assertEquals(" Incorrect pred !", 'l', node.getPred());
	}

	@Test
	public void testNodeDistSrc() {
		Node node = new Node(1,1);
		node.setDistSource(1);
		assertEquals(" Incorrect distance source !", 1, node.getDistSource());
	}
}
