package mjp.socialnetwork.friend.controllers.auth;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.exceptions.errors.SocialNetworkException;
import mjp.socialnetwork.friend.model.dto.UserDTO;
import mjp.socialnetwork.friend.services.UserService;
import mjp.socialnetwork.friend.views.UserViews;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {


    private final UserService userService;

    @GetMapping(path = "/all")
    @JsonView(UserViews.Public.class)
    public Flux<UserDTO> findAllUsers() {
        try {
            return this.userService.findAllUsers();
        } catch (
                SocialNetworkException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}
