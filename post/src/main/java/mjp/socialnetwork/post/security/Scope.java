package mjp.socialnetwork.post.security;
/**
 * Enumeration des roles disponibles pour ce service
 */
public enum Scope {
    POST_CREATE ("SCOPE_post:create"),
    POST_UPDATE ("SCOPE_post:update"),
    POST_DELETE ("SCOPE_post:delete"),
    POST_SEARCH ("SCOPE_post:search");

    private String scope ;

    Scope(String scope) {
        this.scope = scope.toString() ;
    }


    public String scope() {
        return  this.scope ;
    }
}