package mjp.socialnetwork.friend.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import mjp.socialnetwork.friend.views.UserViews;

import java.sql.Date;
import java.sql.Timestamp;

public class UserDTO {

    @JsonView(UserViews.Private.class)
    private Long idUser;
    @JsonView(UserViews.Public.class)
    private String firstName;
    @JsonView(UserViews.Public.class)
    private String lastName;
    @JsonView(UserViews.Friends.class)
    private Date birthdate;
    @JsonView(UserViews.Friends.class)
    private String email;
    @JsonView(UserViews.Friends.class)
    private String phoneNumber;
    @JsonView(UserViews.Friends.class)
    private String city;
    @JsonView(UserViews.Friends.class)
    private Timestamp signInDate;
    @JsonView(UserViews.Friends.class)
    private String username;
    @JsonView(UserViews.Admin.class)
    private String password;

    public UserDTO() {
    }

    public UserDTO(Long idUser, String firstName, String lastName, Date birthdate, String email, String phoneNumber, String city, Timestamp signInDate,String username, String password) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.signInDate = signInDate;
        this.username = username;
        this.password = password;
    }
    public UserDTO(String firstName, String lastName, Date birthdate, String email, String phoneNumber, String city, Timestamp signInDate, String username,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.signInDate = signInDate;
        this.username= username;
        this.password = password;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
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

    public Timestamp getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Timestamp signInDate) {
        this.signInDate = signInDate;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username;}

    public void setPassword(String password) {
        this.password = password;
    }
}