package mjp.socialnetwork.friend.services;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.model.Friendship;
import mjp.socialnetwork.friend.model.User;
import mjp.socialnetwork.friend.repositories.FriendshipRepository;
import mjp.socialnetwork.friend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.time.LocalDateTime;


@Transactional
@AllArgsConstructor
@Service
public class FriendshipService {

    private final ModelMapper modelMapper;
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public Flux<User> getFriends(Principal principal){
        Flux<User> hisRequests = friendshipRepository.findByFirstUserIdAndStatus(principal.getName(), true)
                .flatMap(friendship -> userRepository.findById(friendship.getSecondUserId()));
        Flux<User> theirRequests = friendshipRepository.findBySecondUserIdAndStatus(principal.getName(), true).
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
    public Flux<Friendship> findInvitationSent(Principal principal) {
        return friendshipRepository.findAllByFirstUserId(principal.getName());
    }

    /**
     * Recupere les invitations que l'on a recus
     *
     * @param principal passé en requete implicitement
     * @return la liste des invitation acceptée ou en pending
     */
    public Flux<Friendship> findInvitationReceived(Principal principal) {
        return friendshipRepository.findAllBySecondUserId(principal.getName());
    }

    /**
     * Permet de créer une invitation
     *
     * @param principal   passé en requete implicitement
     * @param idNewFriend l'id de la personne demandée en amis
     * @return L'invitation créée
     */
    public Mono<Friendship> createInvitation(Principal principal, String idNewFriend) {
        Friendship.FriendshipBuilder builder = Friendship.builder();
        builder.firstUserId(principal.getName());
        builder.secondUserId(idNewFriend);
        builder.friendshipDate(LocalDateTime.now());
        builder.status(false);
        builder.newFriendShip(true);
        Friendship friendship;
        friendship = builder
                .build();
        return friendshipRepository.save(friendship);
    }

    /**
     * Cré le liens d'mmitié entre deux être :)
     * @param principal passé en requete implicitement
     * @param idInvitation une invitation emise mais ni acceptée ni rejetée
     * @return Relayion créée avec passage status true et date insérée
     */
    public Mono<Friendship> acceptInvitation(Principal principal, Long idInvitation) {
        Friendship friendship;
        friendship = Friendship.builder().
                id(idInvitation)
                .secondUserId(principal.getName())
                .friendshipDate(LocalDateTime.now())
                .status(true)
                .build();
        return friendshipRepository.save(friendship);
    }

    /**
     * Annule une invitation envoyée
     *
     * @param principal    passé en requete implicitement
     * @param idInvitation une invitation emise mais ni acceptée ni rejetée
     * @return la suppression de l'invitation
     */
    public Mono<Friendship> cancelPendingInvitation(Principal principal, Long idInvitation) {
        return friendshipRepository.findByFirstUserIdAndId(principal.getName(), idInvitation).flatMap(
                invit -> {
                    assert invit.getId() != null;
                    return this.friendshipRepository
                            .deleteById(invit.getId())
                            .thenReturn(invit);
                }
        );
    }

    /**
     * Rejet d'une invitation reçus, provoque un delete
     *
     * @param principal    passé en requete implicitement
     * @param idInvitation une invitation emise mais ni acceptée ni rejetée
     * @return l'invitation supprimée après rejet
     */
    public Mono<Friendship> rejectInvitation(Principal principal, Long idInvitation) {
        return this.friendshipRepository
                .findBySecondUserIdAndId(principal.getName(), idInvitation)
                .flatMap(
                        invit -> {
                            assert invit.getId() != null;
                            return this.friendshipRepository
                                    .deleteById(invit.getId())
                                    .thenReturn(invit);
                        }
                );
    }

    /**
     * Quand il y a rupture entre 2 amis :'(
     *
     * @param principal    passé en requete implicitement
     * @param secondUserId l'id du second user de la relation
     * @return la relation supprimé en BDD. ils ne sont plus Friendship
     */
    public Mono<Friendship> deleteRelation(Principal principal, String secondUserId) {
        return friendshipRepository.existsFriendshipByFirstUserIdAndSecondUserId(principal.getName(), secondUserId)
                .flatMap(aBoolean -> {
                    if (Boolean.FALSE.equals(aBoolean))
                        return friendshipRepository.findByFirstUserIdAndSecondUserId(secondUserId, principal.getName())
                                .flatMap(invit -> {
                                    assert invit.getId() != null;
                                    return this.friendshipRepository.deleteById(invit.getId()).thenReturn(invit);
                                });
                    else {
                        return friendshipRepository.findByFirstUserIdAndSecondUserId(principal.getName(), secondUserId)
                                .flatMap(invit -> {
                                    assert invit.getId() != null;
                                    return this.friendshipRepository.deleteById(invit.getId()).thenReturn(invit);
                                });
                    }

                });

    }

    /**
     * Compte les amis de l'user
     * @param principal l'user concerné
     * @return Mono de Long resultat du count
     */
    public Mono<Long> howManyFriends(Principal principal){

        return friendshipRepository.countFriendshipByStatusAndFirstUserIdOrSecondUserId(true, principal.getName(), principal.getName());
    }
}
