package glpoo_esiea_1617_velay;

public class GraphPoint {
	private int X;
	private int Y;
	
	public GraphPoint(){
		
	}
	public GraphPoint(int x, int y) {
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
