package jeu.elements;

import java.io.Serializable;

public class Position implements Serializable {

  private int x,y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // Getteurs et setteurs
  public void setX(int x) {
    this.x = x;
  }

  public int getX() {
    return this.x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getY() {
    return this.y;
  }

  @Override
  public boolean equals(Object o) {
    return (this.x==((Position)o).getX()) && (this.y==((Position)o).getY());
  }

  @Override
  public String toString() {
    return "position x : "+this.x+" y : "+this.y;
  }
}
