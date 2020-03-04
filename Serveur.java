/* Réseau */
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/* Entrées/sorties */
import java.io.IOException;

/** Classe représentant le serveur.
  * Le serveur a pour adresse IP 127.0.0.1 et son numéro de port est cherché de manière à trouver le premier port libre.
  * Le serveur lance un processus qui attend que le client formule une demande et répond à la demande dans un processus séparé.
  * La demande constitue en l'ajout du message de tchat du client dans le fichier de tchat.
  */
public class Serveur {

	private int port;
	private String hote = "127.0.0.1";
	private ServerSocket serveur = null;
	private boolean isRunning = true;

	/** Constructeur d'un serveur.
	  * Le serveur se connecte sur le premier port libre.
	  */
	public Serveur() {

		for (port = 1024 ; port <= 65535 ; port++)
			try {
				serveur = new ServerSocket(port, 2, InetAddress.getByName(hote)); // Création du socket représentant le serveur
				System.out.println("Port libre trouvé pour le serveur : " + port);
				break;
			} catch (UnknownHostException e) { // Adresse IP non valide
				e.printStackTrace();
			} catch (IOException e) {
				// On essaye de se connecter sur un port occupé, étant donné que l'on recherche le premier port libre, inutile d'afficher les connexions échouées avec des ports occupés
			}
	}

	/** Méthode qui lance le serveur.
	  * Le serveur attend une demande de client et la traîte dans un processus séparé.
	  */
	public void open(){

		/* Création du processus serveur (création dynamique du Runnable).
		 * Toujours dans un thread à part vu qu'il est dans une boucle infinie.
		 */
		Thread t = new Thread(new Runnable() {

			public void run() {

				while (isRunning == true)  // Processus serveur tournant en arrière-plan
					try {					
						Socket client = serveur.accept(); // On attend une connexion d'un client               
						Thread t = new Thread(new ProcessusClient(client)); // Une fois reçue, on la traite dans un thread séparé 
						t.start();
					} catch (IOException e) {
						e.printStackTrace();
					}
			
				try { // Fermeture du serveur
					serveur.close();
				} catch (IOException e) {
					e.printStackTrace();
					serveur = null;
				}
			}
		});

		t.start(); // Démarrage du processus serveur
	}

	/** Méthode fermant le serveur */
	public void close() {
		isRunning = false;
	}  
}
