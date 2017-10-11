package jeu;

import java.io.*;
import java.util.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.Observer;
import java.util.Observable;

import jeu.elements.*;

public class LiaisonBombermanImpl implements LiaisonBomberman, Observer {
	private Bomberman bomberman;
	private int nbJoueursConnectes;
	private ArrayList<LiaisonPlateau> clients;

	public LiaisonBombermanImpl() {
		clients = new ArrayList<LiaisonPlateau>();
	}

	public void creerPartie(int nbJoueurs, int nbIA) throws PartieEnCoursException {
		try {
			if (this.bomberman == null || this.bomberman.estPartieFinie()) {
				this.bomberman = new Bomberman(nbJoueurs, nbIA);
				this.bomberman.ajouterObserveurPlateau(this);
			}
			else {
				throw new PartieEnCoursException("Une partie est déjà en cours.");
			}
		} catch (NombreJoueurException e) {
			e.printStackTrace();
		} catch (NombreMursException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int demandeId() {
		return this.nbJoueursConnectes+1;
	}

	public boolean connecter(String machine, int port, int id) {
		if (this.bomberman != null) {
			try {
				Registry registry = LocateRegistry.getRegistry(machine, port);
				LiaisonPlateau liaisonPlateau = (LiaisonPlateau)registry.lookup("LiaisonPlateau"+id);
				liaisonPlateau.majPlateau(bomberman.getPlateau());
				clients.add(liaisonPlateau);
				nbJoueursConnectes++;
				return true;
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch(NotBoundException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void jouer(int idJoueur, Instruction instr) throws IdJoueurInconnuException {
		switch (instr){
			case POSER_BOMBE : bomberman.poserBombe(idJoueur);
				break;
			case HAUT : bomberman.deplacerVersLeHaut(idJoueur);
				break;
			case BAS : bomberman.deplacerVersLeBas(idJoueur);
				break;
			case GAUCHE : bomberman.deplacerVersLaGauche(idJoueur);
				break;
			case DROITE : bomberman.deplacerVersLaDroite(idJoueur);
				break;
		}
		mettreAJourAffichage();
	}

	public int getScore(int idJoueur) throws IdJoueurInconnuException {
			return this.bomberman.getScore(idJoueur);
	}

	public HashMap<String,Integer> getScorePartie() {
		return this.bomberman.getListeScoresPartie();
	}

	public HashMap<String,Integer> getMeilleursScores() {
		return Bomberman.getAllScores();
	}

	public void update(Observable obs, Object obj) {
		mettreAJourAffichage();
	}

	private void mettreAJourAffichage() {
		for (LiaisonPlateau client : clients) {
			try {
				client.majPlateau(bomberman.getPlateau());
				if (bomberman.estPartieFinie()) {
					client.setFinPartie();
					nbJoueursConnectes = 0;
				}
			} catch (RemoteException e) {
				System.err.println("Un client s'est déconnecté.");
				clients.remove(client);
			}
		}
	}
}
