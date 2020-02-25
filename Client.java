import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.Serializable;
import java.io.BufferedInputStream;
import java.io.PrintWriter;

/** Classe représentant le client */
public class Client implements Serializable {

	private String nomClient;
	private InetAddress adresseIP;
	private int numeroPort;
	private transient Socket socket = null; // Préciser que le socket ne doit pas pouvoir être sérialisé
	private boolean estConnecte = true;

	/** Constructeur d'un client.
	  * Crée un socket client en fonction de son adresse IP et de son port.
	  * @param ip l'adresse IP du serveur avec lequel souhaite communiquer le client
	  * @param port le numéro de port sur lequel souhaite se connecter le client
	  */
	public Client(String nom, String ip, int port) {

		try {
			nomClient = nom;
			adresseIP = InetAddress.getByName(ip);
			numeroPort = port;
			this.envoyerMessage(nomClient + " vient de se connecter.\n");
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
		this.envoyerMessage(nomClient + " a quitté le tchat.\n");
	}
	
	/** Méthode qui permet au client d'envoyer un message au serveur.
	  * @param message le message à envoyer
	  */
	public void envoyerMessage(String message) {
	
		try {
			System.out.println(message);
			socket = new Socket(adresseIP, numeroPort);
			System.out.println("Client : nom " + nomClient + " adresse IP " + adresseIP.getHostAddress() + " port " + numeroPort);

			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			//reader = new BufferedInputStream(connexion.getInputStream());

			//On envoie la commande au serveur           
			String commande = message;
			writer.write(commande);
			//TOUJOURS UTILISER flush() POUR ENVOYER RÉELLEMENT DES INFOS AU SERVEUR
			writer.flush();             
			System.out.println("Commande " + commande + " envoyée au serveur");

			//On attend la réponse
			/*String response = read();
			System.out.println("\t * " + name + " : Réponse reçue " + response);*/
                       
         } catch (IOException e) {
			estConnecte = false;
			e.printStackTrace();
		} finally {
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
