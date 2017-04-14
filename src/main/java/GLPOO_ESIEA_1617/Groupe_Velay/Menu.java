package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by kafim on 14/04/2017.
 */
public class Menu extends JFrame implements ActionListener{
    private JButton bPlay;
    private JButton bEditeur;
    private JButton bSelectMap;
    private JButton bBotPlay;
    private JButton bQuit;

    public Menu(){
        this.setLayout(new GridLayout(5,1));
        this.setSize(400,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        bPlay = new JButton("Play");
        bBotPlay = new JButton("Ia Play");
        bEditeur = new JButton("Editeur");
        bSelectMap = new JButton("Select Map");
        bQuit = new JButton("Quit");

        bPlay.addActionListener(this);
        bBotPlay.addActionListener(this);
        bEditeur.addActionListener(this);
        bSelectMap.addActionListener(this);
        bQuit.addActionListener(this);

        this.add(bPlay);
        this.add(bBotPlay);
        this.add(bEditeur);
        this.add(bSelectMap);
        this.add(bQuit);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bPlay){
            System.out.println("Lancement d'une partie");
            Garden garden = new Garden();
            garden.initUI();
            garden.loadItems();
        }
        else if (e.getSource() == bBotPlay){
            System.out.println("Lancement d'une partie avec l'ordinateur");


        }
        else if (e.getSource() == bEditeur){
            System.out.println("Lancement de l'éditeur");
            Garden garden = new Garden();
            garden.initEditeur();
        }
        else if (e.getSource() == bSelectMap){
            System.out.println("Choix de la carte et des enfants");
            EditeurViewer edV = new EditeurViewer();
        }
        else if (e.getSource() == bQuit){
            System.out.println("Exit");
            this.dispose();
            System.exit(0);
        }
    }
}
