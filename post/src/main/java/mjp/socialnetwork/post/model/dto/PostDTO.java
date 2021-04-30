package mjp.socialnetwork.post.model.dto;

import lombok.*;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDTO {

    private Long idPost;
    private String message;
    private Timestamp publicationDate;
    private boolean isPublic;
    private String userId;

    public PostDTO(String message, Timestamp publicationDate, boolean isPublic, String userId) {
        this.message = message;
        this.publicationDate = publicationDate;
        this.isPublic = isPublic;
        this.userId = userId;
    }
}
