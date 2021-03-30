package mjp.socialnetwork.friend.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
@Builder
public class InvitationId implements Serializable {

    @Id
    private Long firstUserId;
    @Id
    private Long secondUserId;

}
