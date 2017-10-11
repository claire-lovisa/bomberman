package jeu.elements;

import static org.junit.Assert.*;
import jeu.elements.*;
import org.junit.*;
import static org.hamcrest.core.Is.is;
import org.hamcrest.core.IsEqual;


public class TestMur {
    private Position p1, p2;
    private Mur mur1, mur2;

    @Before
    public void TestControleurMur() {
        p1 = new Position(3,7);
        p2 = new Position(9,4);
        mur1 = new Mur(p1,true);
        mur2 = new Mur(p2,false);
    }

    @Test
    public void testControleurMur() {
        assertThat(mur1.getEstDestructible(), is(true));
        assertThat(mur2.getEstDestructible(), is(false));
        assertThat(mur1.getPosition().getX()==3, is(true));
        assertThat(mur1.getPosition().getY()==7, is(true));
    }
}
