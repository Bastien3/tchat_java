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

/** Classe implémentant le bouton de connexion au tchat */
class BoutonConnexion extends JButton implements MouseListener {

	private JTextField champNom;
	private JTextField champIP;
	private JTextField champPort;
	private JTextArea champConnectes;
	private JTextArea champDiscussion;
	private List<Client> listeClients;
	private BoutonEnvoyer envoyer;
	private BoutonDeconnexion deconnexion;
	private Fenetre fenetre;
	private boolean cliquable = false;
	
	/** Constructeur d'un bouton de connexion 
	  * @param nom le champ nom du tchat
	  * @param ip le champ ip du tchat
	  * @param port le champ port du tchat
	  * @param connectes le champ connectes du tchat
	  * @param discussion le champ discussion du tchat
	  * @param b1 le bouton envoyer du tchat
	  * @param b2 le bouton deconnexion du tchat
	  * @param f la fenêtre du tchat
	  */
	public BoutonConnexion(JTextField nom, JTextField ip, JTextField port, JTextArea connectes, JTextArea discussion, BoutonEnvoyer b1, BoutonDeconnexion b2, Fenetre f) {
	
		super("Connexion");
		champNom = nom;
		champIP = ip;
		champPort = port;
		champConnectes = connectes;
		champDiscussion = discussion;
		envoyer = b1;
		deconnexion = b2;
		fenetre = f;
		listeClients = new LinkedList<Client>();
		/* Grâce à cette instruction, notre objet va s'écouter. Dès qu'un événement de la souris sera intercepté, il en sera averti */
		this.addMouseListener(this);
	}

	/** Setter sur le booléen cliquable.
	  * Permet de déterminer si le bouton est cliquable ou pas.
	  * Si le bouton n'est pas cliquable, il est grisé.
	  */
	public void setCliquable(boolean b) {
		cliquable = b;
	}
	
	/** Méthode appelée lors du clic de souris.
	  * Cette méthode charge la liste des clients à partir du fichier ListeClients.txt, connecte le client au serveur, l'ajoute à la liste et sauvegarde la liste.
	  * @param event l'événement attendu
	  */
	public void mouseClicked(MouseEvent event) {
	
		/* Si le bouton n'est pas cliquable, cliquer dessus n'enclenche aucune action */
		if (!cliquable)
			return;
	
		/* Chargement (désérialisation) de la liste des clients */
		ObjectInputStream fichierClientsInput = null; // ObjectInputStream : fichier comportant des objets	
		try {
			fichierClientsInput = new ObjectInputStream(new FileInputStream("ListeClients.txt")); // Ouverture du fichier de clients en mode lecture
			Client c;
			listeClients = new LinkedList<Client>(); // La liste est clients est réinitialisée pour pouvoir être chargée à partir du fichier
			while ((c = (Client)(fichierClientsInput.readObject())) != null) // Ajout de chaque client du fichier à la liste des clients
				listeClients.add(c);
		} catch (FileNotFoundException e) { // Cette exception est levée si l'objet ObjectInputStream ne trouve aucun fichier
			e.printStackTrace();
		} catch (EOFException e) {
			// La fn du fichier est atteinte, plus rien à faire
		} catch (IOException e) { // Celle-ci se produit lors d'une erreur d'écriture ou de lecture
			e.printStackTrace();
		} catch (ClassNotFoundException e) { // Cette exception est levée si l'objet désérialisé n'a pas de classe connue
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
		
		/* Ajout d'un client à une liste de clients */
		Client c = new Client(champNom.getText(), champIP.getText(), Integer.parseInt(champPort.getText()));
		if (c.estConnecte()) { // Si le client n'a pas eu de problèmes de connexion
			listeClients.add(c);
			deconnexion.setClient(c); // Permet au bouton deconnexion de reconnaître le client qui clique dessus
			envoyer.setClient(c); // Permet au bouton envoyer de reconnaître le client qui clique dessus
			fenetre.changerDeconnexion(); // Le bouton de connexion devient un bouton de déconnexion 
			fenetre.accueilleLeClient(); // Les champs des connectés, de discussion et de message sont maintenant disponibles
		}

		/* Sauvegarde (sérialisation) de tous les clients dans le fichier client */
		ObjectOutputStream fichierClientsOutput = null;
			
		try {
			fichierClientsOutput = new ObjectOutputStream(new FileOutputStream("ListeClients.txt")); // Ouverture du fichier de clients en mode écriture
			for (Client cl : listeClients) // Écriture dans le fichier de tous les clients
				fichierClientsOutput.writeObject(cl);
		} catch (FileNotFoundException e) { // Cette exception est levée si l'objet FileInputStream ne trouve aucun fichier
			e.printStackTrace();
		} catch (IOException e) { // Celle-ci se produit lors d'une erreur d'écriture ou de lecture
			e.printStackTrace();
		} finally {
			// On ferme nos flux de données dans un bloc finally pour s'assurer que ces instructions seront exécutées dans tous les cas même si une exception est levée !*/
			try {
				if (fichierClientsOutput != null)
				fichierClientsOutput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
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
