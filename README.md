# Social-Network
## Lancement applicatif
Pour lancer la BDD et son interface adminer sur le port 8888 faire un ```docker-compose up -d``` à la racine du projet.

Par default le profile utilisé sur les projets java est celui de dev.
## Deploiement
A chaque merge sur la branche main une nouvelle image des application est dockerizée puis pushée sur le repo. Le serveur fait un check des mises à jour toutes les 3 minutes.
