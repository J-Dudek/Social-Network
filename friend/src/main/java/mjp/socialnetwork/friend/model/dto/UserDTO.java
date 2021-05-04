package mjp.socialnetwork.friend.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import mjp.socialnetwork.friend.views.UserViews;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public class UserDTO {

    @JsonView(UserViews.Private.class)
    private String idUser;
    @JsonView(UserViews.Public.class)
    private String firstName;
    @JsonView(UserViews.Public.class)
    private String lastName;
    @JsonView(UserViews.Friends.class)
    private LocalDate birthdate;
    @JsonView(UserViews.Friends.class)
    private String email;
    @JsonView(UserViews.Friends.class)
    private String phoneNumber;
    @JsonView(UserViews.Friends.class)
    private String city;
    @JsonView(UserViews.Friends.class)
    private LocalDateTime signInDate;
    @JsonView(UserViews.Friends.class)
    private String username;




    public UserDTO() {
    }

    public UserDTO(String idUser, String firstName, String lastName, LocalDate birthdate, String email, String phoneNumber, String city, LocalDateTime signInDate, String username) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.signInDate = signInDate;
        this.username = username;

    }
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDateTime getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(LocalDateTime signInDate) {
        this.signInDate = signInDate;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username;}


}