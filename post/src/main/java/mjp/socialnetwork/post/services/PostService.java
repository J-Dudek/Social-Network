package mjp.socialnetwork.post.services;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.post.model.Post;
import mjp.socialnetwork.post.model.dto.PostDTO;
import mjp.socialnetwork.post.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional
@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository  postRepository;
    private final ModelMapper modelMapper;


    public Flux<PostDTO> findAllPosts() {
        return postRepository.findAll().map(post -> modelMapper.map(post,PostDTO.class));
    }

    public Mono<PostDTO> createNewPost(PostDTO postDTO) {
        return postRepository.save(modelMapper.map(postDTO, Post.class)).map(post -> modelMapper.map(post,PostDTO.class));
    }
}
