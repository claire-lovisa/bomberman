package client.ihm;

import jeu.Plateau;
import jeu.Instruction;
import jeu.elements.*;
import client.ControleurClient;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class IHM extends JFrame {
	private ControleurClient client;
	
	private JPanel connexion;
	private JPanel menu;
	private JPanel choixPartie;
	private JPanel creerPartie;
	private Jeu jeu;
	private JPanel partie;
	private Score score;
	private ScorePartie scorePartie;
	private Container content;
	private boolean enJeu;

	public IHM() {
		super("Bomberman");
		setVisible(true);
		this.connexion = new Connexion(this);
		this.menu = new Menu(this);
		this.choixPartie = new ChoixPartie(this);
		this.creerPartie = new CreerPartie(this);
		this.jeu = new Jeu(this);
		this.partie = new Partie(this);
		this.score = new Score(this);
		this.scorePartie = new ScorePartie(this);
		this.content = getContentPane();
		
		this.enJeu = false;
		displayPanel(PageIHM.CONNEXION);
		setSize(800,600);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void displayPanel(PageIHM page) {
		getContentPane().removeAll(); 
		switch (page) {
			case CONNEXION : this.content.add(this.connexion);
						  break;
			case MENU : this.content.add(this.menu);
						  break;
			case CHOIX_PARTIE : this.content.add(this.choixPartie);
						  break;
			case CREER_PARTIE : this.content.add(this.creerPartie);
						  break;
			case JEU : this.content.add(this.jeu);
			           this.enJeu = true;
						  break;
			case PARTIE : this.content.add(this.partie);
						  break;
			case SCORE : this.content.add(this.score);
			        this.score.setScore(this.client.getMeilleursScores());
						  break;
			case SCORE_PARTIE : this.content.add(this.scorePartie);
						  break;
		}
		this.content.validate();
		this.repaint();
	}
	
	public void displayErreur(String message, PageIHM redirection) {
		getContentPane().removeAll(); 
		this.content.add(new Erreur(this, message, redirection));
		this.content.validate();
		this.repaint();
	}
	
	protected void connexionServeur(String adresseIP) {
		try {
			client = new ControleurClient(adresseIP);
			displayPanel(PageIHM.MENU);
		} catch (Exception e) {
			displayErreur("Impossible de se connecter à l'adresse " + adresseIP + ".", PageIHM.CONNEXION);
		}
	}

	public void afficher(Plateau plateau) {
		if (this.enJeu) {
			this.jeu.miseAJour(plateau);
		}
	}
	
	public void setScore(int score) {
		if (this.enJeu) {
			this.jeu.setScore(score);
		}
	}
	
	public void setScoreFinal(HashMap<String,Integer> scoreFinal) {
		displayPanel(PageIHM.SCORE_PARTIE);
		this.scorePartie.setScore(scoreFinal);
		this.enJeu = false;
	}
	
	protected void jouer(Instruction instr) {
		if (client != null) {
			client.effectuerInstruction(instr);
		}
	}
	
	protected void rejoindrePartie() {
		client.setIHM(this);
		client.demandeConnexion();
	}
	
	protected void creerPartie(String nom, int nbJoueurs, int nbIA) {
		client.setIHM(this);
		client.lancerPartie(nbJoueurs, nbIA);
	}
}
