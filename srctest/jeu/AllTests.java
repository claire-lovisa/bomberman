package jeu;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import jeu.elements.SuiteElements;

@RunWith(Suite.class)
@SuiteClasses({
  SuiteElements.class,
  TestPlateau.class,
  TestBomberman.class
})

public class AllTests {}
