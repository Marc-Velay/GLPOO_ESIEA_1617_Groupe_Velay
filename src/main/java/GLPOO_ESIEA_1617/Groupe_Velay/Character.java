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

    private void moveAction(MapObjects [][]gameMap, int valX, int valY, Kid kid){
        if (gameMap[posY+valY][posX+valX].isBusy()){
            System.out.println(valX + " " + valY + " BUSY");
            if (gameMap[posY+valY][posX+valX].getObj().equals(Obj.KID)){
                System.out.println("KID DETECTED :  GOING on Pause mode");
            } else {
                kid.getPath().remove(0);
            }
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
            kid.getPath().remove(0);
        }

    }

	public void moveTop(MapObjects [][]gameMap, Kid kid){
        moveAction(gameMap,0,-1, kid);
	}
	
	public void moveDown(MapObjects [][]gameMap, Kid kid){
        moveAction(gameMap,0,1, kid);
	}
	
	public void moveRight(MapObjects [][]gameMap, Kid kid){
        moveAction(gameMap,1,0, kid);
	}
	
	public void moveLeft(MapObjects [][]gameMap, Kid kid){
        moveAction(gameMap,-1,0, kid);
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
