TP 5 : TCHAT JAVA

Dans le cadre du TP5 du module POO & Java, il fallait implémenter un tchat en Java qui ait les fonctionnalités suivantes : 
- avoir une interface graphique qui permette à l'utilisateur de se connecter puis d'entrer des messages
- permettre une discussion entre plusieurs utilisateurs affichée dans un même champ
- utiliser une implémentation client/serveur, avec un serveur qui gère la discussion et les connexions, et les clients qui sont les différents utilisateurs
- griser le bouton de connexion tant que les champs nom, ID et port ne sont pas remplis
- griser les autres champs (connectés, discussion...) tant que l'utilisateur ne s'est pas connecté
- griser les champs nom, ID et port et transformer le bouton de connexion en bouton de déconnexion quand l'utilisateur est connecté
- chaque utiisateur doit avoir une couleur distincte dans le tchat

I) Interface graphique

L'interface graphique est divisée en 2 grandes parties : 
- la partie connexion, qui comprend les champs nom, IP et port qui sont des JTextField, et un bouton de connexion. Le tout est contenu dans un BoxLayout.
- la partie tchat, qui comprend les champs connectés, discussion et message qui sont des JTextArea et un bouton Envoyer. Le tout est également contenu dans un BoxLayout.

La fenêtre n'est pas redimensionnable et sa taille est calculée par rapport à la taille de l'écran de l'ordinateur (1/2 de sa largeur et 9/10 de sa hauteur).

Les champs connectés, message et discussion sont grisés tant que l'utilisateur n'est pas connecté mais il peut tout de même consulter le contenu de ces champs notamment pour savoir qui est déjà connecté et où en est la discussion. Le bouton connexion devient cliquable automatiquement quand les champs nom, ID et port sont remplis même s'ils contiennent des valeurs incohérentes (l'envoi de a requête de connexion se chargera d'accepter ou non la connexion). Une fois que le client à réussi à se connecter avec succès, les champs nom, ID et port deviennent grisés et les autres champs deviennent accessibles. Son nom apparaît dans la liste des connectés et un message apparaît dans la discussion pour avertir les personnes déjà connectées de l'arrivée de cet utilisateur. 

Seul le champ message est éditable. L'utilisateur doit cliquer sur le bouton Envoyer pour envoyer son message (un appui sur la touche Entrée permet juste d'aller à la ligne). Une fois le message envoyé, il apparaît dans la discussion avec l'heure à laquelle il a été envoyé et le pseudo de celui qui l'a envoyé. Les champs connectés, message et discussion sont extensibles en taille via des barres de défilement horizontales et verticales. Le contenu du champ discussion est chargé avec celui du fichier HistoriqueMessages.txt commum à toutes les fenêtre de tchat. La liste des connectés est contenue dans un fichier ListeClients.txt contenant les clients sérialisés.

Quand l'utilisateur clique sur le bouton Deconnexion, les champs grisés deviennent non grisés et inversement, le bouton se transforme en bouton de connexion, un message apparaît dans la discussion avertissant de cette déconnexion et le pseudo est retiré de la liste des connectés. Pour le moment l'utilisateur n'est pas automatiquement déconnecté s'il quitte la fenêtre de tchat, s'il ne pense pas à se déconnecter, son nom reste dans la liste des connectés. Ce problème a été réglé partiellement en faisant en sorte que la déconnexion supprime de la liste des connectés tous ceux ayant le même pseudo que celui qui se déconnecte (sans les déconnecter eux-mêmes). Cela permet à celui qui a quitté la fenêtre de se reconnecter avec le même pseudo puis de se déconnecter en supprimant les 2 occurences de son pseudo.

La fenêtre est actualisée toutes les secondes à l'aide d'un processus qui recharge les champs connectés et discussion avec le contenu des fichiers correspondants (respectivement ListeClients.txt et HistoriqueMessages.txt). Ce processus reconstruit toute la fenêtre de sorte que les modifications d'apparence (champs grisés/non grisés, bouton connexion/déconnexion) soient opérées. Cela explique une légère latence quand le message est envoyé. Ce processus permet à toutes les fenêtres de tchat de modifier leurs champs connectés et discussion pour les garder à jour sans que le serveur ait à envoyer de message à tous les clients.

II) Client/serveur

Le serveur doit être lancé au préalable pour que les utilisateurs puissent se connecter au tchat. Il suffit de taper la commande : java LanceServeur. Le serveur va essayer de se connecter sur le premier port libre à l'adresse 127.0.0.1 en envoyant un socket sur chacun des ports à partir du port numéro 1024. Quand il a réussi, il envoie un message dans le terminal indiquant le numéro du port sur lequel il s'est connecté. Ce message est important car c'est le numéro de port que doit saisir le client dans le champ port du tchat.

