package mjp.socialnetwork.friend.security;

/**
 * Enumeration des roles disponibles pour ce service
 */
public enum Scope {
    USER_READ ("SCOPE_user:read"),
    USER_UPDATE ("SCOPE_user:update"),
    USER_DELETE ("SCOPE_user:delete"), 
    USER_SEARCH ("SCOPE_user:search"),
    FRIEND_READ ("SCOPE_friend:read"),
    FRIEND_UPDATE ("SCOPE_firend:update"),
    FRIEND_DELETE ("SCOPE_friend:delete"),
    FRIEND_SEARCH ("SCOPE_friend:search"),
    FRIEND_ADD ("SCOPE_friend:add");

    private String scope ;

    Scope(String scope) {
        this.scope = scope.toString() ;
    }


    public String scope() {
        return  this.scope ;
    }
}
