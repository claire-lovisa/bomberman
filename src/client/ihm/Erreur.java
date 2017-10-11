package client.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Erreur extends JPanel implements MouseListener {

	private JButton boutonRedirection;
	private PageIHM redirection;
	private IHM ihm;

	public Erreur(IHM ihm, String message, PageIHM redirection) {
		setBackground(Color.cyan);

		this.boutonRedirection = new JButton("Retour");
		this.redirection = redirection;
		this.ihm = ihm;

		boutonRedirection.setAlignmentX(Component.CENTER_ALIGNMENT);
		boutonRedirection.addMouseListener(this);
		
		JTextArea textArea = new JTextArea(message, 6, 20);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setOpaque(false);
		textArea.setEditable(false);

		JPanel panelBoutons = new JPanel();
		panelBoutons.setBackground(getBackground());
		panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.Y_AXIS));
		panelBoutons.add(textArea);
		panelBoutons.add(Box.createRigidArea(new Dimension(50, 50)));
		panelBoutons.add(boutonRedirection);

		setLayout(new GridBagLayout());
		add(panelBoutons, new GridBagConstraints());
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(boutonRedirection)) {
			ihm.displayPanel(this.redirection);
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
}