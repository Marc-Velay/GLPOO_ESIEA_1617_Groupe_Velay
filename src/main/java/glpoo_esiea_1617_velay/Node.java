package glpoo_esiea_1617_velay;

public class Node {
	private int y;
	private int x;
	private int dist;
	private int distSource;
	private char pred;
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getDist() {
		return dist;
	}
	public void setDist(int dist) {
		this.dist = dist;
	}
	public char getPred() {
		return pred;
	}
	public void setPred(char pred) {
		this.pred = pred;
	}
	public int getDistSource() {
		return distSource;
	}
	public void setDistSource(int distSource) {
		this.distSource = distSource;
	}
	
	public Node(){
		
	}
	public Node(int x, int y){
		this.x=x;
		this.y=y;
	}
	public Node(Node A) {
		this.y = A.y;
		this.x = A.x;
		this.dist = A.dist;
		this.distSource = A.distSource;
		this.pred = A.pred;
	}
	
	
}
