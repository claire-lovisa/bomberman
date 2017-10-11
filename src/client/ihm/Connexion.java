package client.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Connexion extends JPanel implements MouseListener {
	
	private JRadioButton localButton;
	private JRadioButton ipButton;
	private JTextField textFieldAdresseIP;
	private JButton boutonConnexion;
	private IHM ihm;

	public Connexion(IHM ihm) {
		setBackground(Color.cyan);
		
		this.localButton = new JRadioButton("Localhost");
    this.localButton.setActionCommand("test");
		this.localButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.localButton.setBackground(getBackground());
		this.localButton.addMouseListener(this);
    this.localButton.setSelected(true);
		
		this.ipButton = new JRadioButton("Adresse IP");
    this.ipButton.setActionCommand("test2");
		this.ipButton.addMouseListener(this);
		this.ipButton.setBackground(getBackground());
		
		ButtonGroup group = new ButtonGroup();
    group.add(this.localButton);
    group.add(this.ipButton);
		
		this.textFieldAdresseIP = new JTextField(15);
		this.textFieldAdresseIP.setEnabled(false);
		this.textFieldAdresseIP.setBackground(Color.gray);
		
		this.boutonConnexion = new JButton("Connexion");
		this.boutonConnexion.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.boutonConnexion.addMouseListener(this);

		this.ihm = ihm;

		JPanel panelBoutons = new JPanel();
		panelBoutons.setBackground(getBackground());
		panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.Y_AXIS));
		panelBoutons.add(localButton);
		panelBoutons.add(Box.createRigidArea(new Dimension(100, 100)));
		JPanel panelAdresseIp = new JPanel();
		panelAdresseIp.setBackground(getBackground());		
		panelAdresseIp.add(ipButton);
		panelAdresseIp.add(textFieldAdresseIP);
		panelBoutons.add(panelAdresseIp);
		panelBoutons.add(Box.createRigidArea(new Dimension(100, 100)));
		panelBoutons.add(boutonConnexion);

		setLayout(new GridBagLayout());
		add(panelBoutons, new GridBagConstraints());
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(boutonConnexion)) {
      String adresseIP;
			if (localButton.isSelected()) {
				adresseIP = "localhost";
			}
			else {
				adresseIP = textFieldAdresseIP.getText();
			}
			ihm.connexionServeur(adresseIP);
		}
		else if (e.getSource().equals(localButton)) {
			textFieldAdresseIP.setEnabled(false);
			this.textFieldAdresseIP.setBackground(Color.gray);
		}
		else if (e.getSource().equals(ipButton)) {
			textFieldAdresseIP.setEnabled(true);
			this.textFieldAdresseIP.setBackground(Color.white);
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
}
