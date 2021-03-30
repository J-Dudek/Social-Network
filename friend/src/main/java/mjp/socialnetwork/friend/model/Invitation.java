package mjp.socialnetwork.friend.model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(InvitationId.class)
public class Invitation {

    @Id
    private Long firstUserId;
    @Id
    private Long secondUserId;
    private Timestamp invitationDate;
}
