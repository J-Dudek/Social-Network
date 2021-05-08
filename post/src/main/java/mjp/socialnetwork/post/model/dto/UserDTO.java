package mjp.socialnetwork.post.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String idUser;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String email;
    private String phoneNumber;
    private String city;
    private LocalDateTime signInDate;
    private String username;

    public UserDTO(String firstName, String lastName, LocalDate birthdate, String email, String phoneNumber, String city, LocalDateTime signInDate, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.signInDate = signInDate;
        this.username= username;

    }
}