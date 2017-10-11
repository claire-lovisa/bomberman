package serveur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main extends JFrame implements MouseListener {
	private ControleurServeur serveur;
	private JLabel labelEtatServeur;
	
	public Main() throws UnknownHostException {
		super("Bomberman");
		setVisible(true);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JLabel labelAdresseIP = new JLabel("Adresse : " + InetAddress.getLocalHost().getHostAddress());
		add(labelAdresseIP);
		add(Box.createRigidArea(new Dimension(30, 30)));
		this.labelEtatServeur = new JLabel("Création du serveur...");
		add(this.labelEtatServeur);
		add(Box.createRigidArea(new Dimension(30, 30)));
		JButton boutonArret = new JButton("Arrêter serveur");
		boutonArret.addMouseListener(this);
		add(boutonArret);
		
		setSize(300,200);
	}
	
	protected void setServeur(ControleurServeur serveur) {
		this.serveur = serveur;
	}
	
	protected void setEtat(String message) {
		this.labelEtatServeur.setText(message);
	}
	
	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {
		System.exit(0);
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
	
	public static void main(String[] args) throws UnknownHostException {
		Main main = new Main();
		main.setServeur(new ControleurServeur());
		main.setEtat("En attente de connexion...");
	}
}
