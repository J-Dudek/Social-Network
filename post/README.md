# Module POST

Gestion des utilisateurs ```user``` et des relations ```friendship```.

Au démarrage de l'application, quel que soit le profil (**DEV** ou **PROD**), le microservice va tenter de récupérer sa configuration auprès du [Module configuration](https://github.com/J-Dudek/Social-Network/tree/main/configuration). Nous avons fait le choix de faire une duplication des fichiers de configuration, ainsi si l'application de configuration n'est pas lancé ou rencontre un problème elle peut vivre avec ses fichiers embarqués.

L'application s'enregistre ensuite auprès du [Module Gateway](https://github.com/J-Dudek/Social-Network/tree/main/gateway).

Les scopes utilisés pour les différents endpoints sont les suivants :
```
POST_CREATE ("SCOPE_post:create"),
POST_UPDATE ("SCOPE_post:update"),
POST_DELETE ("SCOPE_post:delete"),
POST_SEARCH ("SCOPE_post:search");
```
Dans ces cas d'utilisations:

```java
@Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource()))
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers(HttpMethod.GET, "/posts/all/**").hasAuthority(Scope.POST_SEARCH.scope())
                        .pathMatchers(HttpMethod.OPTIONS, "/posts/all/**").hasAuthority(Scope.POST_SEARCH.scope())
                        .pathMatchers(HttpMethod.POST, "/posts/all/private").hasAuthority(Scope.POST_SEARCH.scope())
                        .pathMatchers(HttpMethod.OPTIONS, "/posts/all/private").hasAuthority(Scope.POST_SEARCH.scope())
                        .pathMatchers(HttpMethod.POST, "/posts/all/private").hasAuthority(Scope.POST_SEARCH.scope())
                        .pathMatchers(HttpMethod.OPTIONS, "/posts/all/private").hasAuthority(Scope.POST_SEARCH.scope())
                        .pathMatchers(HttpMethod.POST, "/posts").hasAuthority(Scope.POST_CREATE.scope())
                        .pathMatchers(HttpMethod.GET, "/posts/my").hasAuthority(Scope.POST_SEARCH.scope())
                        .pathMatchers(HttpMethod.OPTIONS, "/posts/my").hasAuthority(Scope.POST_SEARCH.scope())
                        .pathMatchers(HttpMethod.GET, "/posts/**").hasAuthority(Scope.POST_SEARCH.scope())
                        .pathMatchers(HttpMethod.DELETE, "/posts/**").hasAuthority(Scope.POST_DELETE.scope())
                        .pathMatchers(HttpMethod.OPTIONS, "/posts/**").hasAuthority(Scope.POST_DELETE.scope())
                        .pathMatchers(HttpMethod.PUT, "/posts/update-status").hasAuthority(Scope.POST_UPDATE.scope())
                        .pathMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder())))
                .build();
    }
```

Au niveau des configurations des origins des requêtes, seules les requêtes provenant de l'application web sont acceptées :

```frontDomain``` correspond à l'url de l'application front (mise dans les ***properties***, permet donc un changement rapide en fonction de l'environnement choisi.
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

Comme expliqué dans le readme principal du repository, nous avons opté pour l'utilisation des flux ainsi que d'une base de données réactive. Nous avons fait le choix d'utiliser plusieurs types de retour sur nos différents endpoints afin d'implémenter et d'expérimenter différentes façons de retourner les données.

Ainsi, vous trouverez des endpoints retournant parfois des ```Mono<UserDTO>``` ou ```Flux<UserDTO>``` mais également des ```Mono<ResponseEntity<UserDTO>```.
