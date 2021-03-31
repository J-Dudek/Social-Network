package mjp.socialnetwork.friend.services;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.converters.UserConverter;
import mjp.socialnetwork.friend.model.dto.UserDTO;
import mjp.socialnetwork.friend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Transactional
@AllArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public Flux<UserDTO> findAllUsers() {
         return userConverter.entityToDto(userRepository.findAll());
    }

}
