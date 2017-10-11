package jeu.elements;

import java.io.Serializable;

public class Joueur implements Serializable {
  private static final long serialVersionUID = 1554979645589057745L;
  private int id;
  private int score=0;
  private int porteeBombe=2;
  private int nbBombeMax=3;
  private String nom;
  private boolean enJeu=true;
  private Position pos;

  public Joueur(int id, Position pos) {
    this.id=id;
    this.pos=pos;
    this.nom = "";
  }

  public int getNbBombeMax() {
    return this.nbBombeMax;
  }

  public int getId() {
    return this.id;
  }

  public int getScore() {
    return this.score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getPorteeBombe() {
    return this.porteeBombe;
  }

  public void setPorteeBombe(int porteeBombe) {
    this.porteeBombe = porteeBombe;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getNom() {
    return this.nom;
  }

  public void deplacer(int x, int y) {
    this.pos = new Position(x,y);
  }

  public void augmenterScore(int addScore) {
    this.score = this.score+addScore;
  }

  public void toucherParBombe() {
    this.enJeu=false;
  }

  public Position getPosition() {
    return this.pos;
  }

  public boolean estEnJeu() {
    return this.enJeu;
  }

  public void decrementerNbBombeMax() {
      this.nbBombeMax--;
  }

  public void incrementerNbBombeMax() {
      this.nbBombeMax++;
  }

  @Override
  public String toString() {
      return String.format("Le joueur a comme id : %d, il a %d points et peut poser jusqu'à %d bombes", id, score, nbBombeMax);
  }
}
