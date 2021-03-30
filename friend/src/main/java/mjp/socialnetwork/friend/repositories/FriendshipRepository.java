package mjp.socialnetwork.friend.repositories;

import mjp.socialnetwork.friend.model.Friendship;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FriendshipRepository extends ReactiveCrudRepository<Friendship, Long> {
}
