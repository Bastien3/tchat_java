import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import java.awt.Dimension;
import java.text.NumberFormat;
 
public class Fenetre extends JFrame {

	
 	
	/** Constructeur d'une fenêtre */
	public Fenetre() {
		
 		JButton bouton = new JButton("Connexion");

		this.setTitle("Tchat");  // Définit un titre pour notre fenêtre    
		this.setSize(400, 600); // Définit sa taille : 400 pixels de large et 600 pixels de haut    
		this.setLocationRelativeTo(null); // Nous demandons maintenant à notre objet de se positionner au centre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termine le processus lorsqu'on clique sur la croix rouge  
		this.setResizable(true); // Empêche la fenêtre de pouvoir être redimensionnée 
		this.setAlwaysOnTop(false) ; // Affiche la fenêtre au premier plan
		this.setUndecorated(false); // Permet de laisser les contours et lez boutons de contrôle   
		
		/* Définition du panneau contenant les 2 premiers champs : nom, connexion
		 * Ce panneau contient une ligne horizontale contenant les 2 champs.
		 */
    	JPanel p1 = new JPanel();
    	FlowLayout fl1 = new FlowLayout();
    	JTextField tf1 = new JTextField();
    	JButton b1 = new JButton("Connexion");
    	tf1.setPreferredSize(new Dimension(150, 30));
    	p1.setLayout(fl1);
    	p1.add(new JLabel("Nom"));
    	p1.add(tf1);
    	p1.add(b1);
    	
    	/* Définition du panneau contenant les 2 champs suivants : ID, port
		 * Ce panneau contient une ligne horizontale contenant les 2 champs.
		 */
    	JPanel p2 = new JPanel();
    	FlowLayout fl2 = new FlowLayout();
    	JFormattedTextField ftf1 = new JFormattedTextField(NumberFormat.getIntegerInstance());
    	JFormattedTextField ftf2 = new JFormattedTextField(NumberFormat.getIntegerInstance());
    	ftf1.setPreferredSize(new Dimension(150, 30));
    	ftf2.setPreferredSize(new Dimension(150, 30));
    	p2.setLayout(fl2);
    	p2.add(new JLabel("IP"));
    	p2.add(ftf1);
    	p2.add(new JLabel("Port"));
    	p2.add(ftf2);
    	
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
		JTextField tf2 = new JTextField();
		p4.setLayout(new BoxLayout(p4, BoxLayout.PAGE_AXIS));
		p4.add(new JLabel("Connectés"));
    	p4.add(tf2);
		
		/* Définition du panneau contenant la discussion, le message et le bouton envoyer.
    	 * C'est une colonne qui contient 3 éléments.
    	 */
		JPanel p5 = new JPanel();
		JTextField tf3 = new JTextField(100);
		JTextField tf4 = new JTextField(100);
		p5.setLayout(new BoxLayout(p5, BoxLayout.PAGE_AXIS));
		p5.add(new JLabel("Discussion"));
    	p5.add(tf3);
    	p5.add(new JLabel("Message"));
    	p5.add(tf4);
    	JButton b2 = new JButton("Envoyer");
    	b2.addActionListener(new BoutonListener());
		p5.add(b2);
		
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
	
	class BoutonListener implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      System.out.println("TEXT : jtf " + tf4.getText());
    }
  }      
}
