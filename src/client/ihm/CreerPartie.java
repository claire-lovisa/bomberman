package client.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class CreerPartie extends JPanel implements MouseListener {
	private IHM ihm;
	
	private int nbJoueur;
	private int nbIA;
	
	private Border bordure = new LineBorder(Color.BLACK, 6);
	
	private JTextField textFieldNom;
	private JButton btnCreerPartie;
	private JButton btnMenu;
	private JButton j2;
	private JButton j3;
	private JButton j4;
	private JButton ia0;
	private JButton ia1;
	private JButton ia2;
	private JButton ia3;
	
	public CreerPartie(IHM ihm) {
		setBackground(Color.cyan);
		
		nbJoueur = 4;
		
		this.textFieldNom = new JTextField(25);
		j2 = new JButton("2");
		j3 = new JButton("3");
		j4 = new JButton("4");
		ia0 = new JButton("0");
		ia1 = new JButton("1");
		ia2 = new JButton("2");
		ia3 = new JButton("3");
		this.btnCreerPartie = new JButton("Créer la partie");
		this.btnMenu = new JButton("Retour menu");
		this.ihm = ihm;

		btnCreerPartie.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		j4.setBorder(bordure);
		ia0.setBorder(bordure);
		
		j2.addMouseListener(this);
		j3.addMouseListener(this);
		j4.addMouseListener(this);
		ia0.addMouseListener(this);
		ia1.addMouseListener(this);
		ia2.addMouseListener(this);
		ia3.addMouseListener(this);
		btnCreerPartie.addMouseListener(this);
		btnMenu.addMouseListener(this);

		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(getBackground());
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		
		JPanel panelNom = new JPanel();
		panelNom.add(new JLabel("Nom de la partie "));
		panelNom.add(textFieldNom);
		panelCentral.add(panelNom);
		panelCentral.add(Box.createRigidArea(new Dimension(50, 50)));
		
		JPanel panelNbJoueurs = new JPanel();
		panelNbJoueurs.add(new JLabel("Nombre de joueurs "));
		panelNbJoueurs.add(j2);
		panelNbJoueurs.add(j3);
		panelNbJoueurs.add(j4);
		panelCentral.add(panelNbJoueurs);
		panelCentral.add(Box.createRigidArea(new Dimension(50, 50)));
		
		JPanel panelNbIA = new JPanel();
		panelNbIA.add(new JLabel("Nombre d'IA "));
		panelNbIA.add(ia0);
		panelNbIA.add(ia1);
		panelNbIA.add(ia2);
		panelNbIA.add(ia3);
		panelCentral.add(panelNbIA);
		panelCentral.add(Box.createRigidArea(new Dimension(50, 50)));
		
		panelCentral.add(btnCreerPartie);
		

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		add(panelCentral, gridBagConstraints);
		gridBagConstraints = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 10, 0, 0), 1, 1);
		add(btnMenu, gridBagConstraints);
	}
	
	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(btnMenu)) {
			ihm.displayPanel(PageIHM.MENU);
		}
		else if (e.getSource().equals(btnCreerPartie)) {
			ihm.creerPartie((textFieldNom.getText().equals("") ? "Nouvel partie" : textFieldNom.getText()), nbJoueur, nbIA);
		}
		else if (e.getSource().equals(ia0) || e.getSource().equals(ia1) ||
		         e.getSource().equals(ia2) || e.getSource().equals(ia3)) {
			ia0.setBorder(UIManager.getBorder("Button.border"));
			ia1.setBorder(UIManager.getBorder("Button.border"));
			ia2.setBorder(UIManager.getBorder("Button.border"));
			ia3.setBorder(UIManager.getBorder("Button.border"));
			JButton b = (JButton)e.getSource();
			b.setBorder(bordure);
			this.nbIA = Integer.parseInt(b.getText());
		}
		else if (e.getSource().equals(j2) || e.getSource().equals(j3) ||
		         e.getSource().equals(j4)) {
			j2.setBorder(UIManager.getBorder("Button.border"));
			j3.setBorder(UIManager.getBorder("Button.border"));
			j4.setBorder(UIManager.getBorder("Button.border"));
			ia2.setEnabled(true);
			ia3.setEnabled(true);
			JButton b = (JButton)e.getSource();
			b.setBorder(bordure);
			this.nbJoueur = Integer.parseInt(b.getText());
			if (this.nbJoueur < 4) {
				ia3.setEnabled(false);
				if (this.nbIA == 3) {
					ia3.setBorder(UIManager.getBorder("Button.border"));
					ia2.setBorder(bordure);
					this.nbIA = 2;
				}
				if (this.nbJoueur < 3) {
					ia2.setEnabled(false);
					if (this.nbIA == 2) {
						ia2.setBorder(UIManager.getBorder("Button.border"));
						ia1.setBorder(bordure);
						this.nbIA = 1;
					}
				}
			}
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
}
