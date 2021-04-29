package mjp.socialnetwork.post.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjp.socialnetwork.post.model.Post;
import mjp.socialnetwork.post.model.dto.PostDTO;
import mjp.socialnetwork.post.services.PostService;
import org.modelmapper.ModelMapper;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Component
public class PostHandler {

    private final PostService postService;
    private final ModelMapper modelMapper;

    Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        log.debug(serverRequest.toString());
        return defaultReadResponse(
                this.postService
                        .findAll()
                        .map(post -> modelMapper.map(post, PostDTO.class))
        );
    }

    Mono<ServerResponse> findAllPrivate(ServerRequest serverRequest) {
        log.debug(serverRequest.toString());
        return defaultReadResponse(
                this.postService
                        .findAllPrivate(id(serverRequest), false)
                        .map(post -> modelMapper.map(post, PostDTO.class))
        );
    }

    Mono<ServerResponse> findById(ServerRequest serverRequest) {
        log.debug(serverRequest.toString());
        return defaultReadResponse(
                this.postService.findById(id(serverRequest))
                        .map(post -> modelMapper.map(post, PostDTO.class))
        );
    }

    Mono<ServerResponse> deleteById(ServerRequest serverRequest) {
        return defaultReadResponse(
                this.postService.delete(id(serverRequest))
                        .map(post -> modelMapper.map(post, PostDTO.class))
        );
    }

    Mono<ServerResponse> create(ServerRequest serverRequest) {
        return defaultReadResponse(
                this.postService
                        .create(modelMapper.map(serverRequest.bodyToMono(PostDTO.class), Post.class))
                        .map(post -> modelMapper.map(post, PostDTO.class)));
    }

    Mono<ServerResponse> update(ServerRequest serverRequest) {
        return defaultReadResponse(
                this.postService
                        .update(modelMapper.map(serverRequest.bodyToMono(PostDTO.class), Post.class))
                        .map(post -> modelMapper.map(post, PostDTO.class)));
    }

    private static Mono<ServerResponse> defaultReadResponse(Publisher<PostDTO> domainePublisher) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(domainePublisher, PostDTO.class);
    }

    private static Long id(ServerRequest serverRequest) {
        return Long.parseUnsignedLong(serverRequest.pathVariable("id"));
    }
}
