package glpoo_esiea_1617_velay;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class EditorGardenUI extends JPanel implements ActionListener,
		MouseListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2016323681122277300L;
	private static EditorGardenUI instance; 							// Singleton
	private JButton bSave;												//Bouton Sauver
	private JButton bPath;												//Bouton Chemin des Kid
	private JButton bNext;												//Bouton Next
	private JButton bPrev;												//Bouton Prev
	private GameItemsList objActuel;
	private Image objImgActuel;											//Element sélectionné par la souris
	private Image rock;
	private Image terre;
	private Image oeuf;
	private Image kidE;
	private Image kidW;
	private Image kidS;
	private Image kidN;
	private Image arrowLeft;
	private Image arrowRight;
	private Image arrowDown;
	private Image arrowUp;
	private Font font = new Font("Courier", Font.BOLD, 20);
	private static int sizeX;											
	private static int sizeY;
	private int blocSize;
	private int xActuel;												//Coordonée x de la souris
	private int yActuel;												//Coordonée y de la souris
	private int xPathActuel;
	private int yPathActuel;
	private Kid kidActual;
	private int kidNumber;
	private ArrayList<Kid> listKid;
	private static GameObjects[][] gameMap;
	private boolean BLOCKED = false;
	private int etape;
	private EditorGardenHUD hud;										

	public EditorGardenUI(int sizeY, int sizeX, int blocSize,
			GameObjects[][] gameMap, ArrayList<Kid> listKid, EditorGardenHUD hud) {
		EditorGardenUI.sizeX = sizeX;
		EditorGardenUI.sizeY = sizeY;
		this.blocSize = blocSize;
		EditorGardenUI.gameMap = EditorGarden.getGameMap();
		this.hud = hud;
		this.bPath = new JButton("Chemin des Kids");
		this.bPath.addActionListener(this);
		this.bNext = new JButton("Next");
		this.bNext.addActionListener(this);
		this.bPrev = new JButton("Prev");
		this.bPrev.addActionListener(this);
		loadImages();
		bSave = new JButton("Sauver");
		bSave.addActionListener(this);
		add(bPath);
		add(bPrev);
		add(bNext);
		add(bSave);
		bPrev.setVisible(false);
		bNext.setVisible(false);
		bSave.setVisible(false);
		objImgActuel = null;
		objActuel = GameItemsList.Rock;
		etape = 1;
		this.listKid = listKid;
		hud.setEtape(1);

	}
/**	Singleton de la classe EditorGardenUI
 * 
 * @param sizeX		largeur du jardin
 * @param sizeY 	profondeur du jardin
 * @param blocSize	Taille d'un bloc en pixels
 * @param gameMap	Carte du Jardin
 * @param listKid	Liste des enfants
 * @param hud		HUD
 * @return			Instance de EditorGardenUI
 */
	public static synchronized EditorGardenUI getInstance(int sizeX, int sizeY,
			int blocSize, GameObjects[][] gameMap, ArrayList<Kid> listKid,
			EditorGardenHUD hud) {
		if (instance == null) {
			instance = new EditorGardenUI(sizeX, sizeY, blocSize, gameMap,
					listKid, hud);
		}
		return instance;
	}
