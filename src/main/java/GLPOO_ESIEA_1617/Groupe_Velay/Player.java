package GLPOO_ESIEA_1617.Groupe_Velay;

public interface Player {
	
	int score = 0;
	int posX = 0;
	int posY = 0;
	
	
	public void movePlayer(char dir);
	public void moveTop();
	public void moveDown();
	public void moveRight();
	public void moveLeft();
	public void pickUp();
	public void AddScore();
	
	public void setScore();
	public void setPosX();
	public void setPosY();
	
	public int getScore();
	public int getPosX();
	public int getPosY();
	
	
	
}
