import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/** Classe implémentant le bouton de connexion au tchat */
class BoutonConnexion extends JButton implements MouseListener {

	private String nomBouton;
	private JTextField champNom;
	private JTextArea champConnectes;
	
	/** Constructeur d'un bouton de connexion */
	public BoutonConnexion(JTextField nom, JTextArea connectes){
		super("Connexion");
		nomBouton = "Connexion";
		champNom = nom;
		champConnectes = connectes;
		/*Grâce à cette instruction, notre objet va s'écouter. Dès qu'un événement de la souris sera intercepté, il en sera averti */
		this.addMouseListener(this);
	}
	
	//Méthode appelée lors du clic de souris
	public void mouseClicked(MouseEvent event) {
		champConnectes.setText(champConnectes.getText() + champNom.getText() + "\n");
		champNom.setText("");
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
