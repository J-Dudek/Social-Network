package mjp.socialnetwork.friend.services;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.model.User;
import mjp.socialnetwork.friend.model.dto.UserDTO;
import mjp.socialnetwork.friend.repositories.UserRepository;
import mjp.socialnetwork.friend.utils.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;


@AllArgsConstructor
@Transactional
@Service
public class UserService {


    private final UserRepository userRepository;

    //TODO DELETE just for test
    public Flux<UserDTO> findAllUsers() {
        return userRepository.findAll().map(UserMapper::toDto);
    }

    /**
     * Recherche si l'utilisateur existe en base, si non, l'insert
     * @param principal passé dans le token
     * @return Mono user inséré ou trouvé en bdd
     */
    public Mono<UserDTO> logOrsign(Principal principal) {
        return userRepository.existsById(principal.getName())
                .flatMap(exists -> {
                    if (Boolean.FALSE.equals(exists)) {
                        return userRepository.save(User.builder()
                                .id(principal.getName())
                                .signInDate(LocalDateTime.now())
                                .isNew(true) // for stats
                                .newUser(true) //transcient
                                .build())
                                .map(UserMapper::toDto);
                    } else {
                        return userRepository.findById(principal.getName())
                                .map(UserMapper::toDto);
                    }
                });
    }

    /**
     * Recuperation d'un utilisateur en passant son id technique en parametre
     *
     * @param userId
     * @return
     */
    public Mono<UserDTO> findById(String userId) {
        return userRepository.findById(userId)
                .map(UserMapper::toDto);
    }

    /**
     * Permet de mettre à jour un utilisateur
     *
     * @param principal l'utilisateur connecte
     * @param userDTOMono   les données mise à jour
     * @return l'user mis à jour
     */
    public Mono<UserDTO> updateUser(Principal principal, Mono<UserDTO> userDTOMono) {

        return userRepository
                .findById(principal.getName())
                .flatMap(user -> userDTOMono
                        .map(UserMapper::toEntity)
                        .doOnNext(u -> u.setIsNew(false))
                        .doOnNext(u -> u.setId(user.getId()))
                        .doOnNext(u -> u.setSignInDate(user.getSignInDate()))
                        .doOnNext(u -> u.setBirthdate(u.getBirthdate().plusDays(1)))
                )
                .flatMap(this.userRepository::save)
                .map(UserMapper::toDto);
    }

    /**
     * Permet de recuperer les users comportant l'input , on recherche volontairement qu'avec un espace maxi
     * @param input la saisie utilisateur
     * @return le flux d'UserDTO
     */
    public Flux<UserDTO> findUsersByInput(String input){
        String[] s = input.split(" ");
        if(s.length>1){
            return Flux.merge(getByInput(s[0]),getByInput(s[1])).distinct().map(UserMapper::toDto);
        }else{
            return getByInput(s[0]).map(UserMapper::toDto);
        }
    }
    private Flux<User>getByInput(String search){
        Flux < User > byUsername = userRepository.findUsersByUsernameLike(search);
        Flux<User> byFirstName = userRepository.findUsersByFirstNameLike(search);
        Flux<User> byLastName = userRepository.findUsersByLastNameLike(search);
        return Flux.merge(byUsername, byFirstName, byLastName).distinct();

    }

    /**
     * Suppression de l'user qui fait appel à la methode.
     * @param principal passé dans le token
     */
    public Mono<Void> deleteUserById(Principal principal) {
        return userRepository.deleteById(principal.getName());
    }


}
