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

    //TODO DELETE just for test
    public Flux<UserDTO> findAllUsers() {
         return userRepository.findAll().map(this::userToDTO);
    }

    /**
     * Recherche si l'utilisateur existe en base, si non, l'insert
     * @param principal
     * @return Mono user inséré ou trouvé en bdd
     */
    @Transactional
    public Mono<User> logOrsign(Principal principal){
        return userRepository.existsById(principal.getName())
                .flatMap(aBoolean -> {
                    if (!aBoolean) {
                        User newUser = User.builder().id(principal.getName()).newUser(true).build();
                        return userRepository.save(newUser);
                    } else {
                        Mono<User> user = userRepository.findById(principal.getName());
                        return user;
                    }
                });
    }

    /**
     * Recuperation d'un utilisateur en passant son id technique en parametre
     * @param userId
     * @return
     */
    public Mono<User> findById(String userId){
        return userRepository.findById(userId);
    }

    /**
     *  Permet de mettre à jour un utilisateur
     * @param principal l'utilisateur connecte
     * @param userDTO les données mise à jour
     * @return
     */
    @Transactional
    public Mono<User> updateUser(Principal principal,UserDTO userDTO){
        User userUpdated = dtoToUser(userDTO);
        userUpdated.setId(principal.getName());
        return userRepository.save(userUpdated);
    }

    /**
     * Permet de rechercher les utilisateur ayant un nom ou un prenom qui like
     * @param firstname
     * @param lastname
     * @return
     */
    public Flux<User> findByfirstOrlastNameLike(String firstname, String lastname){
        return userRepository.findUsersByFirstNameLikeOrLastNameLike(firstname,lastname);
    }

    /**
     * Suppression de l'user qui fait appel à la methode.
     * @param principal
     */
    public void deleteUserById(Principal principal){
        userRepository.deleteById(principal.getName());
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
