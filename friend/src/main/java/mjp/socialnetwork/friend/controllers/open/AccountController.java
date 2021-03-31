package mjp.socialnetwork.friend.controllers.open;

import mjp.socialnetwork.friend.converters.UserConverter;
import mjp.socialnetwork.friend.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/account")
public class AccountController {

    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private  String uri;
    public AccountController(UserConverter userConverter, UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userConverter=userConverter;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }


}
