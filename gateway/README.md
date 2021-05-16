# Module GATEWAY

Ce module sert de passerelle pour contacter les micro-services Friend et Post.

---

Elle a comme particularité [cette classe ](/gateway/src/main/java/mjp/socialnetwork/gateway/DedupeResponseHeaderGatewayFilterFactory.java) qui permet d'éviter l'erreur de multiples ```cors origins``` tout en passant le token applicatif dans le header de la requête.


