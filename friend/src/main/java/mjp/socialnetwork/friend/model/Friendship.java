package mjp.socialnetwork.friend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Friendship implements Persistable {

    @Id
    private Long id;

    private String firstUserId;
    private String secondUserId;

    private Date friendshipDate;
    private boolean status;

    @Transient
    private boolean newFriendShip;


    @Override
    @Transient
    public boolean isNew() {
        return this.newFriendShip || id == null;
    }

    public Friendship setAsNew(){
        this.newFriendShip = true;
        return this;
    }
}
