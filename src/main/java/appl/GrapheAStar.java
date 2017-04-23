package appl;


import java.util.ArrayList;

public class GrapheAStar {
	//private int [][]map;
	private int H;
	private int W;
	private Node S;
	private Node D;
	private boolean empty;
	private boolean pathExist;
	private ArrayList<Node> E;
	private ArrayList<Node> V;



	public void initAStar(GameObjects[][]map, int X1, int Y1, int X2, int Y2){
		Node S = new Node(X1,Y1);
		setS(S);
		Node D = new Node(X2,Y2);
		setD(D);
		aStar(S,D,map);
		System.out.println("A Star INIT : ("+X2+","+Y2+") -> ("+X1+","+Y1+")");
	}

	public GrapheAStar(int h, int w){
		H = h;
		W = w;
		S = new Node();
		D = new Node();
		empty = true;
		pathExist = false;
		E = new ArrayList<Node>();
		V = new ArrayList<Node>();
	}

	private void aStar(Node S, Node D,GameObjects[][]map){
		V.clear();
		E.clear();
		Node X = new Node();
		Node T = new Node();
		S.setDist(calculDistH(S,D));
		S.setDistSource(0);
		S.setPred('S');
		E.add(S);
		empty = false;

		//-------------------------------------------
		// IL FAUT GERER QUAND IL n'y A PAS DE CHEMIN
		//-------------------------------------------
		while(!E.isEmpty() && !isContained(E,D)){
			X = E.get(0);
			E.remove(0);
			V.add(X);
			
			// Up
			if (X.getY()>0){
				T.setX(X.getX());
				T.setY(X.getY()-1);
				if (!map[T.getY()][T.getX()].isOccupied() && !isContained(V,T)) // add node up in E
				{
					T.setDistSource(X.getDistSource()+1);
					T.setDist(T.getDistSource()+calculDistH(T,D));
					T.setPred('D');
					addSortedNode(E,T);
				}

			}
			// Down
			if (X.getY()<H-1){
				T.setX(X.getX());
				T.setY(X.getY()+1);
				if (!map[T.getY()][T.getX()].isOccupied() && !isContained(V,T)) // add node down in E
				{
					T.setDistSource(X.getDistSource()+1);
					T.setDist(T.getDistSource()+calculDistH(T,D));
					T.setPred('U');
					addSortedNode(E,T);
				}
			}

			// Right
			if (X.getX()<W-1){
				T.setX(X.getX()+1);
				T.setY(X.getY());
				if (!map[T.getY()][T.getX()].isOccupied() && !isContained(V,T)) // add node right in E
				{
					T.setDistSource(X.getDistSource()+1);
					T.setDist(T.getDistSource()+calculDistH(T,D));
					T.setPred('L');
					addSortedNode(E,T);
					//System.out.println("add RIGHT");
				}
			}
			// Left
			if (X.getX()>0){
				T.setX(X.getX()-1);
				T.setY(X.getY());
				if (!map[T.getY()][T.getX()].isOccupied() && !isContained(V,T)) // add node left in E
				{
					T.setDistSource(X.getDistSource()+1);
					T.setDist(T.getDistSource()+calculDistH(T,D));
					T.setPred('R');
					addSortedNode(E,T);
					//System.out.println("add LEFT");
				}
			}
		}
		if (!E.isEmpty()){
			X = E.get(0);
			E.remove(0);
			V.add(X);
			pathExist = true;
		}
		else
		{
			pathExist = false;
			System.out.println("NO PATH AVAILABLE");
		}

	}

	// renvoi si le noeud est dans la liste en v�rifiant juste ses positions
	private boolean isContained(ArrayList<Node> L, Node A){
		for (int i=0; i<L.size(); i++){
			if (A.getX() == L.get(i).getX() && A.getY() == L.get(i).getY())
				return true;
		}
		return false;
	}

	public Path FillPathA(){
		if (!empty && pathExist){
			Path path = new Path(true);
			Node P = new Node(V.get(V.size()-1));
			while (P.getPred()!='S'){
				path.add(new GraphPoint(P.getX(),P.getY()));
				if (P.getPred()=='U')		P = extractFromList(P.getX(),P.getY()-1);
				else if (P.getPred()=='D')	P = extractFromList(P.getX(),P.getY()+1);
				else if (P.getPred()=='R')	P = extractFromList(P.getX()+1,P.getY());
				else if (P.getPred()=='L')	P = extractFromList(P.getX()-1,P.getY());
			}
			path.add(new GraphPoint(P.getX(),P.getY()));
			for (int i = 0; i< path.getPath().size(); i++){
				System.out.print("("+ path.getPath().get(i).getX()+","+path.getPath().get(i).getY()+") -> ");
			}
			System.out.println();
			return path;
		}
		return null;

	}


	private Node extractFromList(int x, int y){
		int i = 0;
		while (i < V.size()){
			if (V.get(i).getX() == x && V.get(i).getY() == y){
				return new Node(V.get(i));
			}
			i++;
		}
		return new Node(0,0);
	}

	private int calculDistH(Node S, Node A){
		return Math.abs(S.getX()-A.getX()) + Math.abs(S.getY()-A.getY());
	}

	private void addSortedNode (ArrayList<Node> L, Node A){
		// on utilise un nouveau noeud pour �viter des probl�mes de r�f�rences
		Node Ad = new Node();
		Ad.setDist(A.getDist());
		Ad.setDistSource(A.getDistSource());
		Ad.setPred(A.getPred());
		Ad.setX(A.getX());
		Ad.setY(A.getY());
		boolean OK = false;
		int i = 0;
		while (!OK && i<L.size()){
			if (Ad.getX() == L.get(i).getX() && Ad.getY() == L.get(i).getY()){
				OK = true;
				// deja dans E
				if (Ad.getDist() < L.get(i).getDist()){
					L.get(i).setDist(Ad.getDist());
					// update el E
				}
			}
			else{
				if (Ad.getDist() > L.get(i).getDist()){
					i++;
				}
				else{
					L.add(i,Ad);
					OK = true;
					// Ajout milieu
				}
			}
		}
		if (!OK)
			L.add(Ad);
			// Ajout en tete ou en fin
	}

	public int getH() {
		return H;
	}

	public void setH(int h) {
		H = h;
	}

	public int getW() {
		return W;
	}

	public void setW(int w) {
		W = w;
	}

	public Node getS() {
		return S;
	}

	public void setS(Node s) {
		S = s;
	}

	public Node getD() {
		return D;
	}

	public void setD(Node d) {
		D = d;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public ArrayList<Node> getE() {
		return E;
	}

	public void setE(ArrayList<Node> e) {
		E = e;
	}

	public ArrayList<Node> getV() {
		return V;
	}

	public void setV(ArrayList<Node> v) {
		V = v;
	}

	/**
	 * @return the pathExist
	 */
	public boolean isPathExist() {
		return pathExist;
	}

	/**
	 * @param pathExist the pathExist to set
	 */
	public void setPathExist(boolean pathExist) {
		this.pathExist = pathExist;
	}
	
}
