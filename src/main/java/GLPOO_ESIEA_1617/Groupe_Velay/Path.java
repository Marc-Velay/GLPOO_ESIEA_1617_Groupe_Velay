package GLPOO_ESIEA_1617.Groupe_Velay;

import java.util.ArrayList;

public class Path {
	private ArrayList<Point> path = null;
	private boolean exist;
	
	public Path(){
		path = new ArrayList<Point>();
		exist = false;
	}
	public Path(boolean exist){
		path = new ArrayList<Point>();
		this.exist = exist;
	}
	
	public void affiche(){
		for (int i=0; i<path.size(); i++){
			System.out.print("("+path.get(i).getX()+","+path.get(i).getY()+") -> ");
		}
		System.out.println();
	}
	public ArrayList<Point> getPath() {
		return path;
	}

	public void setPath(ArrayList<Point> path) {
		this.path = path;
	}
	

	public void add(Point point) {
		// TODO Auto-generated method stub
		this.path.add(point);
	}
	
	public char direction(int x1, int x2, int y1, int y2){
		if 		(x1+1 == x2 && y1 == y2) return 'R';
		else if (x1-1 == x2 && y1 == y2) return 'L';
		else if (x1 == x2 && y1+1 == y2) return 'D';
		else if (x1 == x2 && y1-1 == y2) return 'U';
		else return '0';
	}

	/**
	 * @return the exist
	 */
	public boolean isExist() {
		return exist;
	}

	/**
	 * @param exist the exist to set
	 */
	public void setExist(boolean exist) {
		this.exist = exist;
	}
	public boolean equals(int x, int y) {
		if (!this.path.isEmpty()){
			if (this.path.get(this.path.size()-1).getX() == x
					&& this.path.get(this.path.size()-1).getY() == y)
				return true;
			
				for (int i=0; i<this.path.size(); i++){
					if (this.path.get(i).getX() == x 
							&& this.path.get(i).getY() == y ){
						System.out.println("path equal 2 ways");
						return true;
					}
			}
		}
		return false;
	}

}
