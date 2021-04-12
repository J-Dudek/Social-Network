package mjp.socialnetwork.friend.services;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.model.User;
import mjp.socialnetwork.friend.model.dto.UserDTO;
import mjp.socialnetwork.friend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;


@AllArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Flux<UserDTO> findAllUsers() {
         return userRepository.findAll().map(this::userToDTO);
    }

    @Transactional
    public Mono<User> logOrsign(Principal principal){
        return userRepository.existsById(principal.getName())
                .flatMap(aBoolean -> {
                    if (!aBoolean) {
                        User newUser = User.builder().id(principal.getName()).newUser(true).build();
                        System.out.println("NewUser : "+newUser.toString());
                        return userRepository.save(newUser);
                    } else {
                        Mono<User> user = userRepository.findById(principal.getName());
                        System.out.println("Exist : "+user.toString());
                        return user;
                    }
                });
    }



    /**
     * permet de convertir user en DTO
     * @param user
     * @return
     */
    public UserDTO userToDTO(User user){
        return modelMapper.map(user,UserDTO.class);
    }

    /**
     * permet de convertir DTO en user
     * @param userDTO
     * @return
     */
    public User dtoToUser(UserDTO userDTO){
        return modelMapper.map(userDTO,User.class);
    }


}
