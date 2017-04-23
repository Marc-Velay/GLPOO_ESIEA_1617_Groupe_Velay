package appl;

public class Player {

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

    private void moveAction(GameObjects [][]gameMap, int valX, int valY, Kid kid){
        if (gameMap[posY+valY][posX+valX].isOccupied()){
            System.out.println(valX + " " + valY + " BUSY");
            if (gameMap[posY+valY][posX+valX].getType().equals(GameItemsList.Kid)){
                System.out.println("KID DETECTED :  GOING on Pause mode");
            } else {
                kid.getPath().remove(0);
            }
        } else{
            gameMap[posY+valY][posX+valX].setOccupied(true);
            gameMap[posY][posX].setOccupied(false);
            gameMap[posY][posX].setType(GameItemsList.Empty);
            if(gameMap[posY+valY][posX+valX].getType().equals(GameItemsList.Egg)){
                gameMap[posY+valY][posX+valX].setType(GameItemsList.EggAndKid);
            } else {
                gameMap[posY+valY][posX+valX].setType(GameItemsList.Kid);
            }
            posY += valY;
            posX += valX;
            kid.getPath().remove(0);
        }
    }

	protected void moveTop(GameObjects [][]gameMap, Kid kid){
        moveAction(gameMap,0,-1, kid);
	}
	
	protected void moveDown(GameObjects [][]gameMap, Kid kid){
        moveAction(gameMap,0,1, kid);
	}
	
	protected void moveRight(GameObjects [][]gameMap, Kid kid){
        moveAction(gameMap,1,0, kid);
	}
	
	protected void moveLeft(GameObjects [][]gameMap, Kid kid){
        moveAction(gameMap,-1,0, kid);
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
