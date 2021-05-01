package mjp.socialnetwork.friend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.sql.Date;
import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements Persistable {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private Date birthdate;
    private String email;
    private String phoneNumber;
    private String city;
    private Timestamp signInDate;
    private String username;


    @Transient
    private boolean newUser;

    @Override
    @Transient
    public boolean isNew() {
        return this.newUser || id == null;
    }

    public User setAsNew(){
        this.newUser = true;
        return this;
    }

}
