package jeu;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;


public interface LiaisonPlateau extends Remote {
	public void majPlateau(Plateau plateau) throws RemoteException;
	public boolean aChange() throws RemoteException;
	public void setAChange(boolean aChange) throws RemoteException;
	public Plateau getPlateau() throws RemoteException;
	public void setFinPartie() throws RemoteException;
	public boolean estPartieFinie() throws RemoteException;
}
