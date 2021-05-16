# Module CONFIGURATION

Ce module a pour but de servir les fichiers de configuration aux micro-services [Friend](https://github.com/J-Dudek/Social-Network/tree/readme/friend) et [Post](https://github.com/J-Dudek/Social-Network/tree/readme/post).

Il récupère les fichiers de configuration dans le dossier [Configs](https://github.com/J-Dudek/Social-Network/tree/main/configs) ce qui permet de ne pas avoir à re-builder chaque application lors d'une modification des properties.

Chaque application possède un fichier ```application.properties``` pour  **DEV** ainsi qu'un pour **PROD**. Le fichier ```application.properties``` contient les informations communes aux applications.

Encore une fois, afin que chaque utilisateur ayant cloné le repository puisse l'utiliser nous avons fait le choix de ne pas mettre de repository approuvé avec des clés d'authentification mais dans le cas d'un livrable pour un client la sécurité serait gérée autrement.
