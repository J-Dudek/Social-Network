package mjp.socialnetwork.friend.services;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.model.Friendship;
import mjp.socialnetwork.friend.model.User;
import mjp.socialnetwork.friend.model.dto.FriendshipDTO;
import mjp.socialnetwork.friend.model.dto.UserDTO;
import mjp.socialnetwork.friend.repositories.FriendshipRepository;
import mjp.socialnetwork.friend.repositories.UserRepository;
import mjp.socialnetwork.friend.utils.FriendshipMapper;
import mjp.socialnetwork.friend.utils.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.security.Principal;
import java.time.LocalDateTime;


@Transactional
@AllArgsConstructor
@Service
public class FriendshipService {

    private final ModelMapper modelMapper;
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public Flux<User> getFriends(Principal principal) {
        Flux<User> hisRequests = friendshipRepository.findByFirstUserIdAndStatus(principal.getName(), true)
                .flatMap(friendship -> userRepository.findById(friendship.getSecondUserId()));
        Flux<User> theirRequests = friendshipRepository.findBySecondUserIdAndStatus(principal.getName(), true).
                flatMap(friendship -> userRepository.findById(friendship.getFirstUserId()));
        return hisRequests.mergeWith(theirRequests);
    }

    public Flux<User> getFriends(String userId) {
        Flux<User> hisRequests = friendshipRepository.findByFirstUserIdAndStatus(userId, true)
                .flatMap(friendship -> userRepository.findById(friendship.getSecondUserId()));
        Flux<User> theirRequests = friendshipRepository.findBySecondUserIdAndStatus(userId, true).
                flatMap(friendship -> userRepository.findById(friendship.getFirstUserId()));
        return hisRequests.mergeWith(theirRequests);
    }

    public Mono<Boolean> isFriend(Principal principal, String friendId) {
        return this.friendshipRepository.existsFriendshipByFirstUserIdAndSecondUserId(principal.getName(), friendId)
                .flatMap(isFriend -> {
                    if (Boolean.TRUE.equals(isFriend)) {
                        return Mono.just(true);
                    } else {
                        return this.friendshipRepository.existsFriendshipByFirstUserIdAndSecondUserId(friendId, principal.getName());
                    }
                });
    }

    /**
     * Recupere les invitations envoyées
     *
     * @param principal passé en requete implicitement
     * @return l'invitation en question
     */
    public Flux<Tuple2<UserDTO, FriendshipDTO>> findInvitationSent(Principal principal) {
        return friendshipRepository.findAllByFirstUserId(principal.getName()).filter(friendship -> !friendship.isStatus())
                    .flatMap(friendship -> Mono.zip(
                            this.userRepository.findById(friendship.getSecondUserId())
                                    .map(UserMapper::toDto),
                            Mono.just(friendship)
                                    .map(FriendshipMapper::toDto)
                            )
                    );
    }

    /**
     * Recupere les invitations que l'on a recus
     *
     * @param principal passé en requete implicitement
     * @return la liste des invitation acceptée ou en pending
     */
    public Flux<Tuple2<UserDTO, FriendshipDTO>> findInvitationReceived(Principal principal) {
        return friendshipRepository.findAllBySecondUserId(principal.getName()).filter(friendship -> !friendship.isStatus())
                .flatMap(friendship -> Mono.zip(
                        this.userRepository.findById(friendship.getFirstUserId())
                                .map(UserMapper::toDto),
                        Mono.just(friendship)
                                .map(FriendshipMapper::toDto)
                        )
                );
    }

    /**
     * Permet de créer une invitation
     *
     * @param principal   passé en requete implicitement
     * @param idNewFriend l'id de la personne demandée en amis
     * @return L'invitation créée
     */
    public Mono<Friendship> createInvitation(Principal principal, String idNewFriend) {

        return friendshipRepository.save(
                Friendship.builder()
                        .friendshipDate(LocalDateTime.now()).status(false)
                        .isNew(true)
                        .firstUserId(principal.getName())
                        .secondUserId(idNewFriend).build());
    }

    /**
     * Cré le liens d'mmitié entre deux être :)
     *
     * @param principal    passé en requete implicitement
     * @param idInvitation une invitation emise mais ni acceptée ni rejetée
     * @return Relayion créée avec passage status true et date insérée
     */
    public Mono<FriendshipDTO> acceptInvitation(Principal principal, Long idInvitation) {

        return this.friendshipRepository.findById(idInvitation)
                .doOnNext(friendship -> friendship.setStatus(true))
                .doOnNext(friendship -> friendship.setIsNew(false))
                .flatMap(this.friendshipRepository::save)
                .map(FriendshipMapper::toDto);
    }

    /**
     * service used by several endpoint to delete a friendship or a pending invitation
     * @param idInvitation une invitation emise mais ni acceptée ni rejetée
     * @return mono void
     */
    public Mono<Void> deleteInvitation(Long idInvitation) {
        return this.friendshipRepository.findById(idInvitation)
                .flatMap(this.friendshipRepository::delete);
    }

    /**
     * Compte les amis de l'user
     *
     * @param principal l'user concerné
     * @return Mono de Long resultat du count
     */
    public Mono<Long> howManyFriends(Principal principal) {

        return friendshipRepository.countFriendshipByStatusAndFirstUserIdOrSecondUserId(true, principal.getName(), principal.getName());
    }
}
