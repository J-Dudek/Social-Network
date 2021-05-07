package mjp.socialnetwork.friend.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.exceptions.errors.SocialNetworkException;
import mjp.socialnetwork.friend.model.dto.UserDTO;
import mjp.socialnetwork.friend.services.FriendshipService;
import mjp.socialnetwork.friend.services.UserService;
import mjp.socialnetwork.friend.views.UserViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/friends/users")
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final FriendshipService friendshipService;



    @GetMapping(path = "/all")
    public Flux<UserDTO> findAllUsers(Principal principal) {
        try {
            return this.userService.findAllUsers();
        } catch (
                SocialNetworkException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(path = "/logOrsign")
    public Mono<UserDTO> logOrsign(Principal principal) {
       return userService.logOrsign(principal).map(userService::userToDTO);
    }

    @GetMapping(path = "/{userId}")
    @JsonView(UserViews.Public.class)
    public Mono<UserDTO> findUserByUserId(@PathVariable("userId") String userId){

        return userService.findById(userId).map(userService::userToDTO);
    }

    @PutMapping(path = "/update")
    public Mono<UserDTO> updateUser(Principal principal,@RequestBody UserDTO newUserDto) {
        try {
            return this.userService.updateUser(principal,newUserDto).map(userService::userToDTO);
        } catch (SocialNetworkException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/delete/")
    public void deleteUserById(Principal principal) {
        try {
            this.userService.deleteUserById(principal);
        } catch (SocialNetworkException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{firstName}/{lastName}")
    @JsonView(UserViews.Public.class)
    public Flux<UserDTO> findUsersByFirstNameOrLastName(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName){

        return userService.findByfirstOrlastNameLike(firstName,lastName).map(userService::userToDTO);
    }

    /**
     * Recuperation des informations de l'user (count friend and infos)
     * @param principal passé dans le token
     * @return Flux avec 2 tuple
     */
    @GetMapping(path = "/aboutUser")
    public Mono<Tuple2<UserDTO, Long>> aboutUser(Principal principal){
        Mono<UserDTO> map = userService.findById(principal.getName()).map(userService::userToDTO);
        Mono<Long> longMono = friendshipService.howManyFriends(principal);
        return Mono.zip(map, longMono);
    }
    

}
