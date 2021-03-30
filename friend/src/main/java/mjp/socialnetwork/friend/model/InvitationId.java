package mjp.socialnetwork.friend.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InvitationId implements Serializable {

    @Id
    private Long firstUserId;
    @Id
    private Long secondUserId;

}
