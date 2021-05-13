package mjp.socialnetwork.friend.controllers;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.model.dto.FriendshipDTO;
import mjp.socialnetwork.friend.model.dto.UserDTO;
import mjp.socialnetwork.friend.services.FriendshipService;
import mjp.socialnetwork.friend.services.UserService;
import mjp.socialnetwork.friend.utils.FriendshipMapper;
import mjp.socialnetwork.friend.utils.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/friends/friendship")
public class FriendshipController {

    private final FriendshipService friendshipService;
    private final UserService userService;


    /**
     * Permet d'obtenir toutes les relations existantes de l'user
     *
     * @param principal passé par token
     * @return la liste des users
     */
    @GetMapping(path = "/friends")
    public Flux<UserDTO> getMyFriends(Principal principal) {
        return friendshipService.getFriends(principal).map(UserMapper::toDto);
    }

    @GetMapping(path = "/{id}")
    public Flux<UserDTO> getUserFriends(@PathVariable("id") String userId) {
        return this.friendshipService.getFriends(userId)
                .map(UserMapper::toDto);
    }

    /**
     * @param principal passé dans le token
     * @return la liste des invitations envoyés et non traitées
     */
    @GetMapping(path = "/mysent")
    public Flux<Tuple2<UserDTO, FriendshipDTO>> getMySent(Principal principal) {
        return friendshipService.findInvitationSent(principal);
    }

    /**
     * @param principal passé dans le token
     * @return la liste des invitations reçus en attente d'une action
     */
    @GetMapping(path = "/myreceived")
    public Flux<Tuple2<UserDTO, FriendshipDTO>> getMyReceived(Principal principal) {
        return friendshipService.findInvitationReceived(principal);
    }

    /**
     * Permet de créer une invitation
     *
     * @param principal passé dans le token
     * @param idUser    l'id de l'user invité
     * @return l'invitation en question
     */
    @PostMapping(path = "/sendInvit")
    public Mono<FriendshipDTO> createInvitation(Principal principal, @RequestBody String idUser) {
        return friendshipService.createInvitation(principal, idUser);
    }

    @GetMapping(path = "/verify/{id}")
    public Mono<Boolean> verifyFriendship(Principal principal, @PathVariable("id") String friendId) {
        return this.friendshipService.isFriend(principal, friendId);
    }

    /**
     * Acceptation de l'invitation. création du lien en base
     *
     * @param principal passé dans le token
     * @param idInvit   l'invitation a accepter
     * @return la relation etablie
     */
    @PutMapping(path = "/accept")
    public Mono<FriendshipDTO> acceptInvitation(Principal principal, @RequestBody Long idInvit) {
        return friendshipService.acceptInvitation(principal, idInvit);
    }

    /**
     * Annulation d'une invitation envoyée.
     *
     * @param principal passé dans le token,correspond à celui qui a fait l'invitation
     * @param idInvit   l'invitation à annuler
     * @return mono void
     */
    @PutMapping(path = "/cancel")
    public Mono<ResponseEntity<Void>> cancelInvitation(Principal principal, @RequestBody Long idInvit) {
        return this.friendshipService.deleteInvitation(idInvit)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    /**
     * Refu d'une invitation recus
     * @param principal l'utilisateur
     * @param idInvit id de l'invitation à supprimer
     * @return la suppression
     */
    @DeleteMapping(path = "/reject/{idInvit}")
    public Mono<ResponseEntity<Void>> rejectInvitation(Principal principal, @PathVariable("idInvit") Long idInvit) {
        return this.friendshipService.deleteInvitation(idInvit)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    /**
     * Concerne l'annulation des invitations non acceptées
     * @param principal passé dans le token, correspond à l'user
     * @param idInvit l'invitation concernée
     * @return mono void
     */
    @PutMapping(path = "/delete")
    public Mono<ResponseEntity<Void>> deleteRelation(Principal principal, @RequestBody Long idInvit) {
        return this.friendshipService.deleteInvitation(idInvit)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    /**
     * Quand un amis supprime sa relation avec un autre
     * @param principal le detenteur du compte
     * @param secondUserId l'id à supprimer
     * @return la relation supprimée
     */
    @DeleteMapping(path="/deleteFriend/{secondUserId}")
    public Mono<FriendshipDTO> deleteRelation(Principal principal,@PathVariable("secondUserId") String secondUserId){
        return friendshipService.deleteRelation(principal,secondUserId);
    }

    @GetMapping(path = "/howManyFriends")
    public Mono<Long> howManyFriends(Principal principal) {
        return friendshipService.howManyFriends(principal);
    }
}
