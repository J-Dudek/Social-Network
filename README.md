# Projet Social-Network

Bienvenue sur le projet Social Network réalisé par Pierre Darcas, Julien Dudek et Morgan Lombard pour les cours de **programmation par composant (S2)** et **programmation fonctionnelle (S2)** du Master 1 III de la FGES.

Pour le détail des modules de l'architecture micro service mise en place pour le cours de programmation par composant, vous pouvez vou référez à ce fichier pour une présentation de l'architecture générale puis aux README.md présents dans chaque module pour une présentation plus spécifique.

**Liste des modules :**
- [Module Gateway](https://github.com/J-Dudek/Social-Network/tree/main/gateway)
- [Module friend](https://github.com/J-Dudek/Social-Network/tree/main/friend)
- [Module post](https://github.com/J-Dudek/Social-Network/tree/main/post)
- [Module admin](https://github.com/J-Dudek/Social-Network/tree/main/admin)
- [Module configuration](https://github.com/J-Dudek/Social-Network/tree/main/configuration)
- [Scripts base de données](https://github.com/J-Dudek/Social-Network/tree/main/db)

Pour le project d'application front-end développé à l'aide de React pour le cours de programmation fonctionnelle vous trouverez le README détaillé du projet ici : 

- [Application front-end](https://github.com/J-Dudek/Social-Network/tree/main/social-network)

## Pile technique
> ### Back-end
> Le projet a été développé à l'aide des technologies suivantes :
> - le langage Java
> - Le framework Springboot profiter de l'inversion de contrôle, l'injection de dépendances et la faciliter de paramétrage.
> - Spring cloud pour concevoir une architecture micro-service
> - Spring Actuator pour accéder à une interface de monitoring des micro-services
> - Spring Reactor pour utiliser le paradigme de programmation réactive
> - Spring Webflux pour remplacer l'API Servlet et créer des endpoints compatibles avec Reactor
> - Spring Security pour sécuriser les routes en fonctions des rôles des utilisateurs
> - OAuth2 pour gérer l'identification des utilisateurs

> ### Base de données
> - MariaDB pour créer des bases de données relationnelles
> - R2DBC pour implémenter un driver compatible entre une application réactive et une base de données relationnelle

> ### Front End
> - Le langage Typescript
> - La bibliothèque React
> - Semantic UI afin de profiter de composants et de classes css préformatées
> - Axios pour faciliter la gestion des requêtes http vers l'API

## Architecture
![Micro-service architecture](https://github.com/J-Dudek/Social-Network/blob/main/doc/archi_microservices.png)

## Déploiement de la base de données
Afin de générer la base de données et de profiter de l'interface Adminer pour l'administrer entrez cette commande à la racine du projet:  
```docker-compose up -d```  
Vous pourrez accéder à l'interface à cette adresse http://localhost:8888  


Utilisez les identifiants suivants :  

username ```m1s2TpProg```  
password ```l@Cath0M1s2```  

Il n'est pas nécessaire de remplir le champ ```database```  

Par default le profil utilisé sur les projets java est **DEV**.  

## Devops
Nous avons décidé d'utiliser [CircleCI](https://circleci.com/) afin de gérer l'intégration continue.

- Concernant les modules Java à chaque commit sur une branche quelconque une verification du build de l'application complète est faite en utilisant le profil **DEV**. Lors d'un merge ou un commit sur la branche ```main``` à la suite de l'étape de vérification de build, une image docker est poussée vers un repository [docker](https://www.docker.com/) en utilisant le profil **PROD**.
- Concernant le module embarquant la base de donnée, la mise à jour de l'image docker est faite uniquement sur la branche ```main```.
- Concernant la partie front-end en React, L'image docker est poussée également uniquement sur la branche ```main```.

Pour la partie déploiement nous avons décidé d'utiliser un nas Synology afin d'être indépendant de tout autre structure. Cette étape a demandé plusieurs réglages au niveau du réseau afin que l'ouverture des flux soit effectives et que la sécurité soit préservée.
Un container [watchower](https://github.com/containrrr/watchtower) est paramétré afin de mettre à jour les containers de chaque application. Un ordre de lancement de ces containers est également défini.

## Sécurité Auth0
Afin de permettre à quiconque d'utiliser l'application nous avons décidé de paramétrer un compte qui sera utilisé uniquement pour celle-ci. C'est pour ces raisons que nous avons mis à disposition en clair les clés d'identification applicative.

En termes de sécurité, l'application front-end doit s'authentifier auprès d'auth0 afin d'obtenir un token lui permettant d'accéder à l'API back-end.
Lors de l'inscription d'un utilisateur sur auth0 nous avons défini une règle de gestion via leur interface permettant d'ajouter certaines informations au token.
Ainsi un utilisateur enregistré possède les scopes suivants :

```user:read user:update user:delete user:search post:create post:update post:delete post:search friend:read friend:update friend:delete friend:search friend:add```
Ce qui permet une meilleure granulométrie au niveau des filtres pour les endpoints.

L'application n'ayant pas d'interface d'administration nous n'avons pas créé de règle ou de scope pour cela mais la mise en place est assez simple au vu de la stack technique déjà implémentée.

## Détails des fonctionnalités

Une fois à l’adresse http://localhost:3000/, cliquez sur le bouton ‘Sign Up’ en haut à droite. Deux possibilités : 
- Se connecter avec son compte GitHub ;
- Renseigner un email et un mot de passe.

Cela déclenche un enregistrement Auth0 et l’attribution d’un token de session.

Vous arrivez ensuite sur un formulaire d’inscription à remplir pour pouvoir accéder au réseau social. Lorsque vous cliquez sur ‘save’ cela déclenche l’enregistrement en base de votre compte utilisateur. L'adresse mail saisie dans ce formulaire est l'adresse mail publique pour les autres utilisateurs, mais ne correspond pas à l'adresse mail pour l'authentification Auth0.

Une fois connecté vous arrivez alors sur votre home page. (Vide car vous n’avez pour le moment pas d’amis)

Cliquez sur le bouton ‘profile’ en haut à droite pour accéder à votre espace personnel.

Vous y trouverez une carte reprenant vos informations personnelles. Vous pouvez cliquer sur l’icône en haut à droite de la carte pour éditer ces informations.

Vous pouvez cliquer sur le bouton ‘Write a post’ en haut à gauche pour écrire un post. Un menu drop down vous permet de décider si votre post sera public (visible de tous les utilisateurs du réseau) ou privé (visible uniquement par vos amis). Vous aurez la possibilité de passer vos post de privé à public et inversement grâce au toggle en bas à droite des cartes post dans votre espace personnel.

Vous pouvez rechercher des utilisateurs du site grâce à la Search Bar à gauche. Pour cela rentrez le nom, le prénom ou le username du contact recherché.

Si vous cliquez sur l’étiquette sous la Search Bar vous arriverez sur l’espace personnel de l’utilisateur.

Pour effectuer une demande en ami cliquez sur l’icone avec un plus à droite de la carte profil de l’utilisateur. Si cet utilisateur est déjà votre amis, ce bouton sera remplacer par un bouton permettant de révoquer le lien d'amitié.

En cliquant à nouveau sur ‘Profile’, vous retournerez sur votre espace personnel et verrez votre invitation en attente sous l’encart ‘Sent invitation’.

Si vous recevez une invitation elle apparaitra sous l’encart ‘Received invitation’, vous pourrez alors accepter ou décliner.

En cliquant sur le logo de l’université en haut à gauche, vous arriverez sur votre home page et pourrez voir les posts de vos amis (public et privé).

