# Module FRIEND

Gestion des utilisateurs (user) et des relations (friendship).

Au demarrage de l'application, quelque soit le profil (dev ou prod), le microservices va tenter de récuperer sa configuration auprés du [Module configuration](https://github.com/J-Dudek/Social-Network/tree/readme/configuration) . Nous avons fait le choix de faire une duplication des fichiers de configuration, ainisi si l'application de configuration n'est pas lancé ou rencontre un problème elle peut vivre avec ses fichiers embarqués.

L'application s'enregistre ensuite auprès du [Module Gateway](https://github.com/J-Dudek/Social-Network/tree/readme/gateway).


Les scopes utilisés pour les différents endpoints sont les suivant:

```
USER_READ ("SCOPE_user:read"),
USER_UPDATE ("SCOPE_user:update"),
USER_DELETE ("SCOPE_user:delete"), 
USER_SEARCH ("SCOPE_user:search"),
FRIEND_READ ("SCOPE_friend:read"),
FRIEND_UPDATE ("SCOPE_friend:update"),
FRIEND_DELETE ("SCOPE_friend:delete"),
FRIEND_SEARCH ("SCOPE_friend:search"),
FRIEND_ADD ("SCOPE_friend:add");
```
Dans ces cas d'utilisations:
```java
@Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource()))
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers( "/actuator/**").permitAll()
                        .pathMatchers( HttpMethod.OPTIONS,"/friends/users/aboutUser").hasAuthority(Scope.USER_READ.scope())
                        .pathMatchers( HttpMethod.GET,"/friends/users/aboutUser").hasAuthority(Scope.USER_READ.scope())
                        .pathMatchers( HttpMethod.OPTIONS,"/friends/users/search").hasAuthority(Scope.USER_SEARCH.scope())
                        .pathMatchers( HttpMethod.POST,"/friends/users/search").hasAuthority(Scope.USER_SEARCH.scope())
                        .pathMatchers( HttpMethod.OPTIONS,"/friends/users/all").hasAuthority(Scope.USER_READ.scope())
                        .pathMatchers( HttpMethod.GET,"/friends/users/all").hasAuthority(Scope.USER_READ.scope())
                        .pathMatchers( HttpMethod.OPTIONS,"/friends/users/howManyFriends").hasAuthority(Scope.USER_READ.scope())
                        .pathMatchers( HttpMethod.GET,"/friends/users/howManyFriends").hasAuthority(Scope.USER_READ.scope())
                        .pathMatchers( HttpMethod.OPTIONS,"/friends/users/logOrsign").hasAuthority(Scope.USER_READ.scope())
                        .pathMatchers( HttpMethod.GET,"/friends/users/logOrsign").hasAuthority(Scope.USER_READ.scope())
                        .pathMatchers( HttpMethod.OPTIONS,"/friends/users/update").hasAuthority(Scope.USER_UPDATE.scope())
                        .pathMatchers( HttpMethod.PUT,"/friends/users/update").hasAuthority(Scope.USER_UPDATE.scope())
                        .pathMatchers(HttpMethod.PUT,"/friends/friendship/accept").hasAuthority(Scope.FRIEND_ADD.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/accept").hasAuthority(Scope.FRIEND_ADD.scope())
                        .pathMatchers(HttpMethod.PUT,"/friends/friendship/cancel").hasAuthority(Scope.FRIEND_UPDATE.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/cancel").hasAuthority(Scope.FRIEND_UPDATE.scope())
                        .pathMatchers(HttpMethod.PUT,"/friends/friendship/delete").hasAuthority(Scope.FRIEND_DELETE.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/delete").hasAuthority(Scope.FRIEND_DELETE.scope())
                        .pathMatchers(HttpMethod.DELETE,"/friends/friendship/deleteFriend/**").hasAuthority(Scope.FRIEND_DELETE.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/deleteFriend/**").hasAuthority(Scope.FRIEND_DELETE.scope())
                        .pathMatchers(HttpMethod.GET,"/friends/friendship/friends").hasAuthority(Scope.FRIEND_READ.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/friends").hasAuthority(Scope.FRIEND_READ.scope())
                        .pathMatchers(HttpMethod.GET,"/friends/friendship/myreceived").hasAuthority(Scope.FRIEND_READ.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/myreceived").hasAuthority(Scope.FRIEND_READ.scope())
                        .pathMatchers(HttpMethod.GET,"/friends/friendship/mysent").hasAuthority(Scope.FRIEND_READ.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/mysent").hasAuthority(Scope.FRIEND_READ.scope())
                        .pathMatchers(HttpMethod.DELETE,"/friends/friendship/reject").hasAuthority(Scope.FRIEND_DELETE.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/reject").hasAuthority(Scope.FRIEND_DELETE.scope())
                        .pathMatchers(HttpMethod.POST,"/friends/friendship/sendInvit").hasAuthority(Scope.FRIEND_SEARCH.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/sendInvit").hasAuthority(Scope.FRIEND_SEARCH.scope())
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder())))
                .build();
    }
```

Au niveau des configurations des origins des requêtes, seules les requêtes provenant de l'application web sont acceptées:

```frontDomain``` correspond à l'url de l'application front (mise dans les properties, permet donc un changement rapide en fonction de l'environement choisi. 

```java
CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(frontDomain));
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "DELETE", "POST", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(Collections.singletonList("authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
```

Comme dit dans le readme prinicap du repositorie, l'adoption des flux a été adopté ainsi que qu'une base de données réactive.
Nous avons fait le choix d'utiliser plusieurs type de retour sur nos différents endpoints afin d'implémenter différentes façon de retourner les donnnées.

Ainsi, vous trouverez des endpoints retournant parfois des  ```Monoy<UserDTO>>``` ou ```Fluxy<UserDTO>>``` mais également des ```Mono<ResponseEntity<UserDTO>>```.


