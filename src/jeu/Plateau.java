package jeu;

import java.io.Serializable;
import java.util.Observable;

import jeu.elements.*;
import java.util.*;

public class Plateau extends Observable implements Serializable {

  private int hauteur, longueur;
  private List<Joueur> joueurs;
  private List<Mur> murs;
  private List<Bombe> bombes;

  public Plateau(List<Joueur> joueurs, List<Mur> murs, int longueur, int hauteur) throws NombreJoueurException, NombreMursException {
    this.hauteur = hauteur;
    this.longueur = longueur;
    if(joueurs.size() >= 2 && joueurs.size() <= 4) {
      this.joueurs = joueurs;
    }
    else {
      throw new NombreJoueurException("Le nombre de joueurs doit être compris entre 2 et 4");
    }
    //Comme pour Bomberman, tester si ya seulement 2 ou 3 joueurs
    //nbJ + nbB > taille plateau => pb
    if(murs.size() < this.hauteur*this.longueur-this.joueurs.size()) {
      this.murs = murs;
    }
    else {
      throw new NombreMursException("Le nombre de murs doit être inférieur à la taille du plateau");
    }
    this.bombes = new ArrayList<Bombe>();
  }

  public List<Mur> getMurs() {
    return this.murs;
  }

  public void setMurs(List<Mur> murs) {
    this.murs = murs;
  }

  public List<Joueur> getJoueurs() {
    return this.joueurs;
  }

	public Joueur getJoueur(int idJoueur) throws IdJoueurInconnuException {
		for (Joueur j : this.joueurs) {
			if (j.getId() == idJoueur) {
				return j;
			}
		}
		throw new IdJoueurInconnuException("Aucun joueur ne possède l'identifiant " + idJoueur);
  }

  public List<Bombe> getBombes() {
    return this.bombes;
  }

  public Bombe ajouterBombe(Position pos, Joueur joueur) {
    Bombe bombe = new Bombe(pos, this, joueur);
    this.bombes.add(bombe);
    return bombe;
  }

  public void supprimer(Bombe bombe) {
    this.bombes.remove(bombe);
  }

  public int getHauteur() {
	return this.hauteur;
  }

  public int getLongueur() {
	return this.longueur;
  }
	
	public void notifierChangement() {
		setChanged();
    notifyObservers();
	}
}
