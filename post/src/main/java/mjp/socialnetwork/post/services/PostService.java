package mjp.socialnetwork.post.services;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.post.converters.PostConverter;
import mjp.socialnetwork.post.model.dto.PostDTO;
import mjp.socialnetwork.post.repositories.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Transactional
@AllArgsConstructor
@Service
public class PostService {

    private final PostConverter postConverter;
    private final PostRepository  postRepository;


    public Flux<PostDTO> findAllPosts() {
        return postConverter.entityToDto(postRepository.findAll());
    }
}
