package glpoo_esiea_1617_velay;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class AskDialog extends JDialog implements ActionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8822573306066265881L;
	@SuppressWarnings("rawtypes")
	private JComboBox sizeX, sizeY;
    private JLabel lsizeX, lsizeY;
    private JButton ok;
    private JButton bCancel;
    private int X, Y;
    private boolean cancel = false;
    private boolean over = false;

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    @Override
    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    @Override
    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public AskDialog(JFrame parent, String title, boolean modal){
        super(parent, title, modal);
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JPanel panelQ = new JPanel();
        GridLayout gl = new GridLayout(2, 2);
        panelQ.setLayout(gl);
        add(panelQ,BorderLayout.CENTER);
        ok = new JButton("Valider");
        add(ok, BorderLayout.SOUTH);
        bCancel = new JButton("Annuler");
        bCancel.setMaximumSize(new Dimension(80,50));
        ok.setMaximumSize(new Dimension(80,50));
        add(bCancel, BorderLayout.NORTH);
        lsizeX = new JLabel("Taille X");
        lsizeY = new JLabel("Taille Y");
        sizeX = new JComboBox();
        sizeY = new JComboBox();

        panelQ.add(lsizeX);
        panelQ.add(sizeX);
        panelQ.add(lsizeY);
        panelQ.add(sizeY);
        sizeX.addItem("6");
        sizeX.addItem("7");
        sizeX.addItem("8");
        sizeX.addItem("9");
        sizeX.addItem("10");
        sizeX.addItem("11");
        sizeX.addItem("12");
        sizeX.addItem("13");
        sizeY.addItem("6");
        sizeY.addItem("7");
        sizeY.addItem("8");
        sizeY.addItem("9");
        sizeX.setSelectedIndex(7);
        sizeY.setSelectedIndex(3);
        ok.addActionListener(this);
        bCancel.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            X = (int) Integer.valueOf(sizeX.getSelectedItem().toString());
            Y = (int) Integer.valueOf(sizeY.getSelectedItem().toString());
            over = true;
            setVisible(false);
        	System.out.println(X + " Y: " + Y);
            EditorGarden.setSizeX(X);
            EditorGarden.setSizeY(Y);
        }
        else if (e.getSource() == bCancel){
            over = true;
            cancel = true;
            setVisible(false);
        }
    }

    public boolean isCancel() {
        return cancel;
    }
}
