package jeu.elements;

import java.io.Serializable;
import java.util.*;
import java.text.*;

import jeu.Plateau;

public class Bombe implements Serializable {
  private int TICK_AVANT_EXPLOSION = 3;
  private Position position;
  private Plateau plateau;
  private Joueur joueur;
  private String dateBombeExplose;


  public Bombe(Position position, Plateau plateau, Joueur joueur) {
    this.position = position;
    this.plateau = plateau;
    this.joueur = joueur;

    //Date a laquelle la bombe doit exploser
    Calendar cal = new GregorianCalendar();
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
    dateFormat.setTimeZone(cal.getTimeZone());
    cal.add(Calendar.SECOND, TICK_AVANT_EXPLOSION);
    this.dateBombeExplose = dateFormat.format(cal.getTime());
  }


  public void exploser() {
    boolean gaucheStop, droiteStop, hautStop, basStop;
    int i;
    Position posG, posD, posH, posB;
    List<Position> positionsExplosion;
    List<Mur> mursExploses, listeTmpMurs;

    gaucheStop = false;
    droiteStop = false;
    hautStop = false;
    basStop = false;
    positionsExplosion = new ArrayList<Position>();
    mursExploses = new ArrayList<Mur>();

    // Suppression de la Bombe de la liste de Bombes
    this.plateau.supprimer(this);

    // On récupère les Positions atteintes par l’explosion sachant que
    // l’explosion est stoppée par un mur.
    positionsExplosion.add(this.getPosition());

    for(i=1; i<=this.joueur.getPorteeBombe(); i++) {
      posG = new Position(this.position.getX()-i, this.position.getY());
      posD = new Position(this.position.getX()+i, this.position.getY());
      posH = new Position(this.position.getX(), this.position.getY()-i);
      posB = new Position(this.position.getX(), this.position.getY()+i);

      for(Mur mur : this.plateau.getMurs()) {
        if(!gaucheStop) {
          gaucheStop = (posG.equals(mur.getPosition()));
          if(gaucheStop && mur.getEstDestructible()) {
            mursExploses.add(mur);
          }
        }
        if(!droiteStop) {
          droiteStop = (posD.equals(mur.getPosition()));
          if(droiteStop && mur.getEstDestructible()) {
            mursExploses.add(mur);
          }
        }
        if(!hautStop) {
          hautStop = (posH.equals(mur.getPosition()));
          if(hautStop && mur.getEstDestructible()) {
            mursExploses.add(mur);
          }
        }
        if(!basStop) {
          basStop = (posB.equals(mur.getPosition()));
          if(basStop && mur.getEstDestructible()) {
            mursExploses.add(mur);
          }
        }
      }

      if(!gaucheStop) {
        positionsExplosion.add(posG);
      }
      if(!droiteStop) {
        positionsExplosion.add(posD);
      }
      if(!hautStop) {
        positionsExplosion.add(posH);
      }
      if(!basStop) {
        positionsExplosion.add(posB);
      }
    }


    // Parcours des Positions de l’explosion pour éliminer les Joueurs touchés et
    // faire exploser les bombes touchées.
    for(Position pos : positionsExplosion) {
      for(Joueur joueur : this.plateau.getJoueurs()) {
        if(joueur.estEnJeu() && joueur.getPosition().equals(pos)) {
          joueur.toucherParBombe();
          if (!this.joueur.equals(joueur)) {
              this.joueur.setScore(this.joueur.getScore() + 100);
          }
        }
      }

      List<Bombe> bombesExplosees = new ArrayList<Bombe>();

      for(Bombe bombe : this.plateau.getBombes()) {
        if(bombe.getPosition().equals(pos)) {
          bombesExplosees.add(bombe);
        }
      }
      //Ajout des points des bombes adjacentes explosées
      this.joueur.setScore(this.joueur.getScore() + bombesExplosees.size()*20);
      for(Bombe b : bombesExplosees) {
          b.getJoueur().incrementerNbBombeMax();
          this.plateau.supprimer(b);
          b.exploser();
      }
    }

    // Suppression des murs
    listeTmpMurs = this.plateau.getMurs();
    //Ajout des points des murs explosés
    this.joueur.setScore(this.joueur.getScore() + mursExploses.size()*10);
    for(Mur mur : mursExploses) {
      listeTmpMurs.remove(mur);
    }
    this.plateau.setMurs(listeTmpMurs);
		
		// On notifie que le plateau a changé pour mettre à jour l'affichage
		this.plateau.notifierChangement();
  }

  // Getteurs et setteurs
  public int getTICK_AVANT_EXPLOSION() {
    return this.TICK_AVANT_EXPLOSION;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Position getPosition() {
    return this.position;
  }

  public void setPlateau(Plateau plateau) {
    this.plateau = plateau;
  }

  public Plateau getPlateau() {
    return this.plateau;
  }

  public String getDateBombeExplose() {
    return this.dateBombeExplose;
  }

  public void setDateBombeExplose(String date) {
    this.dateBombeExplose = date;
  }

  public Joueur getJoueur() {
      return this.joueur;
  }
}
