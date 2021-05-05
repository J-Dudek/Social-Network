package mjp.socialnetwork.post.services;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.post.model.Post;
import mjp.socialnetwork.post.model.dto.PostDTO;
import mjp.socialnetwork.post.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Transactional
@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    //TODO DELETE just for test
    public Flux<PostDTO> findAllPosts() { return postRepository.findAll().map(this::postToDTO); }


    public Flux<Post> findAll() {
        return postRepository.findAll();
    }

    public Flux<Post> findAllPrivate(long id, boolean isPublic) {
        return this.postRepository.findPostByIdAndPublic(id, isPublic);
    }

    public Mono<Post> findById(Long id) {
        return this.postRepository.findById(id);
    }

    public Mono<Post> delete(Long id) {
        return this.postRepository
                .findById(id)
                .flatMap(
                        post -> this.postRepository
                                .deleteById(post.getId())
                                .thenReturn(post)
                );
    }

    public Mono<Post> update(Post newPost) {
        return this.postRepository
                .findById(newPost.getId())
                .map(oldPost -> {
                    oldPost.setMessage(newPost.getMessage());
                    oldPost.setPublic(newPost.isPublic());
                    return oldPost;
                })
                .flatMap(this.postRepository::save);
    }

    public Mono<Post> create(Post post) {
        return this.postRepository
                .save(new Post(null, post.getMessage(), Timestamp.valueOf(LocalDateTime.now()), post.isPublic(), post.getUserId()));
    }

    /**
     * permet de convertir user en DTO
     * @param post
     * @return
     */
    public PostDTO postToDTO(Post post){
        return modelMapper.map(post,PostDTO.class);
    }


}
