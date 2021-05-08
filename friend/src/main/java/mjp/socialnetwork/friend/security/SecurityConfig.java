package mjp.socialnetwork.friend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebFluxSecurity
@EnableTransactionManagement
public class SecurityConfig {

    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;
    @Value("${deploy.domain}")
    private String frontDomain;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors(corsSpec -> corsSpec.configurationSource(corsConfigurationSource()))
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers( "/actuator/**").permitAll()
                        .pathMatchers( HttpMethod.OPTIONS,"/friends/users/aboutUser").hasAuthority(Scope.USER_READ.scope())
                        .pathMatchers( HttpMethod.GET,"/friends/users/aboutUser").hasAuthority(Scope.USER_READ.scope())
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
                        .pathMatchers(HttpMethod.DELETE,"/friends/friendship/delete").hasAuthority(Scope.FRIEND_DELETE.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/delete").hasAuthority(Scope.FRIEND_DELETE.scope())
                        .pathMatchers(HttpMethod.GET,"/friends/friendship/friends").hasAuthority(Scope.FRIEND_READ.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/friends").hasAuthority(Scope.FRIEND_READ.scope())
                        .pathMatchers(HttpMethod.GET,"/friends/friendship/myreceived").hasAuthority(Scope.FRIEND_READ.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/myreceived").hasAuthority(Scope.FRIEND_READ.scope())
                        .pathMatchers(HttpMethod.GET,"/friends/friendship/mysent").hasAuthority(Scope.FRIEND_READ.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/mysent").hasAuthority(Scope.FRIEND_READ.scope())
                        .pathMatchers(HttpMethod.PUT,"/friends/friendship/reject").hasAuthority(Scope.FRIEND_DELETE.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/reject").hasAuthority(Scope.FRIEND_DELETE.scope())
                        .pathMatchers(HttpMethod.POST,"/friends/friendship/sendInvit").hasAuthority(Scope.FRIEND_SEARCH.scope())
                        .pathMatchers(HttpMethod.OPTIONS,"/friends/friendship/sendInvit").hasAuthority(Scope.FRIEND_SEARCH.scope())
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder())))
                .build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(frontDomain));
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "DELETE", "POST", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(Collections.singletonList("authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    ReactiveJwtDecoder jwtDecoder() {
        NimbusReactiveJwtDecoder jwtDecoder = (NimbusReactiveJwtDecoder) ReactiveJwtDecoders.fromIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);
        jwtDecoder.setClaimSetConverter(new PermissionFilter());

        return jwtDecoder;
    }
}