package appl;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class Menu extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1991283571021884083L;
	//Items in menu UI
    private JButton bPlay;
    private JButton bEditeur;
    private JButton bSelectMap;
    private JButton bBotPlay;
    private JButton bQuit;
	private static Menu instance;
	
    public Menu(){
    	//Create game window
        this.setLayout(new GridLayout(5,1));
        this.setSize(400,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Creation de la carte de jeu
        //garden = new Garden();

        //Initialise buttons
        bPlay = new JButton("Play");
        bBotPlay = new JButton("IA Play");
        bEditeur = new JButton("Editeur");
        bSelectMap = new JButton("Select Map");
        bQuit = new JButton("Quit");
        
        //Add button action listeners
        bPlay.addActionListener(this);
        bBotPlay.addActionListener(this);
        bEditeur.addActionListener(this);
        bSelectMap.addActionListener(this);
        bQuit.addActionListener(this);

        //Add buttons to window
        this.add(bPlay);
        this.add(bBotPlay);
        this.add(bEditeur);
        this.add(bSelectMap);
        this.add(bQuit);

        this.setVisible(true);
    }
    
	public static synchronized Menu getInstance() {
		if (instance == null) {
			instance = new Menu();
		}
		return instance;
	}

	
    public void actionPerformed(ActionEvent e) {
    	//If the button Play is clicked, launch game
        if (e.getSource() == bPlay){
            System.out.println("Lancement d'une partie");
            PlayGarden garden = new PlayGarden();
        }
        //If AI is selected launch game as automatic
        else if (e.getSource() == bBotPlay){
            System.out.println("Lancement d'une partie avec l'ordinateur");
            /*
            garden = new Garden();
            garden.setMode(Mode.Auto);
            garden.initUI();
            garden.loadItems();
            */
        }
        //If editor is selected launch editor
        else if (e.getSource() == bEditeur){
            System.out.println("Lancement de l'éditeur");
            /*
            garden = new Garden();
            garden.setMode(Mode.Normal);
            garden.initEditeur();
            */
        }
        //If map selection is selected, launch picker
        else if (e.getSource() == bSelectMap){
            System.out.println("Choix de la carte et des enfants");
            // affichage presque OK En faire quelque chose choix de la map mode auto ou pas
            // correction des bugs a faire
        }
        //Exit the game
        else if (e.getSource() == bQuit){
            System.out.println("Exit");
            this.dispose();
            System.exit(0);
        }
    }
}
