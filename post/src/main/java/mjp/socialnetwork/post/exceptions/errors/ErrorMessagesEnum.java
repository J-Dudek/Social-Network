package mjp.socialnetwork.post.exceptions.errors;

public enum ErrorMessagesEnum {


    // Post entity error messages
    POST_NOT_FOUND("aucun post trouvé"),
    POST_NO_POSTS_IN_DATABASE("aucun posts dans la base de données"),
    POST_NO_POST_YET("ce user n'a ecrit aucun post"),
    POST_NOT_PUBLIC("ce post n'est pas publique");

    private final String errorMessage;

    ErrorMessagesEnum(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}