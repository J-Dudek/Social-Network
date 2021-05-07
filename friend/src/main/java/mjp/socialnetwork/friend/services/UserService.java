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
import java.time.LocalDateTime;


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
     * @param principal passé dans le token
     * @return Mono user inséré ou trouvé en bdd
     */
    @Transactional
    public Mono<User> logOrsign(Principal principal){
        return userRepository.existsById(principal.getName())
                .flatMap(aBoolean -> {
                    if (!aBoolean) {
                        User newUser = User.builder()
                                .id(principal.getName())
                                .signInDate(LocalDateTime.now())
                                .isNew(true) // for stats
                                .newUser(true) //transcient
                                .build();
                        return userRepository.save(newUser);
                    } else {
                        Mono<User> user;
                        user = userRepository.findById(principal.getName());
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
     * @return l'user mis à jour
     */
    @Transactional
    public Mono<User> updateUser(Principal principal,UserDTO userDTO){

        return userRepository
                .findById(principal.getName())
                .map(user -> {
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setBirthdate(userDTO.getBirthdate().plusDays(1));
                    user.setUsername(userDTO.getUsername());
                    user.setEmail(userDTO.getEmail());
                    user.setPhoneNumber(userDTO.getPhoneNumber());
                    user.setCity(userDTO.getCity());
                    user.setIsNew(false);
                    return user;
                })
                .flatMap(this.userRepository::save);

    }

    /**
     * Permet de rechercher les utilisateur ayant un nom ou un prenom qui like
     * @param firstname prenom ou nom , au final ou fait un match des deux
     * @param lastname prenom ou nom, au final on fait un match des deux
     * @return liste d'users compatible
     */
    public Flux<User> findByfirstOrlastNameLike(String firstname, String lastname){
        return userRepository.findUsersByFirstNameLikeOrLastNameLike(firstname,lastname);
    }

    /**
     * Suppression de l'user qui fait appel à la methode.
     * @param principal passé dans le token
     */
    public void deleteUserById(Principal principal){
        userRepository.deleteById(principal.getName());
    }

    /**
     * permet de convertir user en DTO
     * @param user user à transformer en DTO
     * @return dto de l'user
     */
    public UserDTO userToDTO(User user){
        return modelMapper.map(user,UserDTO.class);
    }

    /**
     * permet de convertir DTO en user
     * @param userDTO dto à passer en en user
     * @return l'user
     */
    public User dtoToUser(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getIdUser())
                .city(userDTO.getCity())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phoneNumber(userDTO.getPhoneNumber())
                .birthdate(userDTO.getBirthdate().plusDays(1))
        .build();

    }


}
