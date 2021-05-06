package mjp.socialnetwork.friend.controllers;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.model.dto.FriendshipDTO;
import mjp.socialnetwork.friend.services.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/friends/friendship")
public class FriendshipController {

    @Autowired
    private final FriendshipService friendshipService;


    /**
     * Permet d'obtenir toutes les relations existantes de l'user
     * @param principal passé par token
     * @return la liste des users
     */
    @GetMapping(path="/friends")
    public Flux<FriendshipDTO> getMyFriends(Principal principal){
        return friendshipService.getFriends(principal).map(friendshipService::userToDTO);
    }

    /**
     *
     * @param principal passé dans le token
     * @return la liste des invitations envoyés et non traitées
     */
    @GetMapping(path = "/mysent")
    public Flux<FriendshipDTO> getMySent(Principal principal){
        return friendshipService.findInvitationSent(principal).map(friendshipService::userToDTO);
    }

    /**
     *
     * @param principal passé dans le token
     * @return la liste des invitations reçus en attente d'une action
     */
    @GetMapping(path="/myreceived")
    public Flux<FriendshipDTO> getMyReceived(Principal principal){
        return friendshipService.findInvitationReceived(principal).map(friendshipService::userToDTO);
    }

    /**
     * Permet de créer une invitation
     * @param principal passé dans le token
     * @param idUser l'id de l'user invité
     * @return l'invitation en question
     */
    @PostMapping(path="/sendInvit")
    public Mono<FriendshipDTO> createInvitation(Principal principal, @RequestBody String idUser){
        return friendshipService.createInvitation(principal,idUser).map(friendshipService::userToDTO);
    }

    @GetMapping(path = "/verify/{id}")
    public Mono<Boolean> verifyFriendship(Principal principal, @PathVariable("id") String friendId) {
        return this.friendshipService.isFriend(principal, friendId);
    }

    /**
     * Acceptation de l'invitation. création du lien en base
     * @param principal passé dans le token
     * @param idInvit l'invitation a accepter
     * @return la relation etablie
     */
    @PutMapping(path="/accept")
    public Mono<FriendshipDTO> acceptInvitation(Principal principal, @RequestBody Long idInvit){
        return friendshipService.acceptInvitation(principal,idInvit).map(friendshipService::userToDTO);
    }

    /**
     * Annulation d'une invitation envoyée.
     * @param principal passé dans le token,corrsspond à celui qui a fait l'invitation
     * @param idInvit l'invitation à annuler
     * @return l'invitation supprimée.
     */
    @PutMapping(path="/cancel")
    public Mono<FriendshipDTO> cancelInvitation(Principal principal,@RequestBody Long idInvit){
        return friendshipService.cancelPendingInvitation(principal,idInvit).map(friendshipService::userToDTO);
    }

    /**
     * Refu d'une invitation recus
     * @param principal passé dans le token, correspond à l'user
     * @param idInvit l'invitation concernée
     * @return l'invitation supprimée
     */
    @DeleteMapping(path="/reject")
    public Mono<FriendshipDTO> rejectInvitation(Principal principal, @RequestBody Long idInvit){
        return friendshipService.cancelPendingInvitation(principal,idInvit).map(friendshipService::userToDTO);
    }

    /**
     * Quand un amis supprime sa relation avec un autre
     * @param principal l'user qui prend la decision de rompre
     * @param secondUserId l'id du futur ex-ami
     * @return la relation supprimée
     */
    @DeleteMapping(path="delete")
    public Mono<FriendshipDTO> deleteRelation(Principal principal,@RequestBody String secondUserId){
        return friendshipService.deleteRelation(principal,secondUserId).map(friendshipService::userToDTO);
    }

    @GetMapping(path = "/howManyFriends")
    public Mono<Long> howManyFriends(Principal principal){
        return friendshipService.howManyFriends(principal);
    }
}
