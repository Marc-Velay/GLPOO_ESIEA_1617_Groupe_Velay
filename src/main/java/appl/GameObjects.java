package appl;


public class GameObjects {
	private int X;
	private int Y;
	private int sizeX;
	private int sizeY;
	private GameItemsList type;
	private int numEggs = 0;
	private boolean occupied = false;
	private int distToPlayer;
	private GrapheAStar grapheAStar;
	
	public GameObjects(){
		
	}
	public GameObjects(int sizeX, int sizeY, int x, int y, GameItemsList type) {
		super();
		this.X = x;
		this.Y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.type = type;
	}
	
	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	public GameItemsList getType() {
		return type;
	}
	public void setType(GameItemsList type) {

		if(type == GameItemsList.Egg) {
			System.out.println("created graph");
			setGrapheAStar(new GrapheAStar(sizeX, sizeY));
			this.setDistToPlayer(Integer.MAX_VALUE);			
		}
		this.type = type;
	}
	public int getNumEggs() {
		return numEggs;
	}
	public void setNumEggs(int numEggs) {
		this.numEggs = numEggs;
	}
	public boolean isOccupied() {
		return occupied;
	}
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	public int getDistToPlayer() {
		return distToPlayer;
	}
	public void setDistToPlayer(int distToPlayer) {
		this.distToPlayer = distToPlayer;
	}
	public GrapheAStar getGrapheAStar() {
		System.out.println("created graph");
		return grapheAStar;
	}
	public void setGrapheAStar(GrapheAStar grapheAStar) {
		this.grapheAStar = grapheAStar;
	}
	@Override
    public String toString() {
        return "Point{" +
                "X=" + X +
                ", Y=" + Y +
                "}, type: " +
                type;
    }
	
}
