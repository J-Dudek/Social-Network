package mjp.socialnetwork.post.model.dto;

import java.sql.Timestamp;
import java.util.Date;

public class PostDTO {

    private Long idPost;
    private String message;
    private Timestamp publicationDate;
    private boolean isPublic;
    private Long userId;

    public PostDTO() {
    }

    public PostDTO(Long idPost, String message, Timestamp publicationDate, boolean isPublic, Long userId) {
        this.idPost = idPost;
        this.message = message;
        this.publicationDate = publicationDate;
        this.isPublic = isPublic;
        this.userId = userId;
    }
    public PostDTO(String message, Timestamp publicationDate, boolean isPublic, Long userId) {
        this.message = message;
        this.publicationDate = publicationDate;
        this.isPublic = isPublic;
        this.userId = userId;
    }

    public Long getIdPost() {
        return idPost;
    }

    public void setIdPost(Long idPost) {
        this.idPost = idPost;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Timestamp publicationDate) {
        this.publicationDate = publicationDate;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