/**Charge l'image nomImg depuis le dossier "src/main/resources/Images/"
 * 
 * @param nomImg nom de l'image à charger
 * @return L'image chargée
 */
	public Image chargerImage(String nomImg) {
		Image img;
		System.out.println(Game.IMGPATH + nomImg + ".png");
		ImageIcon imageIcon = new ImageIcon(Game.IMGPATH + nomImg + ".png");
		img = imageIcon.getImage().getScaledInstance(blocSize, blocSize,
				Image.SCALE_DEFAULT);
		return img;
	}
	
	private void loadImages() { // Charge touts les images
		rock = chargerImage("mur");
		oeuf = chargerImage("oeuf");
		terre = chargerImage("terre");
		kidE = chargerImage("E1");
		kidW = chargerImage("O1");
		kidS = chargerImage("S1");
		kidN = chargerImage("N1");
		arrowLeft = chargerImage("arrowLeft");
		arrowRight = chargerImage("arrowRight");
		arrowUp = chargerImage("arrowUp");
		arrowDown = chargerImage("arrowDown");
	}
	
	/** Ordonne l'affichage de la carte
	 * 
	 * @param gameMap Carte du jardin
	 * @param listKid List des Enfants
	 * @param g 
	 */
	private void afficheCarte(GameObjects[][] gameMap, ArrayList<Kid> listKid,
			Graphics g) {
		g.setFont(font);
		g.setColor(Color.black);
		int counter = 0;
		for (int y = 0; y < sizeY; y++) {
			for (int x = 0; x < sizeX; x++) {
				afficherImage(terre, x * blocSize, y * blocSize, g); // On affiche la terre partout
				// System.out.println(gameMap.length + " " + gameMap[0].length +
				// gameMap[y][x].getType() + " counter: " + counter++);
				if (gameMap[y][x].getType().equals(GameItemsList.Rock))
					afficherImage(rock, blocSize * x, blocSize * y, g);
				else if (gameMap[y][x].getType().equals(GameItemsList.Egg)) {
					afficherImage(oeuf, x * blocSize, y * blocSize, g);
					g.drawString(String.valueOf(gameMap[y][x].getNumEggs()), x
							* blocSize + 4 * blocSize / 5, y * blocSize
							+ blocSize);
				} else if (gameMap[y][x].getType().equals(
						GameItemsList.EggAndKid)) {
					afficherImage(oeuf, x * blocSize, y * blocSize, g);
					g.drawString(String.valueOf(gameMap[y][x].getNumEggs()), x
							* blocSize + 4 * blocSize / 5, y * blocSize
							+ blocSize);
				}
			}
		}

		drawKids(gameMap, listKid, g);
	}

	private void drawKids(GameObjects[][] gameMap, ArrayList<Kid> listKid,
			Graphics g) {
		for (Kid kid : listKid) {
			// showPath(gameMap, kid, g);
			switch (kid.getDirection()) {
			case 'E':
				afficherImage(kidE, kid.getPosX() * blocSize, kid.getPosY()
						* blocSize, g);
				break;
			case 'N':
				afficherImage(kidN, kid.getPosX() * blocSize, kid.getPosY()
						* blocSize, g);
				break;
			case 'W':
				afficherImage(kidW, kid.getPosX() * blocSize, kid.getPosY()
						* blocSize, g);
				break;
			case 'S':
				afficherImage(kidS, kid.getPosX() * blocSize, kid.getPosY()
						* blocSize, g);
				break;
			}
		}
	}

	private void drawKid(Graphics g, Kid kid) {
		if (kid != null) {
			switch (kid.getDirection()) {
			case 'E':
				afficherImage(kidE, kid.getPosX() * blocSize, kid.getPosY()
						* blocSize, g);
				break;
			case 'N':
				afficherImage(kidN, kid.getPosX() * blocSize, kid.getPosY()
						* blocSize, g);
				break;
			case 'W':
				afficherImage(kidW, kid.getPosX() * blocSize, kid.getPosY()
						* blocSize, g);
				break;
			case 'S':
				afficherImage(kidS, kid.getPosX() * blocSize, kid.getPosY()
						* blocSize, g);
				break;
			}
		}
	}

	private void afficherImage(Image img, int x, int y, Graphics g) {	//Fonction d'affichage basique
		g.drawImage(img, x, y, this);
		Toolkit.getDefaultToolkit().sync();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		afficheCarte(gameMap, listKid, g);
		// System.out.println("AFFICHAGE MAP");
		// afficheMapConsole();
		if (etape == 1) {
			afficherImage(objImgActuel, xActuel, yActuel, g); // on affiche l'élément actuel
			drawKids(gameMap, listKid, g);
		}
		if (etape == 2) {
			afficherChemin(g);
		}
	}

	private void afficherChemin(Graphics g) {
		drawKid(g, kidActual);
		drawPathKid(g, kidActual);
		repaint();
	}

	private void drawPathKid(Graphics g, Kid kid) {
		// System.out.println(yPathActuel+" "+ xPathActuel);
		if (!BLOCKED) {
			if (xPathActuel < sizeX - 1
					&& !gameMap[yPathActuel][xPathActuel + 1].isOccupied()) {
				afficherImage(arrowRight, (xPathActuel + 1) * blocSize,
						yPathActuel * blocSize, g);
			}
			if (xPathActuel > 0
					&& !gameMap[yPathActuel][xPathActuel - 1].isOccupied()) {
				afficherImage(arrowLeft, (xPathActuel - 1) * blocSize,
						yPathActuel * blocSize, g);
			}
			if (yPathActuel < sizeY - 1
					&& !gameMap[yPathActuel + 1][xPathActuel].isOccupied()) {
				afficherImage(arrowDown, xPathActuel * blocSize,
						(yPathActuel + 1) * blocSize, g);
			}
			if (yPathActuel > 0
					&& !gameMap[yPathActuel - 1][xPathActuel].isOccupied()) {
				afficherImage(arrowUp, xPathActuel * blocSize,
						(yPathActuel - 1) * blocSize, g);
			}
		}
	}

	private void deleteKid(int y, int x) {
		for (Kid kid : listKid) {
			if (kid.getPosX() == x && kid.getPosY() == y && kid != kidActual) {
				listKid.remove(kid);
				break;
			}
		}
	}

	private void addSelected(int posX) {
		// ici on mettra en valeur quelle obj est selectionné.
		// TODO
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) { // Tracking de la souris
		xActuel = arg0.getX() - blocSize / 2;
		yActuel = arg0.getY() - blocSize;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!BLOCKED) {
			BLOCKED = true;
			if (etape == 1) {
				System.out.println("souris clique : " + e.getX() + " "
						+ e.getY());
				// si on clique dans le terrain
				if (e.getX() > blocSize && e.getX() < blocSize * (sizeX + 2)
						&& e.getY() > blocSize + 25
						&& e.getY() < blocSize * (sizeY)) {
					System.out.println("souris clique : " + e.getX() + " "
							+ e.getY());
					switch (objActuel) {
					case Rock:
						gameMap[e.getY() / blocSize - 1][e.getX() / blocSize]
								.setType(GameItemsList.Rock);
						gameMap[e.getY() / blocSize - 1][e.getX() / blocSize]
								.setNumEggs(0);
						gameMap[e.getY() / blocSize - 1][e.getX() / blocSize]
								.setOccupied(true);
						break;
					case Egg:
						gameMap[e.getY() / blocSize - 1][e.getX() / blocSize]
								.setType(GameItemsList.Egg);
						gameMap[e.getY() / blocSize - 1][e.getX() / blocSize]
								.setNumEggs(gameMap[e.getY() / blocSize - 1][e
										.getX() / blocSize].getNumEggs() + 1);
						break;
					case Kid:
						char oldDirection = kidActual.getDirection();
						gameMap[e.getY() / blocSize - 1][e.getX() / blocSize]
								.setType(GameItemsList.Kid);
						gameMap[e.getY() / blocSize - 1][e.getX() / blocSize]
								.setNumEggs(0);
						kidActual.setPosX(e.getX() / blocSize);
						kidActual.setPosY(e.getY() / blocSize - 1);
						kidActual.setStartPosX(e.getX() / blocSize);
						kidActual.setStartPosY(e.getY() / blocSize - 1);
						listKid.add(kidActual);
						deleteKid(e.getY() / blocSize - 1, e.getX() / blocSize);
						System.out.println(listKid);
						kidActual = new Kid(0, sizeX, sizeY);
						kidActual.setDirection(oldDirection);
						kidActual.setStartDirection(oldDirection);
						break;
					case Empty:
						gameMap[e.getY() / blocSize - 1][e.getX() / blocSize]
								.setType(GameItemsList.Empty);
						gameMap[e.getY() / blocSize - 1][e.getX() / blocSize]
								.setNumEggs(0);
						break;
					default: // Default is set to empty
						gameMap[e.getY() / blocSize - 1][e.getX() / blocSize]
								.setType(GameItemsList.Empty);
						gameMap[e.getY() / blocSize - 1][e.getX() / blocSize]
								.setNumEggs(0);
						break;
					}
				}
				// si on clique dans le HUD pour choisir un objet à rajouter
				if (e.getX() > blocSize / 2 && e.getX() < 15 * blocSize / 2
						&& e.getY() > 2 * blocSize + sizeY * blocSize
						&& e.getY() < 3 * blocSize + sizeY * blocSize) {
					System.out.println("CLIQUE DANS LE HUD" + sizeY);
					if (e.getX() > blocSize / 2 && e.getX() < 3 * blocSize / 2) {
						addSelected(1);
						objImgActuel = null;
						objActuel = GameItemsList.Empty;
					} else if (e.getX() > 3 * blocSize / 2
							&& e.getX() < 5 * blocSize / 2) {
						addSelected(3);
						objImgActuel = rock;
						objActuel = GameItemsList.Rock;
					} else if (e.getX() > 5 * blocSize / 2
							&& e.getX() < 7 * blocSize / 2) {
						objImgActuel = oeuf;
						objActuel = GameItemsList.Egg;
					} else if (e.getX() > 7 * blocSize / 2
							&& e.getX() < 9 * blocSize / 2) {
						objImgActuel = kidE;
						objActuel = GameItemsList.Kid;
						kidActual = new Kid(0, sizeX, sizeY);
						kidActual.setDirection('E');
						kidActual.setStartDirection('E');
					} else if (e.getX() > 9 * blocSize / 2
							&& e.getX() < 11 * blocSize / 2) {
						objImgActuel = kidN;
						objActuel = GameItemsList.Kid;
						kidActual = new Kid(0, sizeX, sizeY);
						kidActual.setStartDirection('N');
						kidActual.setDirection('N');
					} else if (e.getX() > 11 * blocSize / 2
							&& e.getX() < 13 * blocSize / 2) {
						objImgActuel = kidW;
						objActuel = GameItemsList.Kid;
						kidActual = new Kid(0, sizeX, sizeY);
						kidActual.setStartDirection('W');
						kidActual.setDirection('W');
					} else if (e.getX() > 13 * blocSize / 2
							&& e.getX() < 15 * blocSize / 2) {
						objImgActuel = kidS;
						objActuel = GameItemsList.Kid;
						kidActual = new Kid(0, sizeX, sizeY);
						kidActual.setDirection('S');
						kidActual.setStartDirection('S');
					}
				}
			} else if (etape == 2) {
				// au dessus
				if (e.getX() > xPathActuel * blocSize
						&& e.getX() < (xPathActuel + 1) * blocSize
						&& e.getY() + 12 > yPathActuel * blocSize
						&& e.getY() + 12 < (yPathActuel + 1) * blocSize) {
					if (!gameMap[yPathActuel - 1][xPathActuel].getType()
							.equals(GameItemsList.Rock)) {
						switch (kidActual.getDirection()) {
						case 'N':
							yPathActuel--;
							kidActual.setPosY(yPathActuel);
							kidActual.getPath().add('A');
							break;
						case 'W':
							kidActual.getPath().add('D');
							kidActual.setDirection('N');
							break;
						case 'E':
							kidActual.getPath().add('G');
							kidActual.setDirection('N');
							break;
						case 'S':
							kidActual.getPath().add('D');
							kidActual.setDirection('W');
							break;
						}

					}
				}
				// en dessous
				if (e.getX() > xPathActuel * blocSize
						&& e.getX() < (xPathActuel + 1) * blocSize
						&& e.getY() + 12 > (yPathActuel + 2) * blocSize
						&& e.getY() + 12 < (yPathActuel + 3) * blocSize) {
					if (!gameMap[yPathActuel + 1][xPathActuel].getType()
							.equals(GameItemsList.Rock)) {
						switch (kidActual.getDirection()) {
						case 'S':
							yPathActuel++;
							System.out.println("en dessous");
							kidActual.setPosY(yPathActuel);
							kidActual.getPath().add('A');
							break;
						case 'W':
							kidActual.getPath().add('G');
							kidActual.setDirection('S');
							break;
						case 'E':
							kidActual.getPath().add('D');
							kidActual.setDirection('S');
							break;
						case 'N':
							System.out.println("en dessous");
							kidActual.getPath().add('D');
							kidActual.setDirection('E');
							break;
						}

					}
				}
				// a droite
				if (e.getX() > (xPathActuel + 1) * blocSize
						&& e.getX() < (xPathActuel + 2) * blocSize
						&& e.getY() + 12 > (yPathActuel + 1) * blocSize
						&& e.getY() + 12 < (yPathActuel + 2) * blocSize) {
					if (!gameMap[yPathActuel][xPathActuel + 1].getType()
							.equals(GameItemsList.Rock)) {
						switch (kidActual.getDirection()) {
						case 'E':
							xPathActuel++;
							System.out.println("a droite");
							kidActual.setPosX(xPathActuel);
							kidActual.getPath().add('A');
							break;
						case 'N':
							kidActual.setDirection('E');
							kidActual.getPath().add('D');
							break;
						case 'S':
							kidActual.getPath().add('G');
							System.out.println("a droite");
							kidActual.setDirection('E');
							break;
						case 'W':
							kidActual.getPath().add('D');
							System.out.println("a droite");
							kidActual.setDirection('N');
							break;
						}

					}
				}
				// a gauche
				if (e.getX() > (xPathActuel - 1) * blocSize
						&& e.getX() < xPathActuel * blocSize
						&& e.getY() + 12 > (yPathActuel + 1) * blocSize
						&& e.getY() + 12 < (yPathActuel + 2) * blocSize) {
					if (!gameMap[yPathActuel][xPathActuel - 1].getType()
							.equals(GameItemsList.Rock)) {
						switch (kidActual.getDirection()) {
						case 'W':
							xPathActuel--;
							System.out.println("a gauche");
							kidActual.setPosX(xPathActuel);
							kidActual.getPath().add('A');
							break;
						case 'N':
							kidActual.setDirection('W');
							kidActual.getPath().add('D');
							break;
						case 'S':
							System.out.println("a gauche");
							kidActual.getPath().add('G');
							kidActual.setDirection('W');
							break;
						case 'E':
							kidActual.getPath().add('D');
							System.out.println("a gauche");
							kidActual.setDirection('S');
							break;
						}
					}
				}
				if (kidActual != null)
					hud.printPath(kidActual.getPath());
				hud.repaint();
			}
			repaint();
			BLOCKED = false;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	if (e.getSource() == bPath) {// Si on clique sur le bouton "Chemin des Kis"
			BLOCKED = true;
			etape = 2;
			this.remove(bPath);
			bPrev.setVisible(true);
			bNext.setVisible(true);
			hud.setEtape(2);
			hud.setKidMax(listKid.size());
			if (listKid.size() == kidNumber) {
				this.remove(bPrev);
				this.remove(bNext);
				bSave.setVisible(true);
			} else if (listKid.size() > 0 && listKid.get(0) != null) {
				kidActual = listKid.get(0);
				kidNumber = 0;
				xPathActuel = kidActual.getPosX();
				yPathActuel = kidActual.getPosY();
			}
			hud.repaint();
			BLOCKED = false;
		}
		if (e.getSource() == bNext) { // On avance au Kid suivant 
			BLOCKED = true;
			kidNumber++;
			if (listKid.size() == kidNumber) {
				this.remove(bPrev);
				this.remove(bNext);
				bSave.setVisible(true);
			} else if (listKid.size() > kidNumber && kidNumber >= 0
					&& listKid.get(kidNumber) != null) {
				hud.setKidActual(kidNumber);
				hud.printPath(null);
				kidActual = listKid.get(kidNumber);
				xPathActuel = kidActual.getPosX();
				yPathActuel = kidActual.getPosY();
				System.out.println(kidActual);
			} else
				kidNumber--;
			hud.repaint();
			BLOCKED = false;
		}
		if (e.getSource() == bPrev) { // On revient en arrière sur le kid précédent
			BLOCKED = true;
			kidNumber--;
			if (listKid.size() > kidNumber && kidNumber >= 0
					&& listKid.get(kidNumber) != null) {
				hud.setKidActual(kidNumber);
				hud.printPath(null);
				kidActual = listKid.get(kidNumber);
				xPathActuel = kidActual.getPosX();
				yPathActuel = kidActual.getPosY();
				System.out.println(kidActual);
			} else
				kidNumber++;
			hud.repaint();
			BLOCKED = false;
		} else if (e.getSource() == bSave) {
			saveMapAndKidMoves();
		}
	}

	@SuppressWarnings("unused")
	private void saveMapAndKidMoves() {		
		JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
		String nom = JOptionPane.showInputDialog(null,
				"Rentrez le nom de la carte", "Request",
				JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(null, "Carte enregistrée " + nom,
				"Message", JOptionPane.INFORMATION_MESSAGE);
		File fmap = null, fkid = null;
		
		if ((new File("Ressources/map/")).mkdirs())		//Check si les dossier existe et création de ces derniers si besoin
			fmap = new File("Ressources/map/" + nom);
		else {
			System.err.println("Cannot create directory map");
		}
		if ((new File("Ressources/kid/")).mkdirs())
			fkid = new File("Ressources/kid/" + nom);
		else {
			System.err.println("Cannot create directory kid");
		}

		try {	//Rempli les fichier à sauvegarder
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
					fmap)));
			pw.println("J " + sizeX + " " + sizeY);
			for (int y = 1; y < sizeY - 1; y++) {
				for (int x = 1; x < sizeX - 1; x++) {
					if (gameMap[y][x].getType().equals(GameItemsList.Egg)) {
						pw.println("C " + x + "-" + y + " "
								+ gameMap[y][x].getNumEggs());
					} else if (gameMap[y][x].getType().equals(
							GameItemsList.Rock)) {
						pw.println("R " + x + "-" + y);
					}
				}
			}
			pw.close();
			pw = new PrintWriter(new BufferedWriter(new FileWriter(fkid)));

			for (Kid kid : listKid) {
				String s = kid.getPath().toString();
				s = s.replaceAll("\\[", "");
				s = s.replaceAll("\\]", "");
				s = s.replaceAll(",", "");
				s = s.replaceAll(" ", "");
				pw.println("E " + kid.getStartPosX() + "-" + kid.getStartPosY()
						+ " " + kid.getStartDirection() + " " + s + " "
						+ "ORDI");
			}
			pw.close();
		} catch (IOException exception) {
			JOptionPane.showInternalMessageDialog(null, exception.getMessage());
			JOptionPane.showMessageDialog(null, exception.getMessage());
		}
	}

	public static void setSizeX(int sizeX) {
		EditorGardenUI.sizeX = sizeX;
	}

	public static void setSizeY(int sizeY) {
		EditorGardenUI.sizeY = sizeY;
	}

	public static void setGameMap(GameObjects[][] gameMap) {
		EditorGardenUI.gameMap = gameMap;
	}
}
