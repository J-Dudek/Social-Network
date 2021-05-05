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
}
