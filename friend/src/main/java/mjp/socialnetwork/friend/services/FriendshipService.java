package mjp.socialnetwork.friend.services;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.model.Friendship;
import mjp.socialnetwork.friend.model.dto.FriendshipDTO;
import mjp.socialnetwork.friend.repositories.FriendshipRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Transactional
@AllArgsConstructor
@Service
public class FriendshipService {

    private final ModelMapper modelMapper;
    private final FriendshipRepository friendshipRepository;


    /**
     * Permet de créer une invitation
     * @param principal
     * @param idNewFriend
     * @return L'invitation créée
     */
    public Mono<Friendship> createInvitation(Principal principal, String idNewFriend){
        Friendship.FriendshipBuilder builder = Friendship.builder();
        builder.firstUserId(principal.getName());
        builder.secondUserId(idNewFriend);
        builder.friendshipDate(Timestamp.valueOf(LocalDateTime.now()));
        builder.status(false);
        builder.newFriendShip(true);
        Friendship friendship;
        friendship = builder
                .build();
        return friendshipRepository.save(friendship);
    }

    public Mono<Friendship> acceptInvitation(Principal principal,Long idInvitation){
        Friendship friendship = Friendship.builder().
                id(idInvitation)
                .secondUserId(principal.getName())
                .friendshipDate(Timestamp.valueOf(LocalDateTime.now()))
                .status(true)
                .build();
        return friendshipRepository.save(friendship);
    }
    /**
     * Mapper de friendship en friendshipDTO
     * @param friendship
     * @return
     */
    public FriendshipDTO userToDTO(Friendship friendship){
        return modelMapper.map(friendship,FriendshipDTO.class);
    }

    /**
     * mapper de friendshipDTO en friendship
     * @param friendshipDTO
     * @return
     */
    public Friendship dtoToUser(FriendshipDTO friendshipDTO){
        return modelMapper.map(friendshipDTO,Friendship.class);
    }


}
