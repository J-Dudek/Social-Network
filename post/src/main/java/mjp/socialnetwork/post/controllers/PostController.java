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

    @GetMapping(path = "/all/{id}")
    public Flux<PostDTO> findAllByUserId(Principal principal, @PathVariable("id") String userId) {
        return this.postService.findAllByUserId(userId);
    }

    @PostMapping(path = "/all/private")
    public Flux<PostDTO> findAllPrivateByUserId(Principal principal, @RequestBody String userId) {
        return this.postService.findAllByUserIdAndPublic(userId, false);
    }

    @PostMapping(path = "/all/public")
    public Flux<PostDTO> findAllPublicByUserId(Principal principal, @RequestBody String userId) {
        return this.postService.findAllByUserIdAndPublic(userId, true);
    }

    @PostMapping(path = "/all/friends")
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

    @PutMapping("/update-status")
    public Mono<ResponseEntity<PostDTO>> toggleStatus(Principal principal, @RequestBody Long postId) {
        return this.postService.toggleStatus(postId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

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

    @GetMapping(path="/my")
    public Flux<PostDTO> my(Principal principal){
        return postService.findMyPosts(principal);
    }
}
