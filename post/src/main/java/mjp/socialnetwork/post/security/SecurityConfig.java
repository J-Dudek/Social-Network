package mjp.socialnetwork.post.security;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig  {
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
                        .pathMatchers(  HttpMethod.GET,"/posts/all/**").hasAuthority(Scope.POST_SEARCH.scope())
                        .pathMatchers(  HttpMethod.OPTIONS,"/posts/all/**").hasAuthority(Scope.POST_SEARCH.scope())
<<<<<<< refs/remotes/origin/postcard
                        .pathMatchers(  HttpMethod.POST,"/posts").hasAuthority(Scope.POST_CREATE.scope())
=======
                        .pathMatchers(  HttpMethod.GET,"/posts/my").hasAuthority(Scope.POST_SEARCH.scope())
                        .pathMatchers(  HttpMethod.OPTIONS,"/posts/my").hasAuthority(Scope.POST_SEARCH.scope())
<<<<<<< refs/remotes/origin/postcard
>>>>>>> #4 findMyPosts
=======
                        .pathMatchers(  HttpMethod.DELETE,"/posts/**").hasAuthority(Scope.POST_DELETE.scope())
                        .pathMatchers(  HttpMethod.OPTIONS,"/posts/**").hasAuthority(Scope.POST_DELETE.scope())
>>>>>>> 💩  PersonnalPost
                        .pathMatchers( HttpMethod.GET, "/actuator/**").permitAll()
                        .pathMatchers( HttpMethod.GET, "/actuator/**").permitAll()
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