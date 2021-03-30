package mjp.socialnetwork.friend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Invitation {

    @Id
    private Long firstUserId;
    @Id
    private Long secondUserId;
    private Timestamp invitationDate;
}
