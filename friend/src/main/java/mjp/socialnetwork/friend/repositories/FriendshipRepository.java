package mjp.socialnetwork.friend.repositories;

import mjp.socialnetwork.friend.model.Friendship;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface FriendshipRepository extends ReactiveCrudRepository<Friendship, Long> {
    Mono<Friendship> findBySecondUserIdAndId(String secondUserId,Long id);
}
