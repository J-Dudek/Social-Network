package mjp.socialnetwork.post.services;

import lombok.AllArgsConstructor;
import mjp.socialnetwork.post.exceptions.PostException;
import mjp.socialnetwork.post.model.dto.PostDTO;
import mjp.socialnetwork.post.model.dto.UserDTO;
import mjp.socialnetwork.post.repositories.PostRepository;
import mjp.socialnetwork.post.utils.PostMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.time.LocalDateTime;

@Transactional
@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Flux<PostDTO> findAll() {
        return this.postRepository.findAll()
                .map(PostMapper::toDto);
    }

    public Flux<PostDTO> findAllByUserId(String userId) {
        return this.postRepository.findAllByUserId(userId)
                .map(PostMapper::toDto);
    }

    public Flux<PostDTO> findAllByUserIdAndPublic(String userId, boolean isPublic) {
        return this.postRepository.findPostByUserIdAndPublic(userId, isPublic)
                .map(PostMapper::toDto);
    }

    public Flux<PostDTO> findAllFriendsPosts(Flux<UserDTO> userDTOFlux) {
        return userDTOFlux
                .flatMap(userDTO -> this.postRepository.findAllByUserId(userDTO.getIdUser()))
                .map(PostMapper::toDto);
    }

    public Mono<PostDTO> findById(Long id) {
        return this.postRepository.findById(id).map(PostMapper::toDto);
    }

    public Mono<PostDTO> create(Principal principal, Mono<PostDTO> postDTO) {
        return postDTO
                .map(PostMapper::toEntity)
                .doOnNext(post -> post.setUserId(principal.getName()))
                .doOnNext(post -> post.setPublicationDate(LocalDateTime.now()))
                .flatMap(this.postRepository::save)
                .map(PostMapper::toDto);
    }

    public Mono<PostDTO> update(Long id, Mono<PostDTO> postDTOMono) {
        return this.postRepository.findById(id)
                .flatMap(post -> postDTOMono
                        .map(PostMapper::toEntity)
                        .doOnNext(p -> p.setId(id)))
                .flatMap(this.postRepository::save)
                .map(PostMapper::toDto);
    }

    public Mono<Void> delete(Principal principal, Long id) {
        return this.postRepository
                .findById(id)
                .flatMap(
                        post -> {
                            if (post.getUserId().equals(principal.getName())) {
                                return this.postRepository.deleteById(id);
                            } else {
                                return Mono.error(PostException::new);
                            }
                        }
                );
    }

    public Flux<PostDTO> findMyPosts(Principal principal){
        return postRepository.findAllByUserId(principal.getName()).map(PostMapper::toDto);
    }


}
