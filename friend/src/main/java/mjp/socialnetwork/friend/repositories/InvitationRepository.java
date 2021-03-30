package mjp.socialnetwork.friend.repositories;

import mjp.socialnetwork.friend.model.Invitation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface InvitationRepository extends ReactiveCrudRepository<Invitation, Long> {
}
