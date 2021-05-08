package mjp.socialnetwork.post.controllers;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.post.model.dto.PostDTO;
import mjp.socialnetwork.post.model.dto.UserDTO;
import mjp.socialnetwork.post.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/posts")
public class PostController {

    private final PostService postService;

    @GetMapping(path = "/all")
    public Flux<PostDTO> findAll() {
        return this.postService.findAll();
    }

    @GetMapping(path = "/all/private/{id}")
    public Flux<PostDTO> findAllPrivate(Principal principal, @PathVariable("id") Long id) {
        return this.postService.findAllPrivate(id);
    }

    @GetMapping(path = "/all/friends")
    public Flux<PostDTO> findAllFriendsPosts(Principal principal, @RequestBody Flux<UserDTO> userDTOFlux) {
        return this.postService.findAllFriendsPosts(userDTOFlux);
    }

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<PostDTO>> findById(@PathVariable("id") Long id) {
        return this.postService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(Principal principal, @PathVariable("id") Long id) {
        return this.postService.delete(principal, id)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    @PostMapping
    public Mono<ResponseEntity<PostDTO>> create(Principal principal, @RequestBody Mono<PostDTO> postDTOMono) {
        return this.postService.create(principal, postDTOMono)
                .map(ResponseEntity::ok);
    }

    @PutMapping(path = "/{id}")
    public Mono<ResponseEntity<PostDTO>> update(@PathVariable("id") Long id, @RequestBody Mono<PostDTO> postDTOMono) {
        return this.postService.update(id, postDTOMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
