package mjp.socialnetwork.friend.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.exceptions.errors.SocialNetworkException;
import mjp.socialnetwork.friend.model.dto.UserDTO;
import mjp.socialnetwork.friend.services.UserService;
import mjp.socialnetwork.friend.views.UserViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/friends/users")
public class UserController {

    @Autowired
    private final UserService userService;




    @GetMapping(path = "/all2")
    public Flux<UserDTO> findAllUsers2(Principal principal) {
        System.out.println("Appel partie secu");
        System.out.println("ID = " + principal.getName());
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


}
