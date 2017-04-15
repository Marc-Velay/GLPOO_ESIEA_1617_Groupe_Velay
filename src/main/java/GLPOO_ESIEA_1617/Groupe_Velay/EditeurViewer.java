package GLPOO_ESIEA_1617.Groupe_Velay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by kafim on 14/04/2017.
 */
public class EditeurViewer extends JPanel implements ActionListener {

    private JFrame container;
    private JFrame f = new JFrame();
    private int ind = 0;
    private JButton bC1 = new JButton();
    private JButton bC2 = new JButton();
    private JButton bC3 = new JButton();
    private JButton bC4 = new JButton();
    private JLabel lCarte = new JLabel();
    private String mapA;
    private JButton bClearCard = new JButton();
    private JButton bQuit = new JButton();
    private JButton bRet = new JButton();
    private MiniView pan1;
    private MiniView pan2;
    private MiniView pan3;
    private MiniView pan4;

    private int i = 0;
    JButton bNext = new JButton("Contenu suivant");
    JButton bPrev = new JButton("Contenu prédédant");
    CardLayout cl = new CardLayout();
    JPanel content = new JPanel();
    //JFrame f = new JFrame();
    //Liste des noms de nos conteneurs pour la pile de cartes
    int indice = 0;
    ArrayList<String> fa = new ArrayList<String>();
    File fp = new File("Ressources/map");
    File[] filp = fp.listFiles();

    public EditeurViewer(){
        f.setSize(930, 795);

        for (int i=0; i<filp.length; i++)	{
            System.out.println("Ressources/map/"+filp[i].getName());
            fa.add("Ressources/map/"+filp[i].getName());
        }

        container = new JFrame();
        //container.setSize(950, 750);
        JPanel boutonPane = new JPanel();

        //Définition de l'action du bouton
        bNext.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                //Via cette instruction, on passe au prochain conteneur de la pile
                System.out.println("ind : " +ind);
                pan1.getGarden().gettRepaint().stop();
                pan2.getGarden().gettRepaint().stop();
                pan3.getGarden().gettRepaint().stop();
                pan4.getGarden().gettRepaint().stop();

                //if (ind<fa.size()-4)
                //  ind++;
                createView();
                System.out.println("ind : " +ind);
                //On ajoute les cartes à la pile avec un nom pour les retrouver
                content.add(container.getContentPane(), "tot");
                cl.next(content);
            }
        });
        bPrev.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                //Via cette instruction, on passe au prochain conteneur de la pile
                System.out.println("ind : " +ind);
                pan1.getGarden().gettRepaint().stop();
                pan2.getGarden().gettRepaint().stop();
                pan3.getGarden().gettRepaint().stop();
                pan4.getGarden().gettRepaint().stop();

                if (ind>=8)
                    ind-=8;
                createView();
                System.out.println("ind : " +ind);
                //On ajoute les cartes à la pile avec un nom pour les retrouver
                content.add(container.getContentPane(), "tot");
                cl.next(content);
            }
        });

        bQuit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                f.dispose();
            }
        });
        bC1.addActionListener(this);
        bC2.addActionListener(this);
        bC3.addActionListener(this);
        bC4.addActionListener(this);
        bRet.addActionListener(this);
        JPanel cardB = new JPanel();
        cardB.add(bC1);
        cardB.add(bC2);
        cardB.add(bC3);
        cardB.add(bC4);
        bClearCard.addActionListener(this);
        bClearCard.setText("Clear");
        lCarte.setText("Carte selectionné : " + "map");
        bQuit.setText("Save and Quit");
        bRet.setText("Quitter");
        boutonPane.add(bRet);
        boutonPane.add(bPrev);
        boutonPane.add(bNext);
        boutonPane.add(lCarte);
        boutonPane.add(bQuit);



        content.setLayout(cl);
        createView();
        content.add(container.getContentPane(), "tot");

        container.setVisible(false);

        f.getContentPane().add(boutonPane, BorderLayout.NORTH);

        f.getContentPane().add(content, BorderLayout.CENTER);

        f.getContentPane().add(cardB, BorderLayout.SOUTH);
        //f.pack();
        //this.setVisible(false);
        f.setLocationRelativeTo(null);
        f.setTitle("Affichage Cartes");
        f.setVisible(true);
        repaint();

    }

    public void createView(){
        container.getContentPane().removeAll();
        GridLayout gl = new GridLayout(2,2);
        gl.setHgap(15);
        gl.setVgap(15);
        container.setLayout(gl);

        if (ind<fa.size()){
            System.out.println("ind : " +ind);
            pan1 = new MiniView(fa.get(ind));
            pan1.getGarden().gettRepaint().start();
            bC1.setText(fa.get(ind).substring(15));
            container.add(pan1);
        }
        ind++;
        if (ind<fa.size()){
            System.out.println("ind : " +ind);
            pan2 = new MiniView(fa.get(ind));
            pan2.getGarden().gettRepaint().start();
            bC2.setText(fa.get(ind).substring(15));
            container.add(pan2);
        }
        ind++;
        if (ind<fa.size()){
            System.out.println("ind : " +ind);
            pan3 = new MiniView(fa.get(ind));
            pan3.getGarden().gettRepaint().start();
            bC3.setText(fa.get(ind).substring(15));
            container.add(pan3);
        }
        ind++;
        if (ind<fa.size()){
            System.out.println("ind : " +ind);
            pan4 = new MiniView(fa.get(ind));
            pan4.getGarden().gettRepaint().start();
            bC4.setText(fa.get(ind).substring(15));
            container.add(pan4);
        }
        ind++;


        if (ind>=fa.size())
        {
            bNext.setEnabled(false);
        }
        else
            bNext.setEnabled(true);

        if (ind<=4)
        {
            bPrev.setEnabled(false);
        }
        else
            bPrev.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == bC1){
            System.out.println(bC1.getText());
            updateChoice(bC1.getText());
            i = ind-4;
            lCarte.setText("Carte selectionné : " + mapA + i);

        }
        else if (e.getSource() == bC2){
            System.out.println(bC2.getText());
            updateChoice(bC2.getText());
            i = ind-3;
            lCarte.setText("Carte selectionné : " + mapA+ i);

        }
        else if (e.getSource() == bC3){
            System.out.println(bC3.getText());
            updateChoice(bC3.getText());
            i = ind -2;
            lCarte.setText("Carte selectionné : " + mapA+ i);

        }
        else if (e.getSource() == bC4){
            System.out.println(bC4.getText());
            updateChoice(bC4.getText());
            i = ind-1;
            lCarte.setText("Carte selectionné : " + mapA+ i);

        }
        else if (e.getSource() == bRet){
            f.dispose();
        }
    }

    private void updateChoice(String text) {
        // TODO Auto-generated method stub
        mapA = text;
    }



}
