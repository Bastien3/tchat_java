/* Réseau */
import java.net.Socket;
import java.net.SocketException;

/* Entrées/sorties */
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/** Classe représentant le processus traitant la demande du client */
public class ProcessusClient implements Runnable {

	private Socket sock;
	private BufferedInputStream reader = null;

	/** Constructeur du processus client
	  * @param pSock la socket client
	  */
	public ProcessusClient(Socket pSock){
		sock = pSock;
	}

	/** Le traitement lancé dans un thread séparé */
	public void run() {

		/* Tant que la connexion est active, on traite les demandes */
		while (!sock.isClosed()) {

			try {

				/* Création des objets qui vont lire et écrire dans le fichier de discussion */
				reader = new BufferedInputStream(sock.getInputStream()); // Le message envoyé par le client
				FileReader fr = null; // L'objet qui va lire dans le fichier
				FileWriter fw = null; // L'objet qui écrire dans le fichier

				try {
					
					fr = new FileReader("HistoriqueMessages.txt");
					int i = 0;
					String contenuDiscussion = "";
					
					/* On récupère ce qu'il y a déjà dans le fichier */
					while((i = fr.read()) != -1)
						contenuDiscussion += (char)i;
						
					/* On construit le nouveau contenu du fichier (ancien contenu + message) */
					String reponse = contenuDiscussion + read();
					
					/* On écrit le tout dans le fichier */
					fw = new FileWriter("HistoriqueMessages.txt");
					fw.write(reponse);
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally { // On ferme nos flux de données dans un bloc finally pour s'assurer que ces instructions seront exécutées dans tous les cas même si une exception est levée !
					try {
						if (fr != null)
							fr.close();
						if (fw != null)
							fw.close();
							
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				reader = null;
				sock.close();

			} catch (SocketException e) {
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}         
		}
	}

	/** Méthode utilisée pour lire les réponses 
	  * @return la réponse lue
	  */
	private String read() throws IOException {      

		String reponse = "";
		int stream;
		byte[] b = new byte[128];
		stream = reader.read(b);
		reponse = new String(b, 0, stream);
		return reponse;
	}
}
