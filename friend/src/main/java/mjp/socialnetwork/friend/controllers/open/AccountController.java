package mjp.socialnetwork.friend.controllers.open;

import mjp.socialnetwork.friend.repositories.UserRepository;
import mjp.socialnetwork.friend.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/friends/account")
public class AccountController {

    private final UserRepository userRepository;
    private final UserService userService;

    public AccountController( UserRepository userRepository, UserService userService){
        this.userRepository=userRepository;
        this.userService=userService;
    }


}
