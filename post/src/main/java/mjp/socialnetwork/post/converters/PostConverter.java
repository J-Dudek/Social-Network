package mjp.socialnetwork.post.converters;

import mjp.socialnetwork.post.model.Post;
import mjp.socialnetwork.post.model.dto.PostDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class PostConverter {

    public Mono<PostDTO> entityToDto(Post post) {
        ModelMapper modelMapper = new ModelMapper();
        return Mono.just(modelMapper.map(post, PostDTO.class));
    }

    public Flux<PostDTO> entityToDto(Flux<Post> posts) {
        return posts.flatMap(post -> entityToDto(post));
    }

    public Post dtoToEntity(PostDTO postDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(postDTO, Post.class);
    }

    public List<Post> dtoToEntity(List<PostDTO> postsDTO) {
        return postsDTO.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
