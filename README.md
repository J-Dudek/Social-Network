# Projet Social-Network

Bienvenue sur le projet Social Network réalisé par Pierre Darcas, Julien Dudek et Morgan Lombard pour les cours de **programmation par composant (S2)** et **programmation fonctionnelle (S2)** du Master 1 III de la FGES.

Pour le détail des modules de l'architecture micro service mise en place pour le cours de programmation par composant, vous pouvez vou référez à ce fichier pour une présentation de l'architecture générale puis aux README.md présents dans chaque module pour une présentation plus spécifique.

**Liste des modules :**
- [Module Gateway](https://github.com/J-Dudek/Social-Network/tree/readme/gateway)
- [Module friend](https://github.com/J-Dudek/Social-Network/tree/readme/friend)
- [Module post](https://github.com/J-Dudek/Social-Network/tree/readme/post)
- [Module admin](https://github.com/J-Dudek/Social-Network/tree/readme/admin)
- [Module configuration](https://github.com/J-Dudek/Social-Network/tree/readme/configuration)
- [Scripts base de données](https://github.com/J-Dudek/Social-Network/tree/readme/db)

Pour le project d'application front-end développé à l'aide de React pour le cours de programmation fonctionnelle vous trouverez le README détaillé du projet ici : 

- [Application front-end](https://github.com/J-Dudek/Social-Network/tree/readme/social-network)

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
![Micro-service architecture](https://github.com/J-Dudek/Social-Network/blob/readme/doc/archi_microservices.png)

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

