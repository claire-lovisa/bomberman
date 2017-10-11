package jeu;

public class IdJoueurInconnuException extends java.lang.Exception {
  public IdJoueurInconnuException() {
    super();
  }

  public IdJoueurInconnuException(String msg) {
    super(msg);
  }

  public IdJoueurInconnuException(String msg, Throwable e) {
    super(msg, e);
  }
}
