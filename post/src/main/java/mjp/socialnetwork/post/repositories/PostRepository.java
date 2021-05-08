package mjp.socialnetwork.post.repositories;

import mjp.socialnetwork.post.model.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PostRepository extends ReactiveCrudRepository<Post, Long> {

    Flux<Post> findPostByIdAndPublic(Long id, boolean isPublic);
    Flux<Post> findAllByUserId(String userId);
}
