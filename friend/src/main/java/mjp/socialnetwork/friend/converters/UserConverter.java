package mjp.socialnetwork.friend.converters;

import mjp.socialnetwork.friend.model.User;
import mjp.socialnetwork.friend.model.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public Mono<UserDTO> entityToDto(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return Mono.just(modelMapper.map(user, UserDTO.class));
    }

    public Flux<UserDTO> entityToDto(List<User> users) {
        return Flux.fromIterable(users).flatMap(user -> entityToDto(user));
    }

    public User dtoToEntity(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDTO, User.class);
    }

    public List<User> dtoToEntity(List<UserDTO> usersDTO) {
        return usersDTO.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
