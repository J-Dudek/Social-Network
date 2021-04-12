package mjp.socialnetwork.friend.repositories;

import mjp.socialnetwork.friend.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
    @Query("SELECT * FROM user WHERE last_name = :lastname")
    Flux<User> findByLastName(String lastName);
}
