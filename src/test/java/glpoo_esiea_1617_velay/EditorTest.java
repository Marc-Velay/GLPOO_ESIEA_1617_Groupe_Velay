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
	
	

}
