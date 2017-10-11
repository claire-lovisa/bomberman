package jeu;

import java.io.*;
import jeu.elements.*;
import java.util.*;
import java.util.concurrent.*;
import java.text.*;
import java.util.Map.Entry;

public class Bomberman {

    private final int DUREE_TICK = 3 ;
    private Plateau plateau;
    private int nbJoueurs;
    private boolean partieFinie;
    private HashMap<String,Integer> listeScoresPartie;

    /** CONSTRUCTEUR **/

    public Bomberman(int nbJoueurs, int nbIA) throws NombreJoueurException, NombreMursException, IOException{
        this.nbJoueurs = nbJoueurs;
        this.partieFinie = false;
        int nbrAleatoire;
        HashMap<String,Integer> listeScoresPartie = new HashMap<String,Integer>();
        String terrain = "";

        //On détermine un terrain au hasard
		Random rand = new Random();
		nbrAleatoire = 2+rand.nextInt(3);
		switch (nbrAleatoire){
			case 2 :
                terrain = "../terrains/Terrain2.txt";
                break;
			case 3 :
                terrain = "../terrains/Terrain3.txt";
                break;
			case 4 :
                terrain = "../terrains/Terrain4.txt";
				break;
		}
        //Ligne a commenter pour exécuter les Tests
        chargerTerrain(terrain, nbJoueurs-nbIA, nbIA);
        //Ligne a décommenter pour exécuter les Tests
        //chargerTerrain("../terrains/Terrain1.txt", nbJoueurs);
    }

    /** METHODE DE CHARGEMENT DE TERRAIN **/

