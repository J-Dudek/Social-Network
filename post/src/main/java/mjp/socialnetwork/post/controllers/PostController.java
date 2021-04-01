package mjp.socialnetwork.post.controllers;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.post.exceptions.PostException;
import mjp.socialnetwork.post.model.dto.PostDTO;
import mjp.socialnetwork.post.repositories.PostRepository;
import mjp.socialnetwork.post.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@Transactional
@RequestMapping(path = "/posts")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping(path = "/all")
    public Flux<PostDTO> findAllPosts() {
        try {
            return this.postService.findAllPosts();
        } catch (
                PostException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/create")
    public Mono<PostDTO> createPost(@RequestBody PostDTO newPostDto) {

        try {
            return this.postService.createNewPost(newPostDto);
        } catch (PostException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}
