package GLPOO_ESIEA_1617.Groupe_Velay;

public class MapObjects {
	
	private int posX;
	private int posY;
	private int score;
	private String name;
	
	MapObjects(final String name, final int score, int posX, int posY) {
		this.name = name;
		this.score = score;
		this.posX = posX;
		this.posY = posY;
	}
	
	public void setCoords(int posX, int posY) {
	}

	public int getX() {
		return this.posX;
	}
	
	public int getY() {
		return this.posY;
	}
	
	public boolean getPickable() {
		if(this.score > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getName() {
		return this.name;
	}
}
