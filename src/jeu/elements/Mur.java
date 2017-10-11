package jeu.elements;

import java.io.Serializable;

public class Mur implements Serializable {
  private boolean estDestructible;
  private Position position;

  public Mur(Position position, boolean estDestructible) {
    this.position = position;
    this.estDestructible = estDestructible;
  }

  public void setEstDestructible(boolean bool) {
    this.estDestructible = bool;
  }

  public boolean getEstDestructible() {
    return this.estDestructible;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Position getPosition() {
    return this.position;
  }

  @Override
  public String toString() {
      return String.format("Position (%d,%d) ; destructible ? %s", this.position.getX(), this.position.getY(), estDestructible);
  }
}
