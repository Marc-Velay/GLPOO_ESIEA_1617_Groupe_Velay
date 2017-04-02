package GLPOO_ESIEA_1617.Groupe_Velay;

public abstract class Character implements AI {
	
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

    private void moveAction(MapObjects [][]gameMap, int valX, int valY){
        if (gameMap[posY+valY][posX+valX].isBusy()){
            System.out.println(valX + " " + valY + " BUSY");
        } else{
            gameMap[posY+valY][posX+valX].setBusy(true);
            gameMap[posY][posX].setBusy(false);
            gameMap[posY][posX].setObj(Obj.JARDIN);
            if(gameMap[posY+valY][posX+valX].getObj().equals(Obj.EGG)){
                gameMap[posY+valY][posX+valX].setObj(Obj.EGGANDKID);
            } else {
                gameMap[posY+valY][posX+valX].setObj(Obj.KID);
            }
            posY += valY;
            posX += valX;
        }

    }

	public void moveTop(MapObjects [][]gameMap){
        moveAction(gameMap,0,-1);
	}
	
	public void moveDown(MapObjects [][]gameMap){
        moveAction(gameMap,0,1);
	}
	
	public void moveRight(MapObjects [][]gameMap){
        moveAction(gameMap,1,0);
	}
	
	public void moveLeft(MapObjects [][]gameMap){
        moveAction(gameMap,-1,0);
	}
	
	public void pickUp(int valEgg){
		addScore(valEgg);
		
	}
	
	public void addScore(int val){
		score += val;
		
	}
    public void movePlayer(char dir) {

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
