package mjp.socialnetwork.friend.utils;

import mjp.socialnetwork.friend.model.User;
import mjp.socialnetwork.friend.model.dto.UserDTO;
import org.modelmapper.ModelMapper;

public class UserMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static UserDTO toDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getIdUser())
                .city(userDTO.getCity())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phoneNumber(userDTO.getPhoneNumber())
                .username((userDTO.getUsername()))
                .signInDate(userDTO.getSignInDate())
                .birthdate(userDTO.getBirthdate().plusDays(1))
                .build();
    }
}
