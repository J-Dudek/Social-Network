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
     *
     * @param principal
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
                        return userRepository.findById(principal.getName())
                                .map(UserMapper::toDto);
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
     * @param userDTO   les données mise à jour
     * @return
     * @param userDTO les données mise à jour
     * @return l'user mis à jour
     */
    @Transactional
    public Mono<User> updateUser(Principal principal,UserDTO userDTO){

        return userRepository
                .findById(principal.getName())
                .flatMap(user -> userDTO
                        .map(UserMapper::toEntity)
                        .doOnNext(u -> u.setIsNew(false))
                )
                .flatMap(this.userRepository::save)
                .map(UserMapper::toDto);
    }

    /**
     * Permet de rechercher les utilisateur ayant un nom ou un prenom qui like
     *
     * @param firstname
     * @param lastname
     * @return
     * @param firstname prenom ou nom , au final ou fait un match des deux
     * @param lastname prenom ou nom, au final on fait un match des deux
     * @return liste d'users compatible
     */
    public Flux<UserDTO> findByfirstOrlastNameLike(String firstname, String lastname) {
        return userRepository.findUsersByFirstNameLikeOrLastNameLike(firstname, lastname)
                .map(UserMapper::toDto);
    }

    /**
     * Suppression de l'user qui fait appel à la methode.
     *
     * @param principal
     * @param principal passé dans le token
     */
    public Mono<Void> deleteUserById(Principal principal) {
        return userRepository.deleteById(principal.getName());
    }


}
