package mjp.socialnetwork.post.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Post {

    @Id
    private Long id;
    private String message;
    private Timestamp publicationDate;
    private boolean isPublic;

    private Long userId;

}
