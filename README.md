# Projet Social-Network

Bienvenue sur le projet Social Network réalisé par Pierre Darcas, Julien Dudek et Morgan Lombard pour les cours de programmation par composant (S2) et programmation fonctionnelle (S2) du Master 1 III de la FGES.

Pour le détail des modules de l'architecture micro service mise en place pour le cours de programmation par composant, vous pouvez vou référez à ce fichier pour une présentation de l'architecture générale puis aux README.md présents dans chaque module pour une présentation plus spécifique.

Liste des modules :

Pour le project d'application front-end développé à l'aide de React pour le cours de programmation fonctionnelle vous trouverez le README détaillé du projet ici : 

## Pile technique
### Back-end
Le projet a été développé à l'aide des technologies suivantes :
- le langage Java
- Le framework Springboot profiter de l'inversion de contrôle, l'injection de dépendances et la faciliter de paramétrage.
- Spring cloud pour concevoir une architecture micro-service
- Spring Actuator pour accéder à une interface de monitoring des micro-services
- Spring Reactor pour utiliser le paradigme de programmation réactive
- Spring Webflux pour remplacer l'API Servlet et créer des endpoints compatibles avec Reactor
- Spring Security pour sécuriser les routes en fonctions des rôles des utilisateurs
- OAuth2 pour gérer l'identification des utilisateurs

### Base de données
- MariaDB pour créer des bases de données relationnelles
- R2DBC pour implémenter un driver compatible entre une application réactive et une base de données relationnelle

### Front End
- Le langage Typescript
- La bibliothèque React
- Semantic UI afin de profiter de composants et de classes css préformatées
- Axios pour faciliter la gestion des requêtes http vers l'API

## Architecture
schéma en cours...


## Lancement applicatif
Afin de générer la base de données et de profiter de l'interface Adminer pour l'administrer entrez cette commande à kla racine du projet:
```docker-compose up -d```
Vous accèderez à l'interface à cette adresse http://localhost:8888
Utilisez les identifiants suivants :
username ```m1s2TpProg```
password ```l@Cath0M1s2```
il n'est pas nécessaire de remplir le champ ```database```

Par default le profil utilisé sur les projets java est ** DEV **.

## Deploiement
A chaque merge sur la branche main une nouvelle image des application est dockerizée puis pushée sur le repo. Le serveur fait un check des mises à jour toutes les 3 minutes.
