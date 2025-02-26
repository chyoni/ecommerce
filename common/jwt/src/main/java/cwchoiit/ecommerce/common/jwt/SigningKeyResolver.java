package cwchoiit.ecommerce.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;

import java.security.Key;

public class SigningKeyResolver extends SigningKeyResolverAdapter {

    public static SigningKeyResolver INSTANCE = new SigningKeyResolver();

    @Override
    public Key resolveSigningKey(JwsHeader header, Claims claims) {
        String keyId = header.getKeyId();
        if (keyId == null) {
            return null;
        }
        return JwtKey.getKey(keyId);
    }
}
