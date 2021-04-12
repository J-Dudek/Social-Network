package mjp.socialnetwork.friend.services;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.model.User;
import mjp.socialnetwork.friend.model.dto.UserDTO;
import mjp.socialnetwork.friend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Transactional
@AllArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Flux<UserDTO> findAllUsers() {
         return userRepository.findAll().map(this::userToDTO);
    }

    /**
     * permet de convertir user en DTO
     * @param user
     * @return
     */
    private UserDTO userToDTO(User user){
        return modelMapper.map(user,UserDTO.class);
    }

    /**
     * permet de convertir DTO en user
     * @param userDTO
     * @return
     */
    private User dtoToUser(UserDTO userDTO){
        return modelMapper.map(userDTO,User.class);
    }


}
