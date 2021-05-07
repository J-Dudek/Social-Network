package mjp.socialnetwork.friend.utils;

import mjp.socialnetwork.friend.model.Friendship;
import mjp.socialnetwork.friend.model.dto.FriendshipDTO;
import org.modelmapper.ModelMapper;

public class FriendshipMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private FriendshipMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static FriendshipDTO toDto(Friendship friendship) {
        return modelMapper.map(friendship, FriendshipDTO.class);
    }

    public static Friendship toEntity(FriendshipDTO friendshipDTO) {
        return Friendship.builder()
                .id(friendshipDTO.getId())
                .firstUserId(friendshipDTO.getFirstUserId())
                .secondUserId(friendshipDTO.getSecondUserId())
                .friendshipDate(friendshipDTO.getFriendshipDate())
                .status(friendshipDTO.getStatus())
                .build();
    }
}
