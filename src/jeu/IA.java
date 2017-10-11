package jeu;

import jeu.elements.*;
import java.util.*;

public class IA {

	private int DUREE_TICK;
	private Bomberman bomberman;
	private Joueur joueur;

	public IA(int DUREE_TICK, Bomberman bomberman, Joueur joueur){
		this.DUREE_TICK = DUREE_TICK;
		this.bomberman = bomberman;
		this.joueur = joueur;
	}

	public void actionAjouer(){
		int nbrAleatoire;
		Random rand = new Random();
		nbrAleatoire = rand.nextInt(40);
		try {
			if(nbrAleatoire == 0) {
				bomberman.poserBombe(this.joueur.getId());
			}
			if(nbrAleatoire > 0 && nbrAleatoire <= 10)
				bomberman.deplacerVersLeHaut(this.joueur.getId());
			if(nbrAleatoire > 10 && nbrAleatoire <= 20)
				bomberman.deplacerVersLeBas(this.joueur.getId());
			if(nbrAleatoire > 20 && nbrAleatoire <= 30)
				bomberman.deplacerVersLaGauche(this.joueur.getId());
			if(nbrAleatoire > 30 && nbrAleatoire <= 40)
				bomberman.deplacerVersLaDroite(this.joueur.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

  public Joueur getJoueur() {
	  return this.joueur;
  }

  public void toucherParBombe(Thread t) {
	  t.interrupt();
  }

  public void lancerIA() {
	  JeuIA jeu = new JeuIA(this);
	  jeu.start();
  }

	class JeuIA extends Thread {
		private IA ia;

		public JeuIA(IA ia) {
			this.ia = ia;
		}

	    @Override
        public void interrupt() {
            super.interrupt();
      	}

	  	@Override
	  	public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				if (!this.ia.getJoueur().estEnJeu())
					this.ia.toucherParBombe(this);
			  	actionAjouer();
			  	try {
	            	Thread.sleep(1000);
	        	} catch (Exception e) {
	            	Thread.currentThread().interrupt();
	        	}
	  		}
		}
	}
}
