package jeu;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

import client.ControleurClient;

public interface LiaisonBomberman extends Remote {
	public void creerPartie(int nbJoueurs, int nbIA) throws RemoteException, PartieEnCoursException;
	public int demandeId() throws RemoteException;
	public boolean connecter(String machine, int port, int id) throws RemoteException;
	public void jouer(int idJoueur, Instruction instr) throws RemoteException, IdJoueurInconnuException;
	public int getScore(int idJoueur) throws RemoteException, IdJoueurInconnuException;
	public HashMap<String,Integer> getScorePartie() throws RemoteException;
	public HashMap<String,Integer> getMeilleursScores() throws RemoteException;
}
