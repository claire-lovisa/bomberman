package jeu;

public class LiaisonPlateauImpl implements LiaisonPlateau {
	private boolean aChange;
	private boolean finPartie;
	private Plateau plateau;
	
	public LiaisonPlateauImpl() {
		this.aChange = false;
		this.finPartie = false;
	}
	
	public void majPlateau(Plateau plateau) {
		this.plateau = plateau;
		this.aChange = true;
	}
	
	public boolean aChange() {
		return aChange;
	}
	
	public void setAChange(boolean aChange) {
		this.aChange = aChange;
	}
	
	public Plateau getPlateau() {
		return this.plateau;
	}
	
	public void setFinPartie() {
		this.finPartie = true;
	}
	
	public boolean estPartieFinie() {
		return this.finPartie;
	}
}
