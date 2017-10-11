package jeu;

import static org.junit.Assert.*;
import jeu.elements.*;
import org.junit.*;
import static org.hamcrest.core.Is.is;
import org.hamcrest.core.IsEqual;
import java.util.*;
import java.io.*;
import jeu.*;
import jeu.elements.*;


public class TestBomberman {

  Bomberman bomberman;
  int nbJoueurs;

  @Before
  public void TestControleurBomberman() throws NombreJoueurException, NombreMursException, IOException {
    this.nbJoueurs = 4;
    this.bomberman = new Bomberman(this.nbJoueurs);
  }

  @Test
  public void testChargerTerrain() throws NombreJoueurException, NombreMursException, IOException, IdJoueurInconnuException {
    Bomberman bomberman = new Bomberman(2);
    Plateau plateau = bomberman.getPlateau();
    assertThat(plateau.getJoueurs().size(), is(2));
    assertThat(plateau.getMurs().size(), is(55));
    assertThat(plateau.getBombes().size(), is(0));
    assertThat(plateau.getJoueur(1).getPosition().getX(), is(1));
    assertThat(plateau.getJoueur(1).getPosition().getY(), is(1));
    assertThat(plateau.getJoueur(2).getPosition().getX(), is(9));
    assertThat(plateau.getJoueur(2).getPosition().getY(), is(1));
  }

  @Test(expected=NombreJoueurException.class)
  // Le terrain est vide, il ne contient pas de joueurs donc NombreJoueurException
  public void testChargerTerrainFaux() throws NombreJoueurException, NombreMursException, IOException, IdJoueurInconnuException  {
    bomberman.chargerTerrain("neMarchePas.txt", 3);
  }

  @Test
  public void testDeplacerVersLeHaut() throws IdJoueurInconnuException {
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getX(), is(1));
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getY(), is(10));
    bomberman.deplacerVersLeHaut(3);
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getX(), is(1));
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getY(), is(9));

    // Test si le joueur reste a sa place en cas de mur obstacle en haut
    bomberman.deplacerVersLeHaut(3);
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getX(), is(1));
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getY(), is(9));

    // Test si le joueur reste a sa place en cas de sortie de terrain en haut
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getX(), is(1));
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getY(), is(1));
    bomberman.deplacerVersLeHaut(1);
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getX(), is(1));
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getY(), is(1));
  }

  @Test
  public void testDeplacerVersLeBas() throws IdJoueurInconnuException {
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getX(), is(1));
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getY(), is(10));
    bomberman.deplacerVersLeHaut(3);

    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getX(), is(1));
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getY(), is(9));
    bomberman.deplacerVersLeBas(3);
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getX(), is(1));
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getY(), is(10));

    // Test si le joueur reste a sa place en cas de mur obstacle
    assertThat(bomberman.getPlateau().getJoueur(2).getPosition().getX(), is(9));
    assertThat(bomberman.getPlateau().getJoueur(2).getPosition().getY(), is(1));
    bomberman.deplacerVersLeBas(2);
    assertThat(bomberman.getPlateau().getJoueur(2).getPosition().getX(), is(9));
    assertThat(bomberman.getPlateau().getJoueur(2).getPosition().getY(), is(1));

    // Test si le joueur reste a sa place en cas de sortie de terrain en bas
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getX(), is(10));
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getY(), is(10));
    bomberman.deplacerVersLeBas(4);
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getX(), is(10));
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getY(), is(10));
  }

  @Test
  public void testDeplacerVersLaGauche() throws IdJoueurInconnuException {
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getX(), is(10));
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getY(), is(10));
    bomberman.deplacerVersLaGauche(4);
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getX(), is(9));
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getY(), is(10));

    // Test si le joueur reste a sa place en cas de mur obstacle
    bomberman.deplacerVersLaGauche(4);
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getX(), is(8));
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getY(), is(10));
    bomberman.deplacerVersLaGauche(4);
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getX(), is(7));
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getY(), is(10));
    bomberman.deplacerVersLaGauche(4);
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getX(), is(7));
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getY(), is(10));

    // Test si le joueur reste a sa place en cas de sortie de terrain a gauche
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getX(), is(1));
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getY(), is(10));
    bomberman.deplacerVersLaGauche(3);
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getX(), is(1));
    assertThat(bomberman.getPlateau().getJoueur(3).getPosition().getY(), is(10));
  }

  @Test
  public void testDeplacerVersLaDroite() throws IdJoueurInconnuException {
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getX(), is(1));
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getY(), is(1));
    bomberman.deplacerVersLaDroite(1);
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getX(), is(2));
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getY(), is(1));

    // Test si le joueur reste a sa place en cas de mur obstacle
    bomberman.deplacerVersLaDroite(1);
    bomberman.deplacerVersLaDroite(1);
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getX(), is(3));
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getY(), is(1));
    bomberman.deplacerVersLaDroite(1);
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getX(), is(3));
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getY(), is(1));

    // Test si le joueur reste a sa place en cas de sortie de terrain a droite
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getX(), is(10));
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getY(), is(10));
    bomberman.deplacerVersLaDroite(4);
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getX(), is(10));
    assertThat(bomberman.getPlateau().getJoueur(4).getPosition().getY(), is(10));
  }

  @Test
  public void testPoserBombe() throws IdJoueurInconnuException {
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getX(), is(1));
    assertThat(bomberman.getPlateau().getJoueur(1).getPosition().getY(), is(1));
    assertThat(bomberman.getPlateau().getBombes().size(), is(0));
    bomberman.poserBombe(1);
    assertThat(bomberman.getPlateau().getBombes().size(), is(1));
    assertThat(bomberman.getPlateau().getBombes().get(0).getPosition().getX(), is(1));
    assertThat(bomberman.getPlateau().getBombes().get(0).getPosition().getX(), is(1));
    bomberman.poserBombe(2);
    assertThat(bomberman.getPlateau().getBombes().size(), is(2));
    assertThat(bomberman.getPlateau().getBombes().get(1).getPosition().getX(), is(9));
    assertThat(bomberman.getPlateau().getBombes().get(1).getPosition().getX(), is(9));
  }
}
