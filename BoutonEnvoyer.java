import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.util.Date;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/** Classe implémentant le bouton d'envoi de message. */
class BoutonEnvoyer extends JButton implements MouseListener {

	private String nom;
	JTextArea champDiscussion, champMessage;
	private Client client;
	
	/** Constructeur d'un bouton d'envoi de message */
	public BoutonEnvoyer(JTextArea discussion, JTextArea message){
		super("Envoyer");
		nom = "Envoyer";
		champDiscussion = discussion;
		champMessage = message;
		/*Grâce à cette instruction, notre objet va s'écouter. Dès qu'un événement de la souris sera intercepté, il en sera averti */
		this.addMouseListener(this);
	}
	
	/** Setter sur client
	  * @param c le client à affecter
	  */
	public void setClient(Client c) {
		client = c;
	}
	
	//Méthode appelée lors du clic de souris
	public void mouseClicked(MouseEvent event) {
	
		/* Envoi du message */
		if (client.estConnecte()) 
			client.envoyerMessage(DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date()) + " " + client.getNom() + " : " + champMessage.getText() + "\n");
			
		/* On attend 100 ms avant d'actualiser le temps que le serveur mette à jour le fichier des messages */
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		/* On actualise */
		String str = "";
		try {
			//Création de l'objet
			FileReader fr = new FileReader("HistoriqueMessages.txt");
			int i = 0;
			//Lecture des données
			while((i = fr.read()) != -1)
				str += (char)i;
			//On ferme le flux
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		champDiscussion.setText(str);
		champMessage.setText("");
	}
	//Méthode appelée lors du survol de la souris
	public void mouseEntered(MouseEvent event) { }
	//Méthode appelée lorsque la souris sort de la zone du bouton
	public void mouseExited(MouseEvent event) { }
	//Méthode appelée lorsque l'on presse le bouton gauche de la souris
	public void mousePressed(MouseEvent event) { }
	//Méthode appelée lorsque l'on relâche le clic de souris
	public void mouseReleased(MouseEvent event) { }
}
