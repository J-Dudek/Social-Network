package mjp.socialnetwork.post.converters;

import mjp.socialnetwork.post.model.Post;
import mjp.socialnetwork.post.model.dto.PostDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
@Component
public class PostConverter {

    public PostDTO entityToDto(Post post) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(post, PostDTO.class);
    }

    public Post dtoToEntity(PostDTO postDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(postDTO, Post.class);
    }

}
