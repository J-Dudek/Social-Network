package mjp.socialnetwork.friend.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Date;
import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

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
    private Boolean isNew;

}
