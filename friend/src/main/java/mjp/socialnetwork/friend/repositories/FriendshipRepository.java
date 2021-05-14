package mjp.socialnetwork.friend.repositories;

import mjp.socialnetwork.friend.model.Friendship;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FriendshipRepository extends ReactiveCrudRepository<Friendship, Long> {

    Mono<Friendship> findBySecondUserIdAndId(String secondUserId,Long id);

    Flux<Friendship> findAllByFirstUserId(String firstUserId);

    Flux<Friendship> findAllBySecondUserId(String secondUserId);

    Mono<Friendship> findByFirstUserIdAndId(String firstUserId,Long Id);

    Mono<Friendship> findByFirstUserIdAndSecondUserId(String firstUserId,String secondUserId);

    Mono<Boolean> existsFriendshipByFirstUserIdAndSecondUserId(String firstUserId,String secondUserId);
    Mono<Boolean> existsFriendshipByFirstUserIdAndSecondUserIdAndStatus(String firstUserId,String secondUserId,Boolean status);

    Flux<Friendship> findByFirstUserIdAndStatus(String firstUserId,Boolean status);
    Flux<Friendship> findBySecondUserIdAndStatus(String secondUserId,Boolean status);

    Mono<Long> countFriendshipByStatusAndFirstUserIdOrSecondUserId(Boolean status,String firstUserId,String secondUserId);
}
