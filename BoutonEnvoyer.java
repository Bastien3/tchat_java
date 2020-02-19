import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/** Classe implémentant le bouton d'envoi de message. */
class BoutonEnvoyer extends JButton implements MouseListener {

	private String nom;
	private int numeroMessage = 0;
	JTextArea champDiscussion, champMessage;
	
	/** Constructeur d'un bouton d'envoi de message */
	public BoutonEnvoyer(JTextArea discussion, JTextArea message){
		super("Envoyer");
		nom = "Envoyer";
		champDiscussion = discussion;
		champMessage = message;
		/*Grâce à cette instruction, notre objet va s'écouter. Dès qu'un événement de la souris sera intercepté, il en sera averti */
		this.addMouseListener(this);
	}
	
	//Méthode appelée lors du clic de souris
	public void mouseClicked(MouseEvent event) {
		numeroMessage++;
		champDiscussion.setText(champDiscussion.getText() +  "Message " + numeroMessage + " : " + champMessage.getText() + "\n");
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
