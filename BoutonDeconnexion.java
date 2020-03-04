/* Collections */
import java.util.LinkedList;
import java.util.List;

/* Interface graphique */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/* Entrées/sorties */
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/** Classe implémentant le bouton de déconnexion au tchat */
class BoutonDeconnexion extends JButton implements MouseListener {

	private JTextField champNom;
	private JTextField champIP;
	private JTextField champPort;
	private JTextArea champConnectes;
	private JTextArea champDiscussion;
	private List<Client> listeClients;
	private Client client;
	private BoutonEnvoyer envoyer;
	private Fenetre fenetre;
	
	/** Constructeur d'un bouton de déconnexion 
	  * @param nom le champ nom du tchat
	  * @param ip le champ ip du tchat
	  * @param port le champ port du tchat
	  * @param connectes le champ connectes du tchat
	  * @param discussion le champ discussion du tchat
	  * @param b1le bouton envoyer du tchat
	  * @param f la fenêtre du tchat
	  */
	public BoutonDeconnexion(JTextField nom, JTextField ip, JTextField port, JTextArea connectes, JTextArea discussion, BoutonEnvoyer b, Fenetre f){
		super("Déconnexion");
		champNom = nom;
		champIP = ip;
		champPort = port;
		champConnectes = connectes;
		champDiscussion = discussion;
		envoyer = b;
		fenetre = f;
		/* Grâce à cette instruction, notre objet va s'écouter. Dès qu'un événement de la souris sera intercepté, il en sera averti */
		this.addMouseListener(this);
	}
	
	/** Setter sur client
	  * @param c le client à affecter
	  */
	public void setClient(Client c) {
		client = c;
	}

	/** Méthode appelée lors du clic de souris.
	  * Cette méthode charge la liste des clients à partir du fichier ListeClients.txt, déconnecte le client au serveur, le retire de la liste et sauvegarde la liste.
	  * @param event l'événement attendu
	  */
	public void mouseClicked(MouseEvent event) {
	
		/* Chargement (désérialisation) de la liste des clients */
		ObjectInputStream fichierClientsInput = null; // ObjectInputStream : fichier comportant des objets	
		try {
			fichierClientsInput = new ObjectInputStream(new FileInputStream("ListeClients.txt")); // Ouverture du fichier de clients en mode lecture
			Client c;
			listeClients = new LinkedList<Client>(); // La liste est clients est réinitialisée pour pouvoir être chargée à partir du fichier
			while ((c = (Client)(fichierClientsInput.readObject())) != null) // Ajout de chaque client du fichier à la liste des clients
				listeClients.add(c);
		
		} catch (EOFException e) { 
			// La fn du fichier est atteinte, plus rien à faire
		} catch (ClassNotFoundException e) { // Cette exception est levée si l'objet désérialisé n'a pas de classe connue
			e.printStackTrace();
		} catch (FileNotFoundException e) { // Cette exception est levée si l'objet ObjectInputStream ne trouve aucun fichier
			e.printStackTrace();
		} catch (IOException e) { // Celle-ci se produit lors d'une erreur d'écriture ou de lecture
			e.printStackTrace();
		} finally {
			// On ferme nos flux de données dans un bloc finally pour s'assurer que ces instructions seront exécutées dans tous les cas même si une exception est levée !
			try {
				if (fichierClientsInput != null)
				fichierClientsInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/* Suppression d'un client à une liste de clients */
		if (client.estConnecte()) { // Si le client est bien connecté
			List nouvelleListeClients = new LinkedList();
			for (Client c : listeClients) // On ajoute tous les clients à garder, c'est à dire tous les clients dont le nom est différent du client à supprimer
				if (!(c.getNom().equals(champNom.getText())))
					nouvelleListeClients.add(c);
			listeClients = nouvelleListeClients;
			client.deconnecter();	
			fenetre.vireLeClient();
		}
	
		
		/* Sauvegarde (sérialisation) de tous les clients dans le fichier client */
		ObjectOutputStream fichierClientsOutput = null;
			
		try {
			fichierClientsOutput = new ObjectOutputStream(new FileOutputStream("ListeClients.txt")); // Ouverture du fichier de clients en mode écriture
			for (Client c : listeClients) // Écriture dans le fichier de tous les clients
				fichierClientsOutput.writeObject(c);		
		} catch (FileNotFoundException e) { // Cette exception est levée si l'objet FileInputStream ne trouve aucun fichier
			e.printStackTrace();
		} catch (IOException e) { // Celle-ci se produit lors d'une erreur d'écriture ou de lecture
			e.printStackTrace();
		} finally {
			// On ferme nos flux de données dans un bloc finally pour s'assurer que ces instructions seront exécutées dans tous les cas même si une exception est levée !
			try {
				if (fichierClientsOutput != null)
				fichierClientsOutput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/* Le bouton de déconnexion devient un bouton de connexion */
		fenetre.changerConnexion();
	}
	// Méthode appelée lors du survol de la souris
	public void mouseEntered(MouseEvent event) { }
	// Méthode appelée lorsque la souris sort de la zone du bouton
	public void mouseExited(MouseEvent event) { }
	// Méthode appelée lorsque l'on presse le bouton gauche de la souris
	public void mousePressed(MouseEvent event) { }
	// Méthode appelée lorsque l'on relâche le clic de souris
	public void mouseReleased(MouseEvent event) { }
}
