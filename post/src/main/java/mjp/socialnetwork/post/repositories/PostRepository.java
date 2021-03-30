package mjp.socialnetwork.post.repositories;

import mjp.socialnetwork.post.model.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostRepository extends ReactiveCrudRepository<Post, Long> {
}
