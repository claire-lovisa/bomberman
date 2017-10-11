package client;

import java.rmi.registry.LocateRegistry ;
import java.rmi.registry.Registry ;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.NotBoundException;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.util.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import jeu.*;
import client.ihm.*;

public class ControleurClient {
	private int id;
	private LiaisonBomberman stub;
	private LiaisonPlateau skeleton;
	private IHM ihm;
	private int port;
	private String machine;
	private Registry registryClient;

	public ControleurClient(String adresseIP) throws RemoteException, NotBoundException {
		try {
			this.machine = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.port = 1099;
		Registry registry = LocateRegistry.getRegistry(adresseIP, port);
		this.stub = (LiaisonBomberman)registry.lookup("LiaisonBomberman");
	}

	public void lancerPartie(int nbJoueurs, int nbIA) {
		try {
			this.stub.creerPartie(nbJoueurs, nbIA);
			this.id = 1;
			creerSkeleteon();
			ihm.displayPanel(PageIHM.JEU);
		} catch(RemoteException e) {
			e.printStackTrace();
		} catch(PartieEnCoursException ex) {
			ihm.displayErreur("Une partie est déjà en cours.", PageIHM.PARTIE);
		}
	}

	public void demandeConnexion() {
		try {
			this.id = this.stub.demandeId();
			creerSkeleteon();
		} catch(RemoteException e) {
			e.printStackTrace();
		}
	}

	private void creerSkeleteon() {
		try {
			int port = 2000 + this.id;
			this.skeleton = (LiaisonPlateau)UnicastRemoteObject.exportObject(new LiaisonPlateauImpl(), 0);
			registryClient = LocateRegistry.createRegistry(port);
			if (!Arrays.asList(registryClient.list()).contains("LiaisonPlateau"+this.id)) {
				registryClient.bind("LiaisonPlateau"+this.id, this.skeleton);
			}
			else {
				registryClient.rebind("LiaisonPlateau"+this.id, this.skeleton);
			}
			if (this.stub.connecter(this.machine, port, this.id)) {
				Ecoute thread = new Ecoute();
				thread.start();
				ihm.displayPanel(PageIHM.JEU);
			}
			else {
				UnicastRemoteObject.unexportObject(registryClient, true);
				this.id = 0;
				this.skeleton = null;
				ihm.displayErreur("Aucune partie n'est créée.", PageIHM.PARTIE);
			}
		} catch(AlreadyBoundException e) {
			e.printStackTrace();
		} catch(RemoteException e) {
			e.printStackTrace();
		}
	}

	public void setIHM(IHM ihm) {
		this.ihm = ihm;
	}

	public void effectuerInstruction(Instruction instruction) {
		try {
			this.stub.jouer(this.id, instruction);
		} catch(RemoteException e) {
			try {
				UnicastRemoteObject.unexportObject(registryClient, true);
			} catch(NoSuchObjectException exc) {
				exc.printStackTrace();
			}
			this.ihm.displayErreur("La connexion avec le serveur a été coupée.", PageIHM.CONNEXION);
		} catch(IdJoueurInconnuException ex) {
			ex.printStackTrace();
		}
	}

	public void majJeu(Plateau plateau) {
		this.ihm.afficher(plateau);
		try {
			this.ihm.setScore(this.stub.getScore(this.id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String,Integer> getScoresPartie() {
		try {
			return stub.getScorePartie();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	public HashMap<String,Integer> getMeilleursScores() {
		try {
			return stub.getMeilleursScores();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}


	class Ecoute extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					if (skeleton.aChange()) {
						majJeu(skeleton.getPlateau());
						skeleton.setAChange(false);
						if (skeleton.estPartieFinie()) {
							UnicastRemoteObject.unexportObject(registryClient, true);
							ihm.setScoreFinal(stub.getScorePartie());
						}
					}
					Thread.sleep(50);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (RemoteException e) {}
		}
	}
}
