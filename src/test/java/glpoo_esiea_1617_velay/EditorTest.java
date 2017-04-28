package glpoo_esiea_1617_velay;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EditorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEditorHUDInstanced() {
		assertNotNull(" Editor HUD not instanced !",EditorGardenHUD.getInstance(1, 1, 1, 1));
	}
	
	@Test
	public void testEditorSizeSet() {
		EditorGarden.setSizeX(1);
		EditorGarden.setSizeY(1);
		assertEquals(" Editor size x not properly set !",1,EditorGarden.getSizeX());
		assertEquals(" Editor size y not properly set !",1,EditorGarden.getSizeY());
	}

}
