import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;


public class ProcessusClient implements Runnable {

	private Socket sock;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;

	/** Constructeur du processus client */
	public ProcessusClient(Socket pSock){
		sock = pSock;
	}

	// Le traitement lancé dans un thread séparé
	public void run(){

		System.err.println("Lancement du traitement de la connexion cliente");
		boolean closeConnexion = false;

		// Tant que la connexion est active, on traite les demandes
		while (!sock.isClosed()) {

			try {

				//Ici, nous n'utilisons pas les mêmes objets que précédemment
				//Je vous expliquerai pourquoi ensuite
				writer = new PrintWriter(sock.getOutputStream());
				reader = new BufferedInputStream(sock.getInputStream());

				try {
					//Création de l'objet
					FileReader fr = new FileReader("HistoriqueMessages.txt");
					int i = 0;
					String str = "";
					//Lecture des données
					while((i = fr.read()) != -1)
						str += (char)i;
					//On ferme le flux
					fr.close();
					//Création de l'objet
					String response = str + read();
					FileWriter fw = new FileWriter("HistoriqueMessages.txt");
					fw.write(response);
					//On ferme le flux
					fw.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				/*InetSocketAddress remote = (InetSocketAddress)sock.getRemoteSocketAddress();

				//On affiche quelques infos, pour le débuggage
				String debug = "";
				debug = "Thread : " + Thread.currentThread().getName() + ". ";
				debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() +".";
				debug += " Sur le port : " + remote.getPort() + ".\n";
				debug += "\t -> Commande reçue : " + response + "\n";
				System.err.println("\n" + debug);

				//On traite la demande du client en fonction de la commande envoyée
				String toSend = "";



				switch(response.toUpperCase()){

				case "FULL":

				toSend = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM).format(new Date());

				break;

				case "DATE":

				toSend = DateFormat.getDateInstance(DateFormat.FULL).format(new Date());

				break;

				case "HOUR":

				toSend = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date());

				break;

				case "CLOSE":

				toSend = "Communication terminée"; 

				closeConnexion = true;

				break;

				default : 

				toSend = "Commande inconnu !";                     

				break;

			}



			//On envoie la réponse au client

			writer.write(toSend);

			//Il FAUT IMPERATIVEMENT UTILISER flush()

			//Sinon les données ne seront pas transmises au client

			//et il attendra indéfiniment

			writer.flush();


			*/
			closeConnexion = true;
			if(closeConnexion){

			System.err.println("COMMANDE CLOSE DETECTEE ! ");

			writer = null;

			reader = null;

			sock.close();

			break;

			}

		}catch(SocketException e){

		System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");

		break;

		} catch (IOException e) {

		e.printStackTrace();

		}         

		}

	}

	// La méthode que nous utilisons pour lire les réponses
	private String read() throws IOException{      

		String response = "";
		int stream;
		byte[] b = new byte[65536];
		stream = reader.read(b);
		response = new String(b, 0, stream);
		return response;
	}
}
