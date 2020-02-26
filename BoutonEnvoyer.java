/* Interface graphique */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JTextArea;

/* Entrées/sorties */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/* Date */
import java.text.DateFormat;
import java.util.Date;

/** Classe implémentant le bouton d'envoi de message. */
class BoutonEnvoyer extends JButton implements MouseListener {

	JTextArea champDiscussion, champMessage;
	private Client client;
	private boolean cliquable = false;
	
	/** Constructeur d'un bouton d'envoi de message
	  * @param discussion le champ discussion du tchat
	  * @param message le champ message du tchat
	  */
	public BoutonEnvoyer(JTextArea discussion, JTextArea message){
		super("Envoyer");
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
	
	/** Setter sur le booléen cliquable.
	  * Permet de déterminer si le bouton est cliquable ou pas.
	  * Si le bouton n'est pas cliquable, il est grisé.
	  */
	public void setCliquable(boolean b) {
		cliquable = b;
	}
	
	/** Méthode appelée lors du clic de souris.
	  * @param event l'événement attendu
	  */
	public void mouseClicked(MouseEvent event) {
	
		/* Si le bouton n'est pas cliquable, cliquer dessus n'enclenche aucune action */
		if (!cliquable)
			return;
	
		/* Envoi du message au format suivant : hh:mm:ss pseudo : message */
		if (client.estConnecte()) 
			client.envoyerMessage(DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date()) + " " + client.getNom() + " : " + champMessage.getText() + "\n");
		
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
