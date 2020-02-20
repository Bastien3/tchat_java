import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.text.NumberFormat;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Toolkit;
 
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

	/** Constructeur d'une fenêtre */
	public Fenetre() {
		
		this.setTitle("Tchat");  // Définit un titre pour notre fenêtre    
		this.setSize(largeur / 2, hauteur); // Définit sa taille : 400 pixels de large et 600 pixels de haut    
		this.setLocationRelativeTo(null); // Nous demandons maintenant à notre objet de se positionner au centre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termine le processus lorsqu'on clique sur la croix rouge  
		this.setResizable(true); // Empêche la fenêtre de pouvoir être redimensionnée 
		this.setAlwaysOnTop(false) ; // Affiche la fenêtre au premier plan
		this.setUndecorated(false); // Permet de laisser les contours et lez boutons de contrôle   
		
		
		
		/* Définition du panneau contenant les 2 premiers champs : nom, connexion
		 * Ce panneau contient une ligne horizontale contenant les 2 champs.
		 */
    	JPanel p1 = new JPanel();
    	FlowLayout fl1 = new FlowLayout(); // Conteneur des 2 champs
    	JTextField tf1 = new JTextField(); // Champ de saisie du nom
    	tf1.setPreferredSize(new Dimension(150, 30)); // Dimension du champ : 150x30
    	
    	p1.setLayout(fl1); // Ajout du conteneur au panneau
    	
    	p1.add(new JLabel("Nom")); // Ajout du label "nom"
    	p1.add(tf1); // Ajout du champ de texte
    	
    	
    	
    	/* Définition du panneau contenant les 2 champs suivants : ID, port
		 * Ce panneau contient une ligne horizontale contenant les 2 champs.
		 */
    	JPanel p2 = new JPanel();
    	FlowLayout fl2 = new FlowLayout(); // Conteneur des 2 champs
    	JTextField tf2 = new JTextField(); // Champ de saisie de l'IP
    	JTextField tf3 = new JTextField(); // Champ de saisie du port
    	
    	tf2.setPreferredSize(new Dimension(150, 30)); // Dimension du champ : 150x30
    	tf3.setPreferredSize(new Dimension(150, 30)); // Dimension du champ : 150x30
    	p2.setLayout(fl2); // Ajout du conteneur au panneau
    	
    	p2.add(new JLabel("IP")); // Ajout du label "IP"
    	p2.add(tf2); // Ajout du champ de texte
    	p2.add(new JLabel("Port")); // Ajout du label "port"
    	p2.add(tf3); // Ajout du champ de texte
    	
    	
    	
    	/* Définition du panneau contenant les 2 panneaux précédents.
    	 * C'est une colonne qui contient p1 et p2.
    	 */
		JPanel p3 = new JPanel();
		
		p3.setLayout(new BoxLayout(p3, BoxLayout.PAGE_AXIS));
		
		p3.add(p1);
		p3.add(p2);
    	
    	
    	
    	/* Définition du panneau contenant la liste des connectés..
    	 * C'est une ligne qui contient un élément : le champ des connectés.
    	 */
		JPanel p4 = new JPanel();
		JPanel p41 = new JPanel();
		JPanel p42 = new JPanel();
		JTextArea ta1 = new JTextArea(); // Champ de saisie des connectés
		JScrollPane sp1 = new JScrollPane(ta1, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Barre de défilement
		
		ta1.setEditable(false); // Le champ connectés ne peut pas être modifié
		
		// Fixation d'une limite pour les tailles avec des barres de défilement
		// !!! C'est bien le JScrollPane qui définit la taille du champ et non le JTextArea !!!
		sp1.setPreferredSize(new Dimension(largeur / 10, hauteur / 4 * 3));
		
		p4.setLayout(new BoxLayout(p4, BoxLayout.PAGE_AXIS));
	
		p41.add(new JLabel("Connectés")); // Ajout du label "connectés"
		p42.add(sp1); // Ajout du champ de texte
    	p4.add(p41);
    	p4.add(p42);
    	
    	
    	JButton connexion = new BoutonConnexion(tf1, ta1); // Création d'un bouton de connexion
    	p1.add(connexion); // Ajout du bouton de connexion
		
		
		
		/* Définition du panneau contenant la discussion, le message et le bouton envoyer.
    	 * C'est une colonne qui contient 5 sous-panneaux contenant les éléments.
    	 */
		JPanel p5 = new JPanel();
		JPanel p51 = new JPanel();
		JPanel p52 = new JPanel();
		JPanel p53 = new JPanel();
		JPanel p54 = new JPanel();
		JPanel p55 = new JPanel();
		JTextArea ta2 = new JTextArea(); // Champ de saisie de la discussion
		JTextArea ta3 = new JTextArea(); // Champ de saisie des messages
		JScrollPane sp2 = new JScrollPane(ta2, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Barre de défilement
		JScrollPane sp3 = new JScrollPane(ta3, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Barre de défilement
		
		ta2.setEditable(false); // Le champ discussion ne peut pas être modifié
		
		// Fixation d'une limite pour les tailles avec des barres de défilement
		sp2.setPreferredSize(new Dimension(largeur / 27 * 10, hauteur / 2));
		sp3.setPreferredSize(new Dimension(largeur / 27 * 10, hauteur / 6));
		
		p5.setLayout(new BoxLayout(p5, BoxLayout.PAGE_AXIS));
		
		p51.add(new JLabel("Discussion")); // Ajout du label "discussion"		
    	p52.add(sp2); // Ajout du champ de texte 	
    	p53.add(new JLabel("Message")); // Ajout du label "message"    	
    	p54.add(sp3); // Ajout du champ de texte
    	p5.add(p51);
		p5.add(p52);
		p5.add(p53);
		p5.add(p54);
		p5.add(p55);
		
    	JButton envoyer = new BoutonEnvoyer(ta2, ta3); // Bouton pour envoyer le message
		p55.add(envoyer); // Ajout du bouton
		
		
		
		
		/* Définition du panneau contenant les panneaux p4 et p5.
    	 * C'est une ligne.
    	 */
		JPanel p6 = new JPanel();
		
		p6.setLayout(new BoxLayout(p6, BoxLayout.LINE_AXIS));
		
		p6.add(p4);
		p6.add(p5);
		
		
		
		/* Définition du panneau contenant les panneaux p3 et p6.
    	 * C'est une colonne.
    	 */
		JPanel p7 = new JPanel();
		
		p7.setLayout(new BoxLayout(p7, BoxLayout.PAGE_AXIS));
		
		p7.add(p3);
		p7.add(p6);
		
   		this.getContentPane().add(p7); // On prévient notre JFrame que notre JPanel sera son content pane
   		
		this.setVisible(true); 
	} 
	
	/*class BoutonListener implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      System.out.println("TEXT : jtf " + tf4.getText());
    }
  }   */   
}
