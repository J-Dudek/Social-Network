package mjp.socialnetwork.friend.repositories;

import mjp.socialnetwork.friend.model.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
    Mono<User> findById(String id);

    Flux<User> findUsersByUsernameLike(String username);
    Flux<User> findUsersByFirstNameLike(String firstName);
    Flux<User> findUsersByLastNameLike(String lastName);



}
