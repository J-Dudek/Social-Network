package mjp.socialnetwork.post.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDTO {

    private Long idPost;
    private String message;
    private LocalDateTime publicationDate;
    private boolean isPublic;
    private String userId;

    public PostDTO(String message, LocalDateTime publicationDate, boolean isPublic, String userId) {
        this.message = message;
        this.publicationDate = publicationDate;
        this.isPublic = isPublic;
        this.userId = userId;
    }
}
