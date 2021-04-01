package mjp.socialnetwork.post.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class PostDTO {

    private Long idPost;
    private String message;
    private LocalDateTime publicationDate;
    private boolean isPublic;
    private Long userId;

    public PostDTO() {
    }

    public PostDTO(Long idPost, String message, LocalDateTime publicationDate, boolean isPublic, Long userId) {
        this.idPost = idPost;
        this.message = message;
        this.publicationDate = publicationDate;
        this.isPublic = isPublic;
        this.userId = userId;
    }
    public PostDTO(String message, LocalDateTime publicationDate, boolean isPublic, Long userId) {
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

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
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
