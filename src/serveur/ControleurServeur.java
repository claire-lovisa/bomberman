package serveur;

import java.util.*;
import java.io.*;
import java.rmi.server.UnicastRemoteObject;
import  java.rmi.registry.LocateRegistry;
import  java.rmi.registry.Registry;

import jeu.*;
import jeu.elements.*;

public class ControleurServeur {
	private LiaisonBomberman skeleton;

	public ControleurServeur() {
		int port = 1099;
		try {
			Registry registry = LocateRegistry.createRegistry(port);
			this.skeleton = (LiaisonBomberman)UnicastRemoteObject.exportObject(new LiaisonBombermanImpl(), 0);
			if (!Arrays.asList(registry.list()).contains("LiaisonBomberman")) {
				registry.bind("LiaisonBomberman", skeleton);
				System.out.println("Binding ...");
			}
			else {
				registry.rebind("LiaisonBomberman", skeleton);
				System.out.println("Rebinding ...");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
