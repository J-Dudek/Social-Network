package mjp.socialnetwork.friend.services;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.friend.model.Friendship;
import mjp.socialnetwork.friend.model.dto.FriendshipDTO;
import mjp.socialnetwork.friend.repositories.FriendshipRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AllArgsConstructor
@Service
public class FriendshipService {

    private final ModelMapper modelMapper;
    private final FriendshipRepository friendshipRepository;


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
