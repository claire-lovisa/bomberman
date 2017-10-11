package jeu.elements;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.AnyOf.anyOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.Before;
import jeu.elements.Joueur;
import jeu.elements.Position;

public class JoueurTest {

  public Joueur joueur1, joueur2;
  public Position pos1, pos2;
  public int scoreDepart, porteeBombe;

  @Before
  public void genese() {
    pos1 = new Position(10, 11);
    pos2 = new Position(20, 22);
    joueur1 = new Joueur(1, pos1);
    joueur2 = new Joueur(2, pos2);
    scoreDepart = 0;
    porteeBombe = 2;
  }

  @Test
  public void constructeur() {
    assertThat(joueur1.getId(),IsEqual.equalTo(1));
    assertThat(joueur1.getPosition(),IsEqual.equalTo(pos1));
    assertThat(joueur1.getScore(),IsEqual.equalTo(scoreDepart));
    assertThat(joueur1.getPorteeBombe(),IsEqual.equalTo(porteeBombe));
  }

  @Test
  public void egalites() {
    assertThat(joueur1.equals(joueur2),is(false));
    assertThat(joueur1.equals(joueur1),is(true));
  }

  @Test
  public void testToString() {
    assertThat(joueur1.toString(),IsEqual.equalTo("Le joueur a comme id : 1, il a 0 points et peut poser jusqu'à 3 bombes"));
  }

  @Test
  public void decrementerNbBombeMax() {
    joueur1.decrementerNbBombeMax();
    assertThat(joueur1.toString(),IsEqual.equalTo("Le joueur a comme id : 1, il a 0 points et peut poser jusqu'à 2 bombes"));
  }

  @Test
  public void deplacement() {
    joueur1.deplacer(20, 22);
    assertThat(joueur1.getPosition().getX(),IsEqual.equalTo(pos2.getX()));
    assertThat(joueur1.getPosition().getY(),IsEqual.equalTo(pos2.getY()));
  }

  @Test
  public void augmentationScore() {
    joueur1.augmenterScore(20);
    assertThat(joueur1.getScore(),IsEqual.equalTo(20));
  }

  @Test
  public void estEnJeu() {
    assertThat(joueur1.estEnJeu(),IsEqual.equalTo(true));
  }

  @Test
  public void toucherParBombe() {
    joueur1.toucherParBombe();
    assertThat(joueur1.estEnJeu(),IsEqual.equalTo(false));
  }

}
