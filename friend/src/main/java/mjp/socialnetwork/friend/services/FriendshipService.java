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
        return this.friendshipRepository.existsFriendshipByFirstUserIdAndSecondUserIdAndStatus(principal.getName(), friendId,true)
                .flatMap(isFriend -> {
                    if (Boolean.TRUE.equals(isFriend)) {
                        return Mono.just(true);
                    } else {
                        return this.friendshipRepository.existsFriendshipByFirstUserIdAndSecondUserIdAndStatus(friendId, principal.getName(),true);
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
     *On verifie dans un premier temps si l'user invité a invité le premier, si oui on accepte et la return
     * Si non, on verifie si le même user a déjà fait une invitation, si oui on la return
     * Si non, on cré l'invitation et on la sauvegarde
     * @param principal   passé en requete implicitement
     * @param idNewFriend l'id de la personne demandée en amis
     * @return L'invitation créée
     */
    public Mono<FriendshipDTO> createInvitation(Principal principal, String idNewFriend) {
        return friendshipRepository.existsFriendshipByFirstUserIdAndSecondUserIdAndStatus(idNewFriend, principal.getName(), false)
                .flatMap(aBoolean -> {
                    if (aBoolean) {
                        return friendshipRepository.findByFirstUserIdAndSecondUserId(idNewFriend, principal.getName())
                                .flatMap(friendship -> {
                                    friendship.setIsNew(false);
                                    friendship.setStatus(true);
                                    return friendshipRepository.save(friendship).map(FriendshipMapper::toDto);
                                });
                    } else {
                        return friendshipRepository.existsFriendshipByFirstUserIdAndSecondUserIdAndStatus( principal.getName(),idNewFriend, false)
                                .flatMap(aBoolean1 -> {
                                    if(aBoolean1){
                                        return friendshipRepository.findByFirstUserIdAndSecondUserId(principal.getName(),idNewFriend).map(FriendshipMapper::toDto);
                                    }else{
                                        Friendship build = Friendship.builder()
                                                .friendshipDate(LocalDateTime.now())
                                                .status(false)
                                                .isNew(true)
                                                .firstUserId(principal.getName())
                                                .secondUserId(idNewFriend).build();
                                        return friendshipRepository.save(build).map(FriendshipMapper::toDto);
                                    }
                                });

                    }
                });

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
     * Quand il y a rupture entre 2 amis :'(
     * @param principal passé en requete implicitement
     * @param secondUserId l'id du second user de la relation
     * @return la relation supprimé en BDD. ils ne sont plus Friendship
     */
    public Mono<FriendshipDTO> deleteRelation(Principal principal, String secondUserId) {
        return friendshipRepository.existsFriendshipByFirstUserIdAndSecondUserId(principal.getName(), secondUserId)
                .flatMap(aBoolean -> {
                    if (!aBoolean)
                        return friendshipRepository.findByFirstUserIdAndSecondUserId(secondUserId, principal.getName())
                                .flatMap(invit -> {
                                    assert invit.getId() != null;
                                    return this.friendshipRepository.deleteById(invit.getId()).thenReturn(invit).map(FriendshipMapper::toDto);
                                });
                    else {
                        return friendshipRepository.findByFirstUserIdAndSecondUserId(principal.getName(), secondUserId)
                                .flatMap(invit -> {
                                    assert invit.getId() != null;
                                    return this.friendshipRepository.deleteById(invit.getId()).thenReturn(invit).map(FriendshipMapper::toDto);
                                });
                    }

                });

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
