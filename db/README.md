# SCRIPTS DES BASES DE DONNÉES

Ce module comporte le dockerfile permettant de créer les bases de données basées sur [mariadb](https://mariadb.org/) en chargeant les scripts pour la génération des tables ainsi qu'un ensemble de données factices pour pouvoir tester l'interface.

2 bases sont ainsi générées :
- [Friend](https://github.com/J-Dudek/Social-Network/blob/main/db/sql/01-initFriend.sql) comportant les tables ```user``` et ```friendship```
- [Post](https://github.com/J-Dudek/Social-Network/blob/main/db/sql/03-initPost.sql) comportant la table ```post```

## Tables Base de données Friend
![bdd friend](https://github.com/J-Dudek/Social-Network/blob/main/doc/bdd_user.png)

## Tables Base de données Post
![bdd post](https://github.com/J-Dudek/Social-Network/blob/main/doc/bdd_post.png)
