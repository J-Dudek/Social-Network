package mjp.socialnetwork.post.controllers;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@AllArgsConstructor
@Configuration
public class PostEndpointConfiguration {

    private PostHandler postHandler;

    @Bean
    public RouterFunction<ServerResponse> highlevelRouter() {
        return RouterFunctions.route()
                .path("posts", this::routes)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route()
                .GET("all", this.postHandler::findAll)
                .GET("all-private", this.postHandler::findAllPrivate)
                .GET("/{id}", this.postHandler::findById)
                .DELETE("/{id}", this.postHandler::deleteById)
                .POST(this.postHandler::create)
                .PUT(this.postHandler::update)
                .build();
    }
}
