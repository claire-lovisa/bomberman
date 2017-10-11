package client.ihm;

import jeu.Plateau;
import jeu.Instruction;
import jeu.elements.*;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;

public class Jeu extends JPanel {
	
	private static final int ID_VIDE = 0;
	private static final int ID_MUR_CASSABLE = 1001;
	private static final int ID_MUR = 1002;
	private static final int ID_BOMBE = 1003;
	private static final int ID_EXPLOSION = 1004;
	
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
  private static final String HAUT = "haut";
  private static final String BAS = "bas";
  private static final String GAUCHE = "gauche";
	private static final String DROITE = "droite";
  private static final String BOMBE = "bombe";
	
	private IHM ihm;
	
	private BufferedImage imgMur;
	private BufferedImage imgMurCassable;
	private BufferedImage imgBombe;
	private BufferedImage imgJ1;
	private BufferedImage imgJ2;
	private BufferedImage imgJ3;
	private BufferedImage imgJ4;
	private BufferedImage imgMort;
	
	private int[][] elements;
	private int[][] joueurs;
	private int[][] morts;
	
	private PanelPlateau panelPlateau;
	private JLabel labelScore;
	
	public Jeu(IHM ihm) {
		this.ihm = ihm;
		try {
			this.imgMur = ImageIO.read(new File("../images/mur.png"));
			this.imgMurCassable = ImageIO.read(new File("../images/mur_cassable.png"));
			this.imgBombe = ImageIO.read(new File("../images/bombe.gif"));
			this.imgJ1 = ImageIO.read(new File("../images/J1.gif"));
			this.imgJ2 = ImageIO.read(new File("../images/J2.gif"));
			this.imgJ3 = ImageIO.read(new File("../images/J3.gif"));
			this.imgJ4 = ImageIO.read(new File("../images/J4.gif"));
			this.imgMort = ImageIO.read(new File("../images/mort.gif"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		setLayout(new BorderLayout());
		JPanel panelScore = new JPanel();
		panelScore.setLayout(new BorderLayout());
		panelScore.add(new JLabel("Score : "), BorderLayout.LINE_START);
		this.labelScore = new JLabel();
		panelScore.add(labelScore, BorderLayout.CENTER);
		add(panelScore, BorderLayout.PAGE_START);
		this.panelPlateau = new PanelPlateau();
		add(panelPlateau, BorderLayout.CENTER);
		
		getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), HAUT);
    getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), BAS);
		getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), GAUCHE);
    getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), DROITE);
		getInputMap(IFW).put(KeyStroke.getKeyStroke("SPACE"), BOMBE);
		
		getActionMap().put(HAUT, new KeyAction(Instruction.HAUT));
		getActionMap().put(BAS, new KeyAction(Instruction.BAS));
		getActionMap().put(GAUCHE, new KeyAction(Instruction.GAUCHE));
		getActionMap().put(DROITE, new KeyAction(Instruction.DROITE));
		getActionMap().put(BOMBE, new KeyAction(Instruction.POSER_BOMBE));
	}
	
	void miseAJour(Plateau plateau) {
		remplirElements(plateau);
		
		this.panelPlateau.repaint();
	}
	
	void setScore(int score) {
		this.labelScore.setText(""+score);
	}
	
	private void remplirElements(Plateau plateau) {
		// On initialise des tableaux si ils n'existaient pas où si la dimension a changé
		if (elements == null || elements.length != plateau.getLongueur() || elements[0].length != plateau.getHauteur()) {
			elements = new int[plateau.getLongueur()][plateau.getHauteur()];
			joueurs = new int[plateau.getLongueur()][plateau.getHauteur()];
			morts = new int[plateau.getLongueur()][plateau.getHauteur()];
			panelPlateau.initDimension();
		}
		// On vide les tableaux de tout élément
		for (int i = 0; i < elements.length; i++) {
			for (int j = 0; j < elements[0].length; j++) {
				elements[i][j] = ID_VIDE;
				joueurs[i][j] = ID_VIDE;
				morts[i][j] = ID_VIDE;
			}
		}
		
		Position pos;
		for (Mur m : plateau.getMurs()) {
			pos = m.getPosition();
			if (m.getEstDestructible()) {
				elements[pos.getX()-1][pos.getY()-1] = ID_MUR_CASSABLE;
			}
			else {
				elements[pos.getX()-1][pos.getY()-1] = ID_MUR;
			}
		}
		
		for (Bombe b : plateau.getBombes()) {
			pos = b.getPosition();
			elements[pos.getX()-1][pos.getY()-1] = ID_BOMBE;
		}
		
		for (Joueur j : plateau.getJoueurs()) {
			pos = j.getPosition();
			if (j.estEnJeu()) {
				joueurs[pos.getX()-1][pos.getY()-1] = j.getId();
			}
			else {
				morts[pos.getX()-1][pos.getY()-1] = j.getId();
			}
		}
	}
	
	private class KeyAction extends AbstractAction {
		Instruction instr;
		
		public KeyAction(Instruction instr) {
			this.instr = instr;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			ihm.jouer(instr);
		}
	}
	
	private class PanelPlateau extends JPanel implements ComponentListener  {
		private int size;
		private int imgSize;
		private int margeVerticale;
		private int margeHorizontale;
		private int hauteurTerrain;
		private int largeurTerrain;
		
		public PanelPlateau() {
			setBackground(Color.black);
			this.addComponentListener(this);
		}
		
		protected void initDimension() {
			int hauteurPanel = (int)panelPlateau.getSize().getHeight();
			int longueurPanel = (int)panelPlateau.getSize().getWidth();
			if (hauteurPanel < longueurPanel) {
				this.size = hauteurPanel;
			}
			else {
				this.size = longueurPanel;
			}
			
			if (elements != null) {
				this.imgSize = Math.min((int)hauteurPanel / elements[0].length, (int)longueurPanel / elements.length);//(int)(size / Math.max(elements.length, elements[0].length));
				this.hauteurTerrain = this.imgSize * elements[0].length;
				this.largeurTerrain = this.imgSize * elements.length;
				
				if (hauteurPanel < longueurPanel) {
					this.margeVerticale = 0;
					this.margeHorizontale = (longueurPanel - largeurTerrain) / 2;
				}
				else {
					this.margeVerticale = (hauteurPanel - hauteurTerrain) / 2;
					this.margeHorizontale = 0;
				}
			}
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if (elements != null) {
				if (this.imgSize == 0) {
					initDimension();
				}
				g.setColor(Color.green);
				g.fillRect(margeHorizontale, margeVerticale, this.largeurTerrain, this.hauteurTerrain);
				
				BufferedImage img = imgBombe;
				for (int i = 0; i < elements.length; i++) {
					for (int j = 0; j < elements[0].length; j++) {
						if (morts[i][j] != ID_VIDE) {
							g.drawImage(imgMort, margeHorizontale+i*imgSize, margeVerticale+j*imgSize, imgSize, imgSize, null);
						}
						if (elements[i][j] != ID_VIDE) {
							if (elements[i][j] == ID_MUR_CASSABLE) {
								img = imgMurCassable;
							}
							else if (elements[i][j] == ID_MUR) {
								img = imgMur;
							}
							else if (elements[i][j] == ID_BOMBE) {
								img = imgBombe;
							}
							g.drawImage(img, margeHorizontale+i*imgSize, margeVerticale+j*imgSize, imgSize, imgSize, null);
						}
						if (joueurs[i][j] != ID_VIDE) {
							g.drawImage(getImgJoueur(joueurs[i][j]), margeHorizontale+i*imgSize, margeVerticale+j*imgSize, imgSize, imgSize, null);
						}
					}
				}
			}
		}
		
		private BufferedImage getImgJoueur(int idJoueur) {
			switch(idJoueur) {
				case 1 : return imgJ1;
				case 2 : return imgJ2;
				case 3 : return imgJ3;
				case 4 : return imgJ4;
				default : return null;
			}
		}
		
		public void componentResized(ComponentEvent e) {
			initDimension();
			repaint();
		}

		public void componentHidden(ComponentEvent e) {}

		public void componentMoved(ComponentEvent e) {}

		public void componentShown(ComponentEvent e) {}
	}
}
