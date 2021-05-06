package mjp.socialnetwork.post.utils;

import mjp.socialnetwork.post.model.Post;
import mjp.socialnetwork.post.model.dto.PostDTO;
import org.modelmapper.ModelMapper;

public class PostMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private PostMapper() { throw new IllegalStateException("Utility class");}

    public static PostDTO toDto(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }

    public static Post toEntity(PostDTO postDTO) {
        return Post.builder()
                .id(postDTO.getIdPost())
                .userId(postDTO.getUserId())
                .isPublic(postDTO.isPublic())
                .message(postDTO.getMessage())
                .publicationDate(postDTO.getPublicationDate())
                .build();
    }
}
