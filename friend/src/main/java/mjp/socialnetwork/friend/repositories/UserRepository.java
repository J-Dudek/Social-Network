package mjp.socialnetwork.friend.repositories;

import mjp.socialnetwork.friend.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
