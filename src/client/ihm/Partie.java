package client.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Partie extends JPanel implements MouseListener {
	private JButton btnRejoindrePartie;
	private JButton btnCreerPartie;
	private JButton btnMenu;
	private IHM ihm;

	public Partie(IHM ihm) {
		setBackground(Color.cyan);

		this.btnRejoindrePartie = new JButton("Rejoindre une partie");
		this.btnCreerPartie = new JButton("Créer une partie");
		this.btnMenu = new JButton("Retour menu");
		this.ihm = ihm;

		btnRejoindrePartie.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCreerPartie.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnRejoindrePartie.addMouseListener(this);
		btnCreerPartie.addMouseListener(this);
		btnMenu.addMouseListener(this);

		JPanel panelBoutons = new JPanel();
		panelBoutons.setBackground(getBackground());
		panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.Y_AXIS));
		panelBoutons.add(btnRejoindrePartie);
		panelBoutons.add(Box.createRigidArea(new Dimension(100, 100)));
		panelBoutons.add(btnCreerPartie);

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		add(panelBoutons, gridBagConstraints);
		gridBagConstraints = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 10, 0, 0), 1, 1);
		add(btnMenu, gridBagConstraints);
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(btnMenu)) {
			ihm.displayPanel(PageIHM.MENU);
		}
		else if (e.getSource().equals(btnRejoindrePartie)) {
			ihm.rejoindrePartie();
		}
		else if (e.getSource().equals(btnCreerPartie)) {
			ihm.displayPanel(PageIHM.CREER_PARTIE);
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
}
