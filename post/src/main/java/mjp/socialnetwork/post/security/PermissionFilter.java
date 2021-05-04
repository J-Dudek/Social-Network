package mjp.socialnetwork.post.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PermissionFilter implements Converter<Map<String, Object>, Map<String, Object>> {

    private final MappedJwtClaimSetConverter delegate = MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

    public Map<String, Object> convert(Map<String, Object> claims) {
        Map<String, Object> convertedClaims = this.delegate.convert(claims);
        String scope = (String) convertedClaims.get("scope");
        List<String> userPermissions = (List<String>) convertedClaims.get("https://social-network.mjp/");
        convertedClaims.put("scope", scope + " " + userPermissions.stream().reduce("", (s, s2) -> s + " " + s2));

        return convertedClaims;
    }
}