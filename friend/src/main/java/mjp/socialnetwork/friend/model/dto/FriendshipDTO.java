package mjp.socialnetwork.friend.model.dto;

import java.sql.Timestamp;

public class FriendshipDTO {

    private Long firstUserId;
    private Long secondUserId;
    private Timestamp friendshipDate;

    public FriendshipDTO() {
    }

    public FriendshipDTO(Long firstUserId, Long secondUserId, Timestamp friendshipDate) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.friendshipDate = friendshipDate;
    }

    public Long getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(Long firstUserId) {
        this.firstUserId = firstUserId;
    }

    public Long getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(Long secondUserId) {
        this.secondUserId = secondUserId;
    }

    public Timestamp getFriendshipDate() {
        return friendshipDate;
    }

    public void setFriendshipDate(Timestamp friendshipDate) {
        this.friendshipDate = friendshipDate;
    }

    @Override
    public String toString() {
        return "FriendshipDTO{" +
                "firstUserId=" + firstUserId +
                ", secondUserId=" + secondUserId +
                ", friendshipDate=" + friendshipDate +
                '}';
    }
}