Quand un client souhaite se connecter au tchat, il remplit les champs nom, IP et port et appuie sur le bouton Connexion. Un socket est alors envoyé au serveur. Si la connexion s'est établie avec succès, le client a maintenant accès au tchat. Sinon un message d'erreur est envoyé dans le terminal client indiquant ce qu'il doit vérifier (IP égale à 127.0.0.1, port correspondant ou disponibilité du serveur).

Quand un client envoie un message dans le tchat, un socket est envoyé au serveur, il est ensuite rempli avec le contenu du message et envoyé au serveur. Le serveur va ouvrir un fichier HistoriqueMessages.txt, lire son contenu, y ajouter le message accompagné de la date d'envoi et du pseudo du client et écrire dans le fichier. Le tchat se charge d'actualiser toutes les secondes le contenu du champ discussion avec le contenu de ce fichier pour que tous les utilisateurs voient le message apparaître en même temps. Si le serveur tombe en panne, le tchat cesse d'être actualisé, les messages envoyés sont perdus mais le client n'est pas déconnecté. Cependant, une fois que le serveur est de nouveau opérationnel, il doit se déconnecter puis se reconnecter.

La déconnexion se déroule de la même manière que la connexion, et une fois qu'elle a réussi, le tchat est mis à jour pour que le client doive se connecter à nouveau pour tchatter. Si le serveur tombe en panne entre temps, le client est quand même retiré de la liste des connectés mais sa déconnexion n'apparaît pas dans le tchat.

III) Scénario d'utilisation

Toto veut se connecter sur le tchat. Pour cela, le serveur doit d'abord avoir été lancé avec la commande "java LanceServeur". Toto doit lancer le tchat avec la commande "java Tchat". Toto doit ensuite rentrer son pseudo "toto", l'adresse IP du serveur qui est 127.0.0.1 et le port sur lequel est connecté le serveur indiqué par un message dans le terminal du serveur. Dès que les 3 champs sont remplis, le bouton Connexion devient cliquable. Mais si certains champs sont incorrects, le fait de cliquer sur le bouton ne fait rien et un message d'erreur apparaît dans le terminal serveur. Une fois que toto a réussi à se connecter, son nom apparaît dans la liste des connectés et un message "toto vient de se connecter." apparaît dans la discussion. Toto peut maintenant envoyer des messages, et se déconnecter quand il veut quitter le tchat. Dans ce cas, son nom est retiré de la liste des connectés et un message "toto a quitté le tchat." apparaît dans la discussion. Si titi veut discuter avec toto, il ouvre une nouvelle fenêtre de tchat, se connecte et il peut maintenant envoyer des messages en son nom et voir ceux que toto à envoyés.

IV) Améliorations possibles

Le tchat implémente les fonctionnalités minimales attendues excepté le système des couleurs. En effet cela pose les problèmes suivants : 
- il ne peut y avoir qu'une seule couleur dans un JTextArea, en effet il faudrait pouvoir affecter une couleur différente à chaque ligne de texte et cela suppose un système de balises que n'implémente pas JTextArea, ou alors utiliser une autre fonctionnalité comme un champ graphique (un Graphics2D par exemple)
- comme la discussion est enregistrée dans un fichier txt, il faut pouvoir stocker l'association entre la couleur et le texte, on pourrait le résoudre en utilisant une classe Message et la sérialisation, mais lorsque la discussion est rechargée, dans l'hypothèse où un client associé à une couleur se déconnecte, il ne faut pas que la couleur puisse être attribuée à un autre client, ce qui pose un autre problème décrit ci-dessous
- à chaque connexion est attribuée une couleur différente, ce qui signifie qu'à partir d'un certain nombre de connectés il n'y aura plus assez de couleurs. Une solution serait de limiter le nombre de connectés. On pourrait aussi supprimer régulièrement la discussion pour ne garder que les couleurs relatives aux derniers connectés mais ce n'est pas très pratique pour suivre une discussion
- un tchat couleurs est pratique s'il n'y a que quelques personnes mais quand il y en a beaucoup on s'y perd vite
Il serait possible d'implémenter le système de couleurs mais cela nécessiterait de revoir une grande partie de la conception.

Aucun des bonus n'a été implémenté et il s'agit donc de directions possibles pour des améliorations. Le tchat se charge d'afficher l'heure d'envoi des messages et le pseudo des connectés ce qui permet déjà de les différencier. La taille des messages est limitée à 512 caractères (changeable dans le code), il est possible d'écrire sur autant de lignes qu'on veut et une ligne peut faire la taille que l'on veut, des barres de défilement permettent d'avoir l'intégralité des messages.

Le tchat devrait pouvoir permettre de communiquer sur plusieurs machines distantes, ce qui n'est pas possible ici vu que le fichier HistoriqueMessages.txt est stocké sur la machine locale ainsi que la liste des connectés. 

