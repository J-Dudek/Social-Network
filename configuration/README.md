# Module CONFIGURATION

Ce module a pour but de servir les fichiers de configuration au applications [Friend](https://github.com/J-Dudek/Social-Network/tree/readme/friend) et [Post](https://github.com/J-Dudek/Social-Network/tree/readme/post).

Il récupère les fichiers de configuration dans le dossier [Configs](https://github.com/J-Dudek/Social-Network/tree/main/configs) ce qui permet de ne pas avoir à re-builder chaque application lors d'une modification de properties.

Chaque application possède  un ```application.properties``` de ```dev``` ainsi q'un de ```prod```. Le fichier ```application.properties``` contient les informations communes aux applications.

Encore une fois, afin que chaque utilisateur ayant cloner le repo puisse l'utiliser nous avons fait le choix de ne pas mettre de repo prové avec des clès d'authentification mais dans le cas d'un livrable pour un client la sécurité serait gérée autrement.
