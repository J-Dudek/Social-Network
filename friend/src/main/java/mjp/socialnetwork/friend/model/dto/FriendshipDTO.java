package mjp.socialnetwork.friend.model.dto;

import lombok.Builder;

import java.sql.Date;
@Builder
public class FriendshipDTO {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
    private String firstUserId;
    private String secondUserId;
    private Date friendshipDate;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    private Boolean status;

    public FriendshipDTO() {
    }

    public FriendshipDTO(Long id,String firstUserId, String secondUserId, Date friendshipDate, boolean status) {
        this.id = id;
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.friendshipDate = friendshipDate;
        this.status=status;
    }

    public String getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(String firstUserId) {
        this.firstUserId = firstUserId;
    }

    public String getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(String secondUserId) {
        this.secondUserId = secondUserId;
    }

    public Date getFriendshipDate() {
        return friendshipDate;
    }

    public void setFriendshipDate(Date friendshipDate) {
        this.friendshipDate = friendshipDate;
    }

    @Override
    public String toString() {
        return "FriendshipDTO{" +
                "id= "+ id +
                "firstUserId=" + firstUserId +
                ", secondUserId=" + secondUserId +
                ", friendshipDate=" + friendshipDate +
                ", status= "+ status+
                '}';
    }
}