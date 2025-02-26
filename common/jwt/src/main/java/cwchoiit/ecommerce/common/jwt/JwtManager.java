package cwchoiit.ecommerce.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import org.springframework.data.util.Pair;

import java.security.Key;
import java.util.Date;

import static cwchoiit.ecommerce.common.jwt.JwtProperties.EXPIRATION_TIME;

public abstract class JwtManager {

    private JwtManager() {
        throw new UnsupportedOperationException("This class is a utility class and cannot be instantiated");
    }

    public static String getUserIdByToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKeyResolver(SigningKeyResolver.INSTANCE)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static String createToken(Long userId) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));

        Pair<String, Key> randomKey = JwtKey.getRandomKey();
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRATION_TIME))
                .setHeaderParam(JwsHeader.KEY_ID, randomKey.getFirst())
                .signWith(randomKey.getSecond())
                .compact();
    }
}
