package jeu.elements;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.AnyOf.anyOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.Before;
import jeu.elements.Joueur;
import jeu.elements.Position;
import jeu.Plateau;
import jeu.elements.Bombe;
import jeu.NombreJoueurException;
import jeu.NombreMursException;

public class BombeTest {

    public Joueur joueurNT, joueurMort;
    public Position pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8, posBombe;
    public Mur mur1, mur2, murND, murNT1 ,murNT2 ;
    public List<Joueur> joueurs;
    private List<Mur> murs;
    private Plateau plateau;

    @Before
    public void genese() throws NombreJoueurException, NombreMursException {

      pos1 = new Position(2, 2);
      pos2 = new Position(2, 3);
      pos3 = new Position(3, 1);
      pos4 = new Position(3, 2);
      pos5 = new Position(6, 3);

      mur1 = new Mur(pos2,true);
      mur2 = new Mur(pos3,true);
      murND = new Mur(pos4,false);
      murNT1 = new Mur(pos1,true);
      murNT2 = new Mur(pos5,true);

      pos6 = new Position(4, 4);
      pos7 = new Position(3, 4);
      pos8 = new Position(5, 3);
      posBombe = new Position(3, 3);

      joueurNT = new Joueur(1, pos6);
      joueurMort = new Joueur(2, pos7);

      murs = new ArrayList<Mur>();
      murs.add(mur1);
      murs.add(mur2);
      murs.add(murNT1);
      murs.add(murND);
      murs.add(murNT2);

      joueurs = new ArrayList<Joueur>();
      joueurs.add(joueurNT);
      joueurs.add(joueurMort);

      plateau = new Plateau(joueurs,murs,10,10);
    }


    @Test
    public void exploserTest1() {
      Bombe bombe = new Bombe(posBombe,plateau,joueurNT);

      bombe.exploser();

      List<Mur> mursNew = plateau.getMurs();

      assertThat(mursNew.contains(murNT1),is(true));
      assertThat(mursNew.contains(mur2),is(true));
      assertThat(mursNew.contains(murNT2),is(true));
      assertThat(mursNew.contains(murND),is(true));
      assertThat(mursNew.contains(mur1),is(false));

      assertThat(joueurMort.estEnJeu(),is(false));

    }

    @Test
    public void exploserTest2() {
      Bombe bombe = new Bombe(posBombe,plateau,joueurNT);
      Bombe bombeAExploser = new Bombe(pos8,plateau,joueurMort);

      bombe.exploser();

      List<Bombe> bombes = plateau.getBombes();
      assertThat(bombes.contains(bombeAExploser),is(false));
    }
}
