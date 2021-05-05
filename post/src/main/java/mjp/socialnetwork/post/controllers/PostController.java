package mjp.socialnetwork.post.controllers;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.post.model.dto.PostDTO;
import mjp.socialnetwork.post.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final PostService postService;
    private final PostDTO postDTO;

    @GetMapping(path = "/all")
    public Flux<PostDTO> findAllPosts(){
        try{
            return this.postService.findAllPosts();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
