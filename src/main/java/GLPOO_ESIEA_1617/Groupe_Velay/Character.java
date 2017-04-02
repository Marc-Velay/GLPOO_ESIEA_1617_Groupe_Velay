package GLPOO_ESIEA_1617.Groupe_Velay;

public abstract class Character implements Player, AI {
	
	protected int score = 0;
	protected int posX = 0;
	protected int posY = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected String name = "";
    protected char direction = ' ';
	
	
	public void movePlayer(char dir){
		switch (dir){
			case 't':
				moveTop();
				break;	
			case 'd':
				moveDown();
				break;
			case 'l':
				moveLeft();
				break;
			case 'r':
				moveRight();
				break;
		}
		
	}
	
	public void moveTop(){
		posY -= 1;
		
	}
	
	public void moveDown(){
		posY += 1;
		
	}
	
	public void moveRight(){
		posX += 1;
		
	}
	
	public void moveLeft(){
		posX -= 1;
		
	}
	
	public void pickUp(int valEgg){
		addScore(valEgg);
		
	}
	
	public void addScore(int val){
		score += val;
		
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public void setPosX(int posX) {
		this.posX = posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void setPosY(int posY) {
		this.posY = posY;
	}

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

	
}
