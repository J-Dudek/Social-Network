package mjp.socialnetwork.post.repositories;

import mjp.socialnetwork.post.model.Post;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PostRepository extends ReactiveCrudRepository<Post, Long> {

    @Query("select * from post where id=:id and is_public=:isPublic")
    Flux<Post> findPostByUserIdAndPublic(String id, boolean isPublic);
    Flux<Post> findAllByUserId(String userId);
}
