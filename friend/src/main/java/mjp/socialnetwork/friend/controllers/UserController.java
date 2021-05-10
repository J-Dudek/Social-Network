package mjp.socialnetwork.friend.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.model.dto.UserDTO;
import mjp.socialnetwork.friend.services.FriendshipService;
import mjp.socialnetwork.friend.services.UserService;
import mjp.socialnetwork.friend.views.UserViews;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/friends/users")
public class UserController {

    private final UserService userService;
    private final FriendshipService friendshipService;

    @GetMapping(path = "/all")
    public Flux<UserDTO> findAllUsers(Principal principal) {
        return this.userService.findAllUsers();
    }


    @GetMapping(path = "/logOrsign")
    public Mono<UserDTO> logOrsign(Principal principal) {
        return userService.logOrsign(principal);
    }

    @GetMapping(path = "/{userId}")
    @JsonView(UserViews.Public.class)
    public Mono<ResponseEntity<UserDTO>> findUserByUserId(@PathVariable("userId") String userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/friend/{userId}")
    @JsonView(UserViews.Friends.class)
    public Mono<ResponseEntity<UserDTO>> findFriendByUserId(@PathVariable("userId") String userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/update")
    public Mono<ResponseEntity<UserDTO>> updateUser(Principal principal, @RequestBody Mono<UserDTO> userDTOMono) {
        return this.userService.updateUser(principal, userDTOMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<Void> deleteUserById(Principal principal) {
        return this.userService.deleteUserById(principal);
    }

    @PostMapping(path = "/search")
    @JsonView(UserViews.Public.class)
    public Flux<UserDTO> findUsersByInput(Principal principal,@RequestBody String input ) {
        return userService.findUsersByInput(input);
    }

    /**
     * Recuperation des informations de l'user (count friend and infos)
     * @param principal passé dans le token
     * @return Flux avec 2 tuple
     */
    @GetMapping(path = "/aboutUser")
    public Mono<Tuple2<UserDTO, Long>> aboutUser(Principal principal){
        Mono<UserDTO> map = userService.findById(principal.getName());
        Mono<Long> longMono = friendshipService.howManyFriends(principal);
        return Mono.zip(map, longMono);
    }


}
