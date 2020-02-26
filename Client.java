/* Réseau */
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/* Entrées/sorties */
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/** Classe représentant le client */
public class Client implements Serializable {

	private String nomClient;
	private InetAddress adresseIP;
	private int numeroPort;
	private transient Socket socket = null; // Préciser que le socket ne doit pas pouvoir être sérialisé
	private boolean estConnecte = true;

	/** Constructeur d'un client.
	  * Crée un socket client en fonction de son adresse IP et de son port.
	  * @param nom le nom du client (son pseudo)
	  * @param ip l'adresse IP du serveur avec lequel souhaite communiquer le client
	  * @param port le numéro de port sur lequel souhaite se connecter le client
	  */
	public Client(String nom, String ip, int port) {

		try {
			nomClient = nom;
			adresseIP = InetAddress.getByName(ip);
			numeroPort = port;
			this.envoyerMessage(nomClient + " vient de se connecter.\n"); // Envoie un message de connexion sur le tchat
		} catch (UnknownHostException e) {
			estConnecte = false;
			e.printStackTrace();
		} 
	}
	
	/** Getter sur le nom du client.
	  * @return le nom du client
	  */
	public String getNom() {
		return nomClient;
	}
	
	/** Getter sur le booléen estConnecte.
	  * @return vrai si la connexion a pu se faire, faux sinon
	  */
	public boolean estConnecte() {
		return estConnecte;
	}
	
	/** Setter sur le booléen estConnecte.
	  * Déconnecte le client, c'est à dire met le booléen à faux.
	  */
	public void deconnecter() {
		estConnecte = false;
		this.envoyerMessage(nomClient + " a quitté le tchat.\n"); // Envoie un message de déconnexion sur le tchat
	}
	
	/** Méthode qui permet au client d'envoyer un message au serveur.
	  * @param message le message à envoyer
	  */
	public void envoyerMessage(String message) {
	
		try {
		
			socket = new Socket(adresseIP, numeroPort);
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

			/* On envoie la commande au serveur */         
			String commande = message;
			writer.write(commande);
			writer.flush(); //TOUJOURS UTILISER flush() POUR ENVOYER RÉELLEMENT DES INFOS AU SERVEUR             
			             
         } catch (IOException e) { // Connexion non réussie
         
         	System.out.println("La connexion avec le serveur n'a pas pu être établie pour le client " + nomClient);
			System.out.println("Veuillez vérifier l'adresse IP du serveur et le port de connexion.");
			System.out.println("S'ils sont corrects, vérifiez que le serveur est opérationnel.");
			estConnecte = false;
			
		} finally { // Dans tous les cas on ferme le socket
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
					socket = null;
				}
		}
	}
}
