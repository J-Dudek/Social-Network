package mjp.socialnetwork.friend.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class FriendshipDTO {

    private Long id;
    private String firstUserId;
    private String secondUserId;
    private LocalDateTime friendshipDate;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    private Boolean status;

    public FriendshipDTO() {
    }

    public FriendshipDTO(Long id, String firstUserId, String secondUserId, LocalDateTime friendshipDate, boolean status) {
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

    public LocalDateTime getFriendshipDate() {
        return friendshipDate;
    }

    public void setFriendshipDate(LocalDateTime friendshipDate) {
        this.friendshipDate = friendshipDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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