package jeu;

import static org.junit.Assert.*;
import jeu.elements.*;
import org.junit.*;
import static org.hamcrest.core.Is.is;
import org.hamcrest.core.IsEqual;
import java.util.*;
import jeu.*;
import jeu.elements.*;


public class TestPlateau {

    List<Joueur> joueurs;
    List<Mur> murs;
    Plateau plateau;
    Joueur j1,j2,j3,j4,j5;
    Bombe bombe;

    @Before
    public void TestControleurPlateau() throws NombreJoueurException, NombreMursException {
        this.j1 = new Joueur(1,new Position(1,1));
        this.j2 = new Joueur(2,new Position(9,9));
        this.j3 = new Joueur(3,new Position(1,9));
        this.j4 = new Joueur(4,new Position(9,1));
        this.joueurs = new ArrayList<Joueur>();
        this.joueurs.add(j1);
        this.joueurs.add(j2);
        this.joueurs.add(j3);
        this.joueurs.add(j4);

        Mur mur1 = new Mur(new Position(3,1),true);
        Mur mur2 = new Mur(new Position(3,2),true);
        Mur mur3 = new Mur(new Position(3,3),false);
        Mur mur4 = new Mur(new Position(3,5),true);
        Mur mur5 = new Mur(new Position(3,9),false);
        this.murs = new ArrayList<Mur>();
        this.murs.add(mur1);
        this.murs.add(mur2);
        this.murs.add(mur3);
        this.murs.add(mur4);
        this.murs.add(mur5);

        this.plateau = new Plateau(this.joueurs,this.murs,10,10);
    }

    @Test
    public void testAjouterBombe() throws NombreJoueurException, NombreMursException {
        assertThat(plateau.getBombes().size()==0, is(true));
        plateau.ajouterBombe(new Position(4,1),j1);
        this.bombe = plateau.getBombes().get(0);
        assertThat(plateau.getBombes().size()==1, is(true));
    }

    @Test
    public void testSupprimerBombe() throws NombreJoueurException, NombreMursException {
        plateau.ajouterBombe(new Position(4,1),j1);
        this.bombe = plateau.getBombes().get(0);
        assertThat(plateau.getBombes().size()==1, is(true));
        plateau.supprimer(this.bombe);
        assertThat(plateau.getBombes().size()==0, is(true));
    }


    @Test(expected=NombreJoueurException.class)
    public void testExceptionNbJoueurs() throws NombreJoueurException, NombreMursException {
        j1 = new Joueur(1,new Position(1,1));
        j2 = new Joueur(2,new Position(9,9));
        j3 = new Joueur(3,new Position(1,9));
        j4 = new Joueur(4,new Position(9,1));
        j5 = new Joueur(5,new Position(5,5));
        List<Joueur> joueurs2 = new ArrayList<Joueur>();
        joueurs2.add(j1);
        joueurs2.add(j2);
        joueurs2.add(j3);
        joueurs2.add(j4);
        joueurs2.add(j5);

        new Plateau(joueurs2, this.murs, 10, 10);
    }

    /*
    @Test(expected=NombreMursException.class)
    public void testExceptionNbMurs() throws NombreJoueurException, NombreMursException {
        Plateau plateau = new Plateau(2,2,this.joueurs,this.murs);
    }
    */
}
