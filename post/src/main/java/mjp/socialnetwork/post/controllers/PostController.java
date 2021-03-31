package mjp.socialnetwork.post.controllers;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.post.exceptions.PostException;
import mjp.socialnetwork.post.model.dto.PostDTO;
import mjp.socialnetwork.post.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/posts")
public class PostController {

    private final PostService postService;

    @GetMapping(path = "/all")
    public Flux<PostDTO> findAllPosts() {
        try {
            return this.postService.findAllPosts();
        } catch (
                PostException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
