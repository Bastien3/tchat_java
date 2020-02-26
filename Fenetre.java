/* Interface graphique */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/* Entrées/sorties */
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
 
/** Classe implémentant la fenêtre de tchat.
  * Elle contient les champs suivants : 
  * - nom : pour saisir le nom de la personne
  * - connexion : bouton pour se connecter au tchat
  * - IP : adresse IP de la personne
  * - port : port sur laquelle elle souhaite se connecter
  * - connectés : la liste des personnes connectées
  * - discussion : la discussion du tchat
  * - message : le champ de saisie du message
  * - envoyer : bouton pour confirmer l'envoi de message
  */
public class Fenetre extends JFrame {

	private int largeur = (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth());
	private int hauteur = (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight());
	private JPanel panneauConnexion, panneauServeur, panneauConnectes, panneauDiscussion;
	private BoutonConnexion connexion;
	private BoutonDeconnexion deconnexion;
	private BoutonEnvoyer envoyer;
	private JTextArea champConnectes, champDiscussion, champMessage;
	private JTextField champNom, champIP, champPort;
	private Color gris = new Color(240,240,240);

	/** Constructeur d'une fenêtre */
	public Fenetre() {
		
		this.setTitle("Tchat");  // Définit un titre pour notre fenêtre    
		this.setSize(largeur / 2, hauteur); // Définit sa taille : 1/2 de la largeur de l'écran et la longueur de sa hauteur   
		this.setLocationRelativeTo(null); // Nous demandons maintenant à notre objet de se positionner au centre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termine le processus lorsqu'on clique sur la croix rouge  
		
		
		
		/* Définition du panneau contenant les 2 premiers champs : nom, connexion
		 * Ce panneau contient une ligne horizontale contenant les 2 champs.
		 */
    	panneauConnexion = new JPanel();
    	FlowLayout fl1 = new FlowLayout(); // Conteneur des 2 champs
    	champNom = new JTextField(); // Champ de saisie du nom
    	champNom.setPreferredSize(new Dimension(150, 30)); // Dimension du champ : 150x30
    	
    	panneauConnexion.setLayout(fl1); // Ajout du conteneur au panneau
    	
    	panneauConnexion.add(new JLabel("Nom")); // Ajout du label "nom"
    	panneauConnexion.add(champNom); // Ajout du champ de texte
    	
    	
    	
    	/* Définition du panneau contenant les 2 champs suivants : ID, port
		 * Ce panneau contient une ligne horizontale contenant les 2 champs.
		 */
    	panneauServeur = new JPanel();
    	FlowLayout fl2 = new FlowLayout(); // Conteneur des 2 champs
    	champIP = new JTextField(); // Champ de saisie de l'IP
    	champPort = new JTextField(); // Champ de saisie du port
    	
    	champIP.setPreferredSize(new Dimension(150, 30)); // Dimension du champ : 150x30
    	champPort.setPreferredSize(new Dimension(150, 30)); // Dimension du champ : 150x30
    	panneauServeur.setLayout(fl2); // Ajout du conteneur au panneau
    	
    	panneauServeur.add(new JLabel("IP")); // Ajout du label "IP"
    	panneauServeur.add(champIP); // Ajout du champ de texte
    	panneauServeur.add(new JLabel("Port")); // Ajout du label "port"
    	panneauServeur.add(champPort); // Ajout du champ de texte
    	
    	
    	
    	/* Définition du panneau contenant les 2 panneaux précédents.
    	 * C'est une colonne qui contient panneauConnexion et panneauServeur.
    	 */
		JPanel p1 = new JPanel();
		
		p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
		
		p1.add(panneauConnexion);
		p1.add(panneauServeur);
    	
    	

    	/* Définition du panneau contenant la liste des connectés.
    	 * C'est une colonne qui contient 2 panneaux contenant eux-mêmes le label "Connectés" et le champ des connectés.
    	 */
		panneauConnectes = new JPanel();
		JPanel p21 = new JPanel(); // Panneau contenant le label "Connectés"
		JPanel p22 = new JPanel(); // Panneau contenant le champ des connectés
		champConnectes = new JTextArea(); // Champ de saisie des connectés
		JScrollPane sp1 = new JScrollPane(champConnectes, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Barre de défilement
		
		champConnectes.setEditable(false); // Le champ connectés ne peut pas être modifié
		champConnectes.setBackground(gris); // Le champ connectés est grisé tant que le client ne s'est pas connecté
		
		// Fixation d'une limite pour les tailles avec des barres de défilement
		// !!! C'est bien le JScrollPane qui définit la taille du champ et non le JTextArea !!!
		sp1.setPreferredSize(new Dimension(largeur / 10, hauteur / 4 * 3));
		
		panneauConnectes.setLayout(new BoxLayout(panneauConnectes, BoxLayout.PAGE_AXIS));
	
		p21.add(new JLabel("Connectés")); // Ajout du label "connectés"
		p22.add(sp1); // Ajout du champ de texte
    	panneauConnectes.add(p21);
    	panneauConnectes.add(p22);
		
		
		
		/* Définition du panneau contenant la discussion, le message et le bouton envoyer et le bouton actualiser.
    	 * C'est une colonne qui contient 5 sous-panneaux contenant les éléments.
    	 */
		panneauDiscussion = new JPanel();
		JPanel p31 = new JPanel();
		JPanel p32 = new JPanel();
		JPanel p33 = new JPanel();
		JPanel p34 = new JPanel();
		JPanel p35 = new JPanel();
		champDiscussion = new JTextArea(); // Champ de saisie de la discussion
		champMessage = new JTextArea(); // Champ de saisie des messages
		JScrollPane sp2 = new JScrollPane(champDiscussion, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Barre de défilement pour le champ discussion
		JScrollPane sp3 = new JScrollPane(champMessage, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Barre de défilement pour le champ message
		
		champDiscussion.setEditable(false); // Le champ discussion ne peut pas être modifié
		champDiscussion.setBackground(gris); // Le champ discussion est grisé tant que le client ne s'est pas connecté
		champMessage.setEditable(false); // Le champ message ne peut pas être modifié tant que la connexion n'est pas établie
		champMessage.setBackground(gris); // Le champ message est grisé tant que le client ne s'est pas connecté
		
		// Fixation d'une limite pour les tailles avec des barres de défilement
		sp2.setPreferredSize(new Dimension(largeur / 27 * 10, hauteur / 2));
		sp3.setPreferredSize(new Dimension(largeur / 27 * 10, hauteur / 6));
		
		panneauDiscussion.setLayout(new BoxLayout(panneauDiscussion, BoxLayout.PAGE_AXIS));
		
		p31.add(new JLabel("Discussion")); // Ajout du label "discussion"		
    	p32.add(sp2); // Ajout du champ de texte 	
    	p33.add(new JLabel("Message")); // Ajout du label "message"    	
    	p34.add(sp3); // Ajout du champ de texte
    	panneauDiscussion.add(p31);
		panneauDiscussion.add(p32);
		panneauDiscussion.add(p33);
		panneauDiscussion.add(p34);
		panneauDiscussion.add(p35);
		
		
		
		/* Création des boutons envoyer, connexion et deconnexion */
    	envoyer = new BoutonEnvoyer(champDiscussion, champMessage); // Bouton pour envoyer le message
    	envoyer.setBackground(gris); // Tant que le client n'est pas connecté, le bouton envoyer est grisé
		p35.add(envoyer); // Ajout du bouton
    	deconnexion = new BoutonDeconnexion(champNom, champIP, champPort, champConnectes, champDiscussion, envoyer, this); // Création d'un bouton de déconnexion (qui va se substituer au bouton de connexion par la suite)
    	connexion = new BoutonConnexion(champNom, champIP, champPort, champConnectes, champDiscussion, envoyer, deconnexion, this); // Création d'un bouton de connexion
    	connexion.setBackground(gris); // Tant que les champs nom, IP et port ne sont pas remplis, le bouton connexion est grisé
    	panneauConnexion.add(connexion); // Ajout du bouton de connexion
		
		
		
		/* Définition du panneau contenant les panneaux panneauConnectes et panneauDiscussion.
    	 * C'est une ligne.
    	 */
		JPanel p4 = new JPanel();
		
		p4.setLayout(new BoxLayout(p4, BoxLayout.LINE_AXIS));
		
		p4.add(panneauConnectes);
		p4.add(panneauDiscussion);
		
		
		
		/* Définition du panneau contenant les panneaux p3 et p6.
    	 * C'est une colonne.
    	 */
		JPanel p5 = new JPanel();
		
		p5.setLayout(new BoxLayout(p5, BoxLayout.PAGE_AXIS));
		
		p5.add(p1);
		p5.add(p4);
		
   		this.getContentPane().add(p5); // On prévient notre JFrame que notre JPanel sera son content pane
   		
		this.setVisible(true); 
		
		/* Actualisation du tchat toutes les secondes */
		Fenetre f = this;
		Thread t = new Thread(new Runnable() {
		
			public void run() {
				while (true) 
					try {
						Thread.sleep(1000);
						f.actualiseToi();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		});
		
		t.start();
	}   
	
	/** Méthode changeant le bouton de connexion en bouton de déconnexion */
	public void changerDeconnexion() {
		panneauConnexion.remove(connexion);
		panneauConnexion.add(deconnexion);
		this.setVisible(true); 
	}
	
	/** Méthode changeant le bouton de déconnexion en bouton de connexion */
	public void changerConnexion() {
		panneauConnexion.remove(deconnexion);
		panneauConnexion.add(connexion);
		this.setVisible(true); 
	}
	
	/** Méthode qui change la couleur du bouton de connexion et le rend cliquable ou pas.
	  * @param b le booléen qui détermine si le bouton est cliquable ou pas
	  */
	public void setConnexion(boolean b) {
		if (b) {
			connexion.setBackground(Color.WHITE);
			connexion.setCliquable(true);
		} else {
			connexion.setBackground(gris);
			connexion.setCliquable(false);
		}		
	}
	
	/** Méthode qui grise les zones de saisie de nom, IP en empêchant le client de les éditer et port et dégrise le reste.
	  * Le client peut maintenant saisir des messages.
	  */
	public void accueilleLeClient() {
	
		champConnectes.setBackground(Color.WHITE);
		champDiscussion.setBackground(Color.WHITE);
		champMessage.setBackground(Color.WHITE);
		champMessage.setEditable(true);
		
		champNom.setBackground(gris);
		champNom.setEditable(false);
		champIP.setBackground(gris);
		champIP.setEditable(false);
		champPort.setBackground(gris);
		champPort.setEditable(false);
		
		envoyer.setBackground(Color.WHITE);
		envoyer.setCliquable(true);
	}
	
	/** Méthode qui fait l'inverse de la méthode accepteLeClient()*/
	public void vireLeClient() {
	
		champConnectes.setBackground(gris);
		champDiscussion.setBackground(gris);
		champMessage.setBackground(gris);
		champMessage.setEditable(false);
		
		champNom.setBackground(Color.WHITE);
		champNom.setEditable(true);
		champIP.setBackground(Color.WHITE);
		champIP.setEditable(true);
		champPort.setBackground(Color.WHITE);
		champPort.setEditable(true);
		
		envoyer.setBackground(gris);
		envoyer.setCliquable(false);
	}
	
	public void actualiseToi() {
	
		/* Si les champs nom, IP et port sont tous remplis, le bouton connexion est dégrisé et devient cliquable.
		 * Sinon il devient grisé et n'est plus cliquable.
		 */
		if (champNom.getText().length() > 0 && champIP.getText().length() > 0 && champPort.getText().length() > 0)
			this.setConnexion(true);
		else
			this.setConnexion(false);
	
		/* Chargement (désérialisation) de la liste des clients */
		ObjectInputStream fichierClientsInput = null; // ObjectInputStream : fichier comportant des objets	
		try {
			fichierClientsInput = new ObjectInputStream(new FileInputStream("ListeClients.txt")); // Ouverture du fichier de clients en mode lecture
			Client c;
			champConnectes.setText(""); // Effacement du champ connectés pour y afficher ensuite l'actuelle liste des clients
			while ((c = (Client)(fichierClientsInput.readObject())) != null) // Ajout de chaque client du fichier à la liste des clients connectés dans le champ des connectés
				champConnectes.setText(champConnectes.getText() + c.getNom() + "\n");
		
		} catch (EOFException e) { // Cette exception est levée lorsque la fin du fichier est atteinte
			
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
		
		/* Actualisation du champ discussion */
		String contenuDiscussion = "";
		FileReader fr = null;
		try {
			fr = new FileReader("HistoriqueMessages.txt"); // Création de l'objet permettant de lire le fichier de discussion
			int i = 0;
			while ((i = fr.read()) != -1) // Lecture des données
				contenuDiscussion += (char)i; // Ajout au champ de texte
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// On ferme nos flux de données dans un bloc finally pour s'assurer que ces instructions seront exécutées dans tous les cas même si une exception est levée !
			try {
				if (fr != null)
					fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		champDiscussion.setText(contenuDiscussion); // Mise à jour du champ de texte de discussion
	}
}