    //Méthode permettant de détecter les différents éléments situés sur le terrain
    public void chargerTerrain(String nomFichier, int nbJoueurs, int nbIA) throws NombreJoueurException, NombreMursException, IOException {
        int r;
        int x = 1;
        int y = 1;
        int longueur = 0;
        int hauteur = 0;
        Position pos;
        Joueur joueur;
        Mur mur;
        List<Joueur> joueurs;
        List<Mur> murs;

        //Chargement terrain
        joueurs = new ArrayList<Joueur>();
        murs = new ArrayList<Mur>();

        List<IA> ias = new ArrayList<IA>();

        try {
            //Chargement du fichier
            File f = new File(nomFichier);
            FileInputStream is = new FileInputStream(f);
            Reader reader = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(reader);

            //On détermine la longueur du plateau
            String line = buffer.readLine();
            longueur = line.indexOf("/");
            is.getChannel().position(0);
            buffer = new BufferedReader(new InputStreamReader(is));

            //Lecture du Terrain caractere par caractere
            while ((r = reader.read()) != -1) {
                char c = (char) r;

                switch (c) {
                    //Si on est en fin de ligne
                    case '/' :
                        y++;
                        x = 1;
                        hauteur++;
                        break ;
                    //Si c'est un joueur
                    case '1' :
                    case '2' :
                    case '3' :
                    case '4' :
                        if (nbJoueurs != 0) {
                            pos = new Position(x,y);
                            joueur = new Joueur(Character.getNumericValue(c),pos);
                            joueurs.add(joueur);
                            joueur.setNom("Joueur " + c);
                            nbJoueurs--;
                        }
                        else if ( nbIA != 0) {
                            pos = new Position(x,y);
                            joueur = new Joueur(Character.getNumericValue(c),pos);
                            joueurs.add(joueur);
                            joueur.setNom("IA " + c);
                            IA ia = new IA(DUREE_TICK, this, joueur);
                            ias.add(ia);
                            nbIA--;
                        }
                        x++;
                        break;
                    //Si c'est un mur destructible
                    case '*' :
                        pos = new Position(x,y);
                        mur = new Mur(pos,true);
                        murs.add(mur);
                        x++;
                        break;
                    //Si c'est un mur indestructible
                    case '#' :
                        pos = new Position(x,y);
                        mur = new Mur(pos,false);
                        murs.add(mur);
                        x++;
                        break;
                    //Case libre
                    case '.' :
                        x++;
                        break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //Création du plateau
        this.plateau = new Plateau(joueurs,murs,longueur,hauteur);
        exploserBombe(this.plateau, this);

        for(IA i : ias)
            i.lancerIA();
    }

    /** METHODE PERMETTANT DE GERER LES ACTIONS DES JOUEURS **/

    public void deplacerVersLeHaut(int idJoueur) throws IdJoueurInconnuException {
        Joueur joueur = this.plateau.getJoueur(idJoueur);
        choixDeplacement(joueur, joueur.getPosition().getX(), joueur.getPosition().getY()-1);
    }

    public void deplacerVersLeBas(int idJoueur) throws IdJoueurInconnuException {
        Joueur joueur = this.plateau.getJoueur(idJoueur);
        choixDeplacement(joueur, joueur.getPosition().getX(), joueur.getPosition().getY()+1);
    }

    public void deplacerVersLaGauche(int idJoueur) throws IdJoueurInconnuException {
        Joueur joueur = this.plateau.getJoueur(idJoueur);
        choixDeplacement(joueur, joueur.getPosition().getX()-1, joueur.getPosition().getY());
    }

    public void deplacerVersLaDroite(int idJoueur) throws IdJoueurInconnuException {
        Joueur joueur = this.plateau.getJoueur(idJoueur);
        choixDeplacement(joueur, joueur.getPosition().getX()+1, joueur.getPosition().getY());
    }

    public void poserBombe(int idJoueur) throws IdJoueurInconnuException {
        Joueur joueur = this.plateau.getJoueur(idJoueur);
        //ajout de la bombe si le joueur possède un nombre de bombes supérieur à 0
        if (joueur.getNbBombeMax() >= 1 && joueur.estEnJeu()) {
            //pose la bombe
            Bombe b = this.plateau.ajouterBombe(joueur.getPosition(), joueur);
            //décrémente le nb de bombe que le joueur peut poser
            joueur.decrementerNbBombeMax();
        }
    }

    /** VERIFICATION DE L'ETAT DE LA PARTIE **/

    public void verifiePartieFinie() {
        int nbJoueursEnJeu = 0;

        for (Joueur j : this.plateau.getJoueurs())
            if (j.estEnJeu())
                nbJoueursEnJeu++;

        if (nbJoueursEnJeu == 1) {
            setPartieFinie();
            listeScoresPartie();
            enregistrerScores();
			this.plateau.notifierChangement();
        }
    }

    /** ENREGISTREMENT DES SCORES **/

    public void enregistrerScores() {
        try{
            Writer output;
            output = new BufferedWriter(new FileWriter("scores.txt",true));
            for(Entry<String, Integer> entree : listeScoresPartie.entrySet())
                output.append(entree.getKey()+":"+entree.getValue()+"\n");
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
       }
       getAllScores();
   }

    public HashMap<String,Integer> getListeScoresPartie() {
        return listeScoresPartie;
    }

    public void listeScoresPartie() {
        HashMap<String, Integer> listeScores = new HashMap<String, Integer>();

        for (Joueur j : this.plateau.getJoueurs())
            listeScores.put(j.getNom(), j.getScore());

        HashMap<String, Integer> listeTriee = trierMap(listeScores);
        this.listeScoresPartie = listeTriee;
    }

    public boolean deplacer(Joueur joueur, Position p) {
        //Bonus bonusUtilise = null;
        boolean deplacementPossible = true;

        //Le joueur ne peut pas sortir du plateau, joueur lorsqu'il a été tué ou jouer lorsque la partie est finie
        if (p.getX() < 1 || p.getY() < 1 || p.getX() > this.plateau.getLongueur() || p.getY() > this.plateau.getHauteur() || !joueur.estEnJeu() || this.partieFinie)
            deplacementPossible = false;

        for(Mur m : this.plateau.getMurs())
            if (m.getPosition().getX() == p.getX() && m.getPosition().getY() == p.getY())
                deplacementPossible = false;

        for(Joueur j : this.plateau.getJoueurs())
            if (j.estEnJeu() && j.getPosition().getX() == p.getX() && j.getPosition().getY() == p.getY())
                deplacementPossible = false;

        for(Bombe b : this.plateau.getBombes())
            if (b.getPosition().getX() == p.getX() && b.getPosition().getY() == p.getY())
                deplacementPossible = false;

        return deplacementPossible;
    }

    /** METHODES PRIVEES **/

    private static HashMap<String, Integer> trierMap (HashMap<String, Integer> mapNonTriee) {
        List<Entry<String, Integer>> liste = new LinkedList<Entry<String, Integer>>(mapNonTriee.entrySet());

        Collections.sort(liste, new Comparator<Entry<String, Integer>>() {
          public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
              return o2.getValue().compareTo(o1.getValue());
          }
        });

        HashMap<String, Integer> mapTriee = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entree : liste)
            mapTriee.put(entree.getKey(), entree.getValue());

        return mapTriee;
    }

    private void choixDeplacement(Joueur joueur, int x, int y) {
        Position position = new Position(x, y);
        if(deplacer(joueur, position)) {
          joueur.deplacer(position.getX(), position.getY());
					this.plateau.notifierChangement();
				}
    }

    //Méthode permettant de gérer l'explosion des bombes
    private void exploserBombe(Plateau plateau, Bomberman bomberman) {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(new Runnable() {
        List<Bombe> bombesExplosees;
        DateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        Calendar calendar;
        String date;

        @Override
        public void run() {
          bombesExplosees = new ArrayList<Bombe>();
          calendar = Calendar.getInstance();
          date = sdf.format(calendar.getTime());
          //Liste des bombes devant exploser
          for (Bombe b : plateau.getBombes())
            if (date.compareTo(b.getDateBombeExplose()) >= 0 && !bomberman.estPartieFinie())
                bombesExplosees.add(b);

          //On explose les bombes et on vérifie si la partie est terminée
          for (Bombe b : bombesExplosees) {
             b.getJoueur().incrementerNbBombeMax();
             b.exploser();
             bomberman.verifiePartieFinie();
          }
        }
      }, 0, 50, TimeUnit.MILLISECONDS);
    }

    /** ACCESSEURS ET MODIFICATEURS **/

    public void setPlateau(Plateau p) {
        this.plateau = p;
    }

    public void setPartieFinie() {
        this.partieFinie = true;
    }

    public Plateau getPlateau () {
        return this.plateau;
    }

    public int getDUREE_TICK() {
        return this.DUREE_TICK;
    }

    public int getNbJoueurs() {
        return this.nbJoueurs;
    }

	public void ajouterObserveurPlateau(Observer obs) {
		this.plateau.addObserver(obs);
	}

    public boolean estPartieFinie() {
        return this.partieFinie;
    }

	public int getScore(int idJoueur) throws IdJoueurInconnuException {
		return this.plateau.getJoueur(idJoueur).getScore();
	}

    //Permet d'obtenir la liste des meilleurs scores de chaque joueur ayant joué au Bomberman
    public static HashMap<String,Integer> getAllScores() {
        HashMap<String, Integer> mapScores = new HashMap<String, Integer>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("scores.txt"));

            while ((line = reader.readLine()) != null) {
                String[] parties = line.split(":");
                if (!mapScores.containsKey(parties[0]) || mapScores.get(parties[0]) < Integer.parseInt(parties[1]))
                    mapScores.put(parties[0], Integer.parseInt(parties[1]));
            }
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return mapScores;
    }

    @Override
    public String toString() {
        return "Le nombre de Joueurs de cette partie de Bomberman : " + this.nbJoueurs;
    }
}
