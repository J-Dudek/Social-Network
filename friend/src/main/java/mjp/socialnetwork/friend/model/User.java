package mjp.socialnetwork.friend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private Date birthdate;
    private String email;
    private String phoneNumber;
    private String city;
    private Timestamp signInDate;
    private String username;

    @JsonIgnore
    private String password;

}
