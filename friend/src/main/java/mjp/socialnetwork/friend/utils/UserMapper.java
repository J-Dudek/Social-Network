package mjp.socialnetwork.friend.utils;

import mjp.socialnetwork.friend.model.User;
import mjp.socialnetwork.friend.model.dto.UserDTO;
import org.modelmapper.ModelMapper;

public class UserMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private UserMapper() { throw new IllegalStateException("Utility class");}

    /**
     * permet de convertir user en DTO
     * @param user
     * @return USerDTO
     */
    public static UserDTO toDto(User user){
        return modelMapper.map(user,UserDTO.class);
    }

    /**
     * permet de convertir DTO en user
     * @param userDTO
     * @return UserDTO
     */
    public static User toEntity(UserDTO userDTO){
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
