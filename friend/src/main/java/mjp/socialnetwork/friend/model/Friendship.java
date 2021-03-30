package mjp.socialnetwork.friend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Friendship {

    @Id
    private Long firstUserId;
    private Long secondUserId;

    private Timestamp friendshipDate;
}
