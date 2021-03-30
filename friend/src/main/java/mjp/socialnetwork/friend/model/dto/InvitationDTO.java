package mjp.socialnetwork.friend.model.dto;

import java.sql.Timestamp;

public class InvitationDTO {

    private Long firstUserId;
    private Long secondUserId;
    private Timestamp invitationDate;

    public InvitationDTO() {
    }

    public InvitationDTO(Long firstUserId, Long secondUserId, Timestamp invitationDate) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.invitationDate = invitationDate;
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

    public Timestamp getInvitationDate() {
        return invitationDate;
    }

    public void setInvitationDate(Timestamp invitationDate) {
        this.invitationDate = invitationDate;
    }
}