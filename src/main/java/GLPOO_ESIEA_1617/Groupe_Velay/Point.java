package GLPOO_ESIEA_1617.Groupe_Velay;

public class Point {
	private int X;
	private int Y;
	
	public Point(){
		
	}
	public Point(int x, int y) {
		super();
		X = x;
		Y = y;
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

    @Override
    public String toString() {
        return "Point{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}